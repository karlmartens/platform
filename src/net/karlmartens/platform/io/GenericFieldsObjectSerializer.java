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
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import net.karlmartens.platform.io.FileOutputStream.WriteBuffer;

/**
 * @author kmartens
 *
 */
public class GenericFieldsObjectSerializer<T> implements Serializer<T> {

  private final Class<T> _type;
  private final int _nullableBytes;
  private final List<Serializer<Object>> _serializers;

  private GenericFieldsObjectSerializer(Class<T> type, int nullableBytes,
      List<Serializer<Object>> serializers) {
    _type = type;
    _nullableBytes = nullableBytes;
    _serializers = serializers;
  }

  @Override
  public void write(WriteBuffer buffer, T value) {
    byte[] nulls = new byte[_nullableBytes];
    Field[] fields = _type.getFields();
    Object[] objs = new Object[fields.length];
    for (int i = 0; i < fields.length; i++) {
      Object o = get(value, fields[i]);
      objs[i] = o;
      if (o == null) {
        int byteIndex = i / 8;
        int bitShift = i % 8;
        nulls[byteIndex] |= (1 << bitShift);
      }
    }

    buffer.putBytes(ByteBuffer.wrap(nulls));
    for (int i = 0; i < fields.length; i++) {
      Object o = objs[i];
      if (o != null) {
        _serializers.get(i).write(buffer, o);
      }
    }
  }

  private Object get(T value, Field field) {
    try {
      return field.get(value);
    } catch (IllegalArgumentException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> Serializer<T> create(Serializers serializers, Class<T> type) {
    int nullable = 0;
    List<Serializer<Object>> list = new ArrayList<>();
    for (Field field : type.getFields()) {
      Class<?> fieldType = field.getType();
      if (!fieldType.isPrimitive())
        nullable++;

      @SuppressWarnings("unchecked")
      Serializer<Object> serializer = (Serializer<Object>) serializers
          .getSerializer(fieldType);
      list.add(serializer);
    }

    int nullableBytes = (int) Math.ceil(nullable / 8.0);

    return new GenericFieldsObjectSerializer<>(type, nullableBytes, list);
  }

}
