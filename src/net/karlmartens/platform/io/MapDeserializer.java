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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import net.karlmartens.platform.io.FileInputStream.ReadBuffer;

/**
 * @author kmartens
 *
 */
public final class MapDeserializer<T extends Map<K, V>, K, V> implements
    Deserializer<T> {

  private final Class<?> _type;
  private final Deserializer<K> _keyDeserializer;
  private final Deserializer<V> _valueDeserializer;

  private MapDeserializer(Class<?> type, Deserializer<K> keyDeserializer,
      Deserializer<V> valueDeserializer) {
    _type = type;
    _keyDeserializer = keyDeserializer;
    _valueDeserializer = valueDeserializer;
  }

  @SuppressWarnings("unchecked")
  @Override
  public T read(ReadBuffer buffer) {
    int size = buffer.getInt();
    Map<K, V> map = createMap();
    for (int i = 0; i < size; i++) {
      K key = _keyDeserializer.read(buffer);
      V value = _valueDeserializer.read(buffer);
      map.put(key, value);
    }
    return (T) map;
  }

  @SuppressWarnings("unchecked")
  private Map<K, V> createMap() {
    if (!_type.isInterface()) {
      try {
        return (T) _type.newInstance();
      } catch (InstantiationException | IllegalAccessException ex) {
        throw new RuntimeException(ex);
      }
    }

    if (SortedMap.class.isAssignableFrom(_type)) {
      return new TreeMap<>();
    }

    return new LinkedHashMap<>();
  }

  public static <T extends Map<K, V>, K, V> MapDeserializer<T, K, V> create(
      Class<?> type, Deserializer<K> keyDeserializer,
      Deserializer<V> valueDeserializer) {
    return new MapDeserializer<T, K, V>(type, keyDeserializer,
        valueDeserializer);
  }

}
