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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import net.karlmartens.platform.io.FileInputStream.ReadBuffer;

/**
 * @author kmartens
 *
 */
public class CollectionDeserializer<T extends Collection<? extends S>, S>
    implements Deserializer<T> {

  private final Class<?> _colType;
  private final Deserializer<?> _deserializer;

  private CollectionDeserializer(Class<?> colType, Deserializer<?> deserializer) {
    _colType = colType;
    _deserializer = deserializer;
  }

  @SuppressWarnings("unchecked")
  @Override
  public T read(ReadBuffer buffer) {
    final Collection<Object> list = createCollection();
    int size = buffer.getInt();
    for (int i = 0; i < size; i++) {
      Object obj = _deserializer.read(buffer);
      list.add(obj);
    }
    return (T) list;
  }

  @SuppressWarnings("unchecked")
  private Collection<Object> createCollection() {
    if (!_colType.isInterface()) {
      try {
        return (Collection<Object>) _colType.newInstance();
      } catch (IllegalAccessException | InstantiationException ex) {
        throw new RuntimeException(ex);
      }
    }

    if (SortedSet.class.isAssignableFrom(_colType))
      return new TreeSet<>();

    if (Set.class.isAssignableFrom(_colType))
      return new LinkedHashSet<>();

    if (Queue.class.isAssignableFrom(_colType))
      return new LinkedList<>();

    return new ArrayList<>();
  }

  public static <T extends Collection<S>, S> Deserializer<T> create(
      Class<?> type, Deserializer<?> deserializer) {
    return new CollectionDeserializer<T, S>(type, deserializer);
  }

}
