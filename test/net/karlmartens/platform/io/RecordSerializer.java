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

import java.nio.ByteBuffer;
import java.util.Collection;

import net.karlmartens.platform.io.FileOutputStream.WriteBuffer;

/**
 * @author kmartens
 *
 */
final class RecordSerializer implements Serializer<Record> {

  @Override
  public void write(WriteBuffer buffer, Record r) {
    Object[] objs = { r.b, r.bool, r.c, r.s, r.i, r.l, r.f, r.d, r.str, r.month, r.strArr, r.strCol };
    byte[] nulls = new byte[((int) Math.ceil(objs.length / 8.0))];
    for (int i = 0; i < objs.length; i++) {
      if (objs[i] == null) {
        int idx = i / 8;
        int bit = i % 8;
        nulls[idx] |= 1 << bit;
      }
    }
    buffer.putBytes(ByteBuffer.wrap(nulls));

    for (Object o : objs) {
      putObject(buffer, o);
    }
  }

  private final void putObject(WriteBuffer buffer, Object o) {
    if (o == null)
      return;
    
    Class<?> type = o.getClass(); 
    @SuppressWarnings("unchecked")
    Serializer<Object> serializer = (Serializer<Object>) serializerForType(type);
    serializer.write(buffer, o);
  }

  private Serializer<?> serializerForType(Class<?> type) {
    if (Byte.class.equals(type))
      return ByteSerializer.instance();
    
    if (Boolean.class.equals(type))
      return BooleanSerializer.instance();

    if (Character.class.equals(type))
      return CharSerializer.instance();
    
    if (Short.class.equals(type))
      return ShortSerializer.instance();

    if (Integer.class.equals(type))
      return IntSerializer.instance();
      
    if (Long.class.equals(type))
      return LongSerializer.instance();
    
    if (Float.class.equals(type))
      return FloatSerializer.instance();

    if (Double.class.equals(type))
      return DoubleSerializer.instance();

    if (String.class.equals(type))
      return StringSerializer.instance();
    
    if (type.isEnum())
      return EnumSerializer.instance();
    
    if (type.isArray()) {
      Class<?> componentType = type.getComponentType();
      Serializer<?> componentSerializer = serializerForType(componentType);
      return ArraySerializer.create(componentSerializer);
    }
    
    if (Collection.class.isAssignableFrom(type)) {
      return CollectionSerializer.create(StringSerializer.instance());
    }

      throw new IllegalStateException();
  }

}
