package net.karlmartens.platform.io;

import net.karlmartens.platform.io.FileStream.ReadBuffer;

public class EnumDeserializer<T> implements Deserializer<T> {
    
    private final Class<T> _type;
    private final IntDeserializer _intDeserializer;

    EnumDeserializer(Class<T> type) {
        _type = type;        
        _intDeserializer = IntDeserializer.instance();
    }

    @Override
    public T read(ReadBuffer buffer) {
        int ordinal = _intDeserializer.read(buffer);
        return _type.getEnumConstants()[ordinal];
    }
    
    public static <T extends Enum<T>> EnumDeserializer<T> create(Class<T> type) {
        return new EnumDeserializer<>(type);
    }

}
