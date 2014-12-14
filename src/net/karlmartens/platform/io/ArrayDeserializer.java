package net.karlmartens.platform.io;

import java.lang.reflect.Array;

import net.karlmartens.platform.io.FileStream.ReadBuffer;

public class ArrayDeserializer implements Deserializer<Object> {
    
    private final Class<?> _componentType;
    private final Deserializer<?> _componentDeserializer;
    private final IntDeserializer _intDeserializer;

    ArrayDeserializer(Class<?> componentType, Deserializer<?> componentDeserializer) {
        _componentType = componentType;
        _componentDeserializer = componentDeserializer;
        _intDeserializer = IntDeserializer.instance();
    }

    @Override
    public Object read(ReadBuffer buffer) {
        int size = _intDeserializer.read(buffer);
        Object arr = Array.newInstance(_componentType, size);
        for (int i=0; i<size; i++) {
            Array.set(arr, i, _componentDeserializer.read(buffer));
        }
        return arr;
    }

}
