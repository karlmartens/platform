package net.karlmartens.platform.io;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

final class Deserializers {

    private final Map<Class<?>, Deserializer<?>> _map = new HashMap<>();
    private boolean _useGenericObjectDeserializer = false;

    void useGenericObjectDeserializer() {
        _useGenericObjectDeserializer = true;
    }

    <T> Deserializers addDeserializer(Class<T> type,
            Deserializer<T> deserializer) {
        _map.put(type, deserializer);
        return this;
    }

    boolean isSupported(Class<?> type) {
        return _useGenericObjectDeserializer
                || type.isEnum()
                || (type.isArray() && _map.containsKey(type.getComponentType()))
                || _map.containsKey(type);
    }

    @SuppressWarnings("unchecked")
    <T> Deserializer<T> getDeserializer(Class<T> type) {
        Deserializer<T> candidate = (Deserializer<T>) _map.get(type);
        if (candidate != null)
            return candidate;

        if (type.isEnum())
            return new EnumDeserializer<>(type);

        if (type.isArray()) {
            Class<?> componentType = type.getComponentType();
            Deserializer<?> deserializer = getDeserializer(componentType);
            return (Deserializer<T>) new ArrayDeserializer(componentType,
                    deserializer);
        }
        
        if (_useGenericObjectDeserializer) {
            return GenericObjectDeserializer.create(this, type);
        }

        throw new IllegalStateException(String.format(Locale.US,
                "No deserializer for the type '%s'.", type));
    }

}
