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

import java.util.Map;
import java.util.Map.Entry;

import net.karlmartens.platform.io.FileOutputStream.WriteBuffer;

/**
 * @author kmartens
 *
 */
public final class MapSerializer<K, V> implements Serializer<Map<K, V>> {

  private final Serializer<K> _keySerializer;
  private final Serializer<V> _valueSerializer;

  private MapSerializer(Serializer<K> keySerializer,
      Serializer<V> valueSerializer) {
        _keySerializer = keySerializer;
        _valueSerializer = valueSerializer;
  }
  
  @Override
  public void write(WriteBuffer buffer, Map<K, V> value) {
    buffer.putInt(value.size());
    for (Entry<K, V> e : value.entrySet()) {
      _keySerializer.write(buffer, e.getKey());
      _valueSerializer.write(buffer, e.getValue());
    }
  }

  public static <K, V> MapSerializer<K, V> create(Serializer<K> keySerializer,
      Serializer<V> valueSerializer) {
    return new MapSerializer<>(keySerializer, valueSerializer);
  }

}
