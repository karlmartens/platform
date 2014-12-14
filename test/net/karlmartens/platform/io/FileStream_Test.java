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

import java.nio.charset.StandardCharsets;
import java.time.Month;
import java.util.Iterator;

import org.junit.Test;

/**
 * @author kmartens
 *
 */
public class FileStream_Test extends AbstractFileStreamTest {

  @Test
  public void testByteFileStream() throws Exception {
    file.putBytes((byte) 0, (byte) 32, (byte) 255);
    file.close();

    Iterator<Byte> it = FileStream.create(file.path(),
        ByteDeserializer.instance());
    summarizeBytes(it);

    expectedSummary.expect("0", "32", "255");
  }

  @Test
  public void testBooleanFileStream() throws Exception {
    file.putBytes((byte) 0, (byte) 1, (byte) 2, (byte) 255);
    file.close();

    Iterator<Boolean> it = FileStream.create(file.path(),
        BooleanDeserializer.instance());
    summarize(it);

    expectedSummary.expect("false", "true", "false", "false");
  }

  @Test
  public void testCharFileStream() throws Exception {
    file.putChars('H', 'o', 'お');
    file.close();

    Iterator<Character> it = FileStream.create(file.path(),
        CharDeserializer.instance());
    summarize(it);

    expectedSummary.expect("H", "o", "お");
  }

  @Test
  public void testStringFileStream() throws Exception {
    putStrings(StandardCharsets.UTF_8, "Hello", "World", "お元気ですか？");
    file.close();

    Iterator<String> it = FileStream.create(file.path(),
        StringDeserializer.instance());
    summarize(it);

    expectedSummary.expect("Hello", "World", "お元気ですか？");
  }

  @Test
  public void testShortFileStream() throws Exception {
    file.putShorts((short) 0, (short) 12, (short) 20456);
    file.close();

    Iterator<Short> it = FileStream.create(file.path(),
        ShortDeserializer.instance());
    summarize(it);

    expectedSummary.expect("0", "12", "20456");
  }

  @Test
  public void testIntFileStream() throws Exception {
    file.putInts(0, 12, 20456);
    file.close();

    Iterator<Integer> it = FileStream.create(file.path(),
        IntDeserializer.instance());
    summarize(it);

    expectedSummary.expect("0", "12", "20456");
  }

  @Test
  public void testLongFileStream() throws Exception {
    file.putLongs(-100000L, 9020456432459916251L, 1234567L);
    file.close();

    Iterator<Long> it = FileStream.create(file.path(),
        LongDeserializer.instance());
    summarize(it);

    expectedSummary.expect("-100000", "9020456432459916251", "1234567");
  }

  @Test
  public void testFloatFileStream() throws Exception {
    file.putFloats(0.12f, 0.45f, 23.456745f);
    file.close();

    Iterator<Float> it = FileStream.create(file.path(),
        FloatDeserializer.instance());
    summarize(it);

    expectedSummary.expect("0.12", "0.45", "23.456745");
  }

  @Test
  public void testDoubleFileStream() throws Exception {
    file.putDoubles(0.12d, 0.45d, 23.4567455d);
    file.close();

    Iterator<Double> it = FileStream.create(file.path(),
        DoubleDeserializer.instance());
    summarize(it);

    expectedSummary.expect("0.12", "0.45", "23.4567455");
  }

  @Test
  public void testEnumFileStream() throws Exception {
    putEnums(Month.values());
    file.close();

    Iterator<Month> it = FileStream.create(file.path(),
        EnumDeserializer.create(Month.class));
    summarize(it);

    expectedSummary.expect("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY",
        "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER",
        "DECEMBER");
  }

  @Test
  public void testRecordFileStream() throws Exception {
    Record r1 = new Record();
    r1.b = (byte) 23;
    r1.bool = true;
    r1.c = 'd';
    r1.s = (short) 32000;
    r1.i = (int) 321000;
    r1.l = (long) 4000000000L;
    r1.f = 12.23f;
    r1.d = 12.34567;
    r1.str = "Hello";
    r1.month = Month.AUGUST;

    Record r2 = new Record();
    r2.b = (byte) 255;
    r2.bool = false;
    r2.c = 'r';
    r2.s = (short) 15000;
    r2.i = (int) 150000;
    r2.l = 8000000000L;
    r2.f = 24.46f;
    r2.d = 24.69134;
    r2.str = "World";
    r2.month = Month.FEBRUARY;

    Record r3 = new Record();
    r3.b = null;
    r3.bool = false;
    r3.c = 'r';
    r3.s = (short) 15000;
    r3.i = (int) 150000;
    r3.l = 8000000000L;
    r3.f = 24.46f;
    r3.d = null;
    r3.str = null;
    r3.month = Month.FEBRUARY;

    putRecords(r1, r2, r3);
    file.close();

    Iterator<Record> it = FileStream.create(file.path(),
        new RecordDeserializer());
    summarizeRecords(it);

    expectedSummary
        .expect(
            //
           "  23  true    d 32000     321000          4000000000  12.230000    12.34567000    AUGUST      Hello,       null", //
           " 255 false    r 15000     150000          8000000000  24.459999    24.69134000  FEBRUARY      World,       null", //
           "null false    r 15000     150000          8000000000  24.459999           null  FEBRUARY       null,       null" //
        );
  }

}
