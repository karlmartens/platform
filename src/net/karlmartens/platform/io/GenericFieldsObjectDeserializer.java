/**
 *   Copyright 2014 Karl Martens
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *       
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *   net.karlmartens.platform, is a library of shared basic utility classes
 */

package net.karlmartens.platform.io;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import net.karlmartens.platform.io.FileInputStream.ReadBuffer;

/**
 * @author kmartens
 *
 */
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
