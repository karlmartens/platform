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
class Serializers {

  private final Map<Class<?>, Serializer<?>> _map = new HashMap<>();
  private boolean _useGenericObjectSerializer = false;

  void useGenericObjectSerializer() {
    _useGenericObjectSerializer = true;
  }

  <T> Serializers addSerializer(Class<T> type, Serializer<T> serializer) {
    _map.put(type, serializer);
    return this;
  }

  @SuppressWarnings("unchecked")
  <T> Serializer<T> getSerializer(Class<T> type) {
    Serializer<T> candidate = (Serializer<T>) _map.get(type);
    if (candidate != null)
      return candidate;

    if (type.isEnum())
      return (Serializer<T>) EnumSerializer.instance();

    if (type.isArray()) {
      Class<?> componentType = type.getComponentType();
      Serializer<?> serializer = getSerializer(componentType);
      return (Serializer<T>) ArraySerializer.create(serializer);
    }

    if (_useGenericObjectSerializer) {
      return GenericFieldsObjectSerializer.create(this, type);
    }

    throw new IllegalStateException(String.format(Locale.US,
        "No serializer for the type '%s'.", type));
  }

}
