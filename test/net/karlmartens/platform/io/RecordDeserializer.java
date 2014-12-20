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

import java.time.Month;
import java.util.Collection;

import net.karlmartens.platform.io.FileInputStream.ReadBuffer;

/**
 * @author kmartens
 *
 */
final class RecordDeserializer implements Deserializer<Record> {

  private final ByteDeserializer _byteReader = ByteDeserializer.instance();
  private final BooleanDeserializer _boolReader = BooleanDeserializer
      .instance();
  private final CharDeserializer _charReader = CharDeserializer.instance();
  private final ShortDeserializer _shortReader = ShortDeserializer.instance();
  private final IntDeserializer _intReader = IntDeserializer.instance();
  private final LongDeserializer _longReader = LongDeserializer.instance();
  private final FloatDeserializer _floatReader = FloatDeserializer.instance();
  private final DoubleDeserializer _doubleReader = DoubleDeserializer
      .instance();
  private final StringDeserializer _stringReader = StringDeserializer
      .instance();
  private final EnumDeserializer<Month> _monthReader = EnumDeserializer
      .create(Month.class);
  private final Deserializer<Collection<String>> _strColReader = CollectionDeserializer.<Collection<String>, String>create
      (Collection.class, StringDeserializer.instance());

  @Override
  public Record read(ReadBuffer buffer) {
    byte[] nulls = new byte[2];
    buffer.getBytes(nulls);
    
    Record r = new Record();
    r.b = testNull(0, nulls) ? null : _byteReader.read(buffer);
    r.bool = testNull(1, nulls) ? null : _boolReader.read(buffer);
    r.c = testNull(2, nulls) ? null : _charReader.read(buffer);
    r.s = testNull(3, nulls) ? null : _shortReader.read(buffer);
    r.i = testNull(4, nulls) ? null : _intReader.read(buffer);
    r.l = testNull(5, nulls) ? null : _longReader.read(buffer);
    r.f = testNull(6, nulls) ? null : _floatReader.read(buffer);
    r.d = testNull(7, nulls) ? null : _doubleReader.read(buffer);
    r.str = testNull(8, nulls) ? null : _stringReader.read(buffer);
    r.month = testNull(9, nulls) ? null : _monthReader.read(buffer);
    r.strCol = testNull(10, nulls) ? null : _strColReader.read(buffer);
    return r;
  }

  private boolean testNull(int bitIndex, byte[] nulls) {
    int idx = bitIndex / 8;
    int mask = 1 << (bitIndex % 8);
    return (nulls[idx] & mask) > 0;
  }

}
