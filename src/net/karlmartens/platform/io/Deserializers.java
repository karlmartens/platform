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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author kmartens
 *
 */
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
            return (Deserializer<T>) ArrayDeserializer.create(componentType,
                    deserializer);
        }
        
        if (_useGenericObjectDeserializer) {
            return GenericFieldsObjectDeserializer.create(this, type);
        }

        throw new IllegalStateException(String.format(Locale.US,
                "No deserializer for the type '%s'.", type));
    }

}