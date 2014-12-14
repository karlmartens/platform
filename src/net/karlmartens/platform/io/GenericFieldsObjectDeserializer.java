package net.karlmartens.platform.io;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import net.karlmartens.platform.io.FileStream.ReadBuffer;

final class GenericObjectDeserializer<T> implements Deserializer<T> {
    
    private final Class<T> _type;
    private final int nullableBytes;
    private final List<Deserializer<?>> _deserializers;
    
    public GenericObjectDeserializer(Class<T> type, int nullableBytes, List<Deserializer<?>> deserializers) {
        _type = type;
        this.nullableBytes = nullableBytes;
        _deserializers = deserializers;
    }

    @Override
    public T read(ReadBuffer buffer) {
        BitSet nulls = readNullRecord(buffer);
        int nullIndex = 0;
        
        try {
            T obj = _type.newInstance();
            
            final Field[] fields = _type.getFields();
            for (int i=0; i<fields.length; i++) {
                Field field = fields[i];
                
                final Object value;
                if (!field.getType().isPrimitive()) {
                    if (nulls.get(nullIndex++)) {
                        value = null;
                    } else {
                        value = readValue(buffer, i);
                    }
                } else {
                    value = readValue(buffer, i);
                }
                
                field.set(obj, value);
            }
            
            return obj;
        } catch (IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private Object readValue(ReadBuffer buffer, int index) {
        Deserializer<?> deserializer = _deserializers.get(index);
        return deserializer.read(buffer);
    }

    private BitSet readNullRecord(ReadBuffer buffer) {
        byte[] rawNullable = new byte[nullableBytes];
        buffer.getBytes(rawNullable);
        return BitSet.valueOf(rawNullable);
    }

    public static <T> Deserializer<T> create(Deserializers deserializers, Class<T> type) {
        int nullable = 0;
        List<Deserializer<?>> list = new ArrayList<>();
        for (Field field : type.getFields()) {
            Class<?> fieldType = field.getType();
            if (!fieldType.isPrimitive())
                nullable++;
            
            Deserializer<?> deserializer = deserializerForType(deserializers, fieldType);
            list.add(deserializer);
        }
        
        int nullableBytes = (int)Math.ceil(nullable / 8.0);
        
        return new GenericObjectDeserializer<>(type, nullableBytes, list);
    }

    private static Deserializer<?> deserializerForType(Deserializers deserializers, Class<?> type) {
        if (deserializers.isSupported(type))
            return deserializers.getDeserializer(type);

        return create(deserializers, type);
    }

}
