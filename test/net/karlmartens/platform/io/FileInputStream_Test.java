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
import java.util.Iterator;

import org.junit.Test;

/**
 * @author kmartens
 *
 */
public class FileInputStream_Test extends AbstractFileStreamTest {

  @Test
  public void testByteFileStream() throws Exception {
    FileOutputStream.create(file.path(), ByteSerializer.instance()).write(
        byteIter((byte) 0, (byte) 32, (byte) 255));

    Iterator<Byte> it = FileInputStream.create(file.path(),
        ByteDeserializer.instance());
    summarizeBytes(it);

    expectedSummary.expect("0", "32", "255");
  }

  @Test
  public void testBooleanFileStream() throws Exception {
    FileOutputStream.create(file.path(), ByteSerializer.instance()).write(
        byteIter((byte) 0, (byte) 1, (byte) 2, (byte) 255));

    Iterator<Boolean> it = FileInputStream.create(file.path(),
        BooleanDeserializer.instance());
    summarize(it);

    expectedSummary.expect("false", "true", "false", "false");
  }

  @Test
  public void testCharFileStream() throws Exception {
    FileOutputStream.create(file.path(), CharSerializer.instance()).write(
        charIter('H', 'o', 'お'));

    Iterator<Character> it = FileInputStream.create(file.path(),
        CharDeserializer.instance());
    summarize(it);

    expectedSummary.expect("H", "o", "お");
  }

  @Test
  public void testStringFileStream() throws Exception {
    FileOutputStream.create(file.path(), StringSerializer.instance()).write(
        objectIter("Hello", "World", "お元気ですか？"));

    Iterator<String> it = FileInputStream.create(file.path(),
        StringDeserializer.instance());
    summarize(it);

    expectedSummary.expect("Hello", "World", "お元気ですか？");
  }

  @Test
  public void testShortFileStream() throws Exception {
    FileOutputStream.create(file.path(), ShortSerializer.instance()).write(
        shortIter((short) 0, (short) 12, (short) 20456));

    Iterator<Short> it = FileInputStream.create(file.path(),
        ShortDeserializer.instance());
    summarize(it);

    expectedSummary.expect("0", "12", "20456");
  }

  @Test
  public void testIntFileStream() throws Exception {
    FileOutputStream.create(file.path(), IntSerializer.instance()).write(
        intIter(0, 12, 20456));

    Iterator<Integer> it = FileInputStream.create(file.path(),
        IntDeserializer.instance());
    summarize(it);

    expectedSummary.expect("0", "12", "20456");
  }

  @Test
  public void testLongFileStream() throws Exception {
    FileOutputStream.create(file.path(), LongSerializer.instance()).write(
        longIter(-100000L, 9020456432459916251L, 1234567L));

    Iterator<Long> it = FileInputStream.create(file.path(),
        LongDeserializer.instance());
    summarize(it);

    expectedSummary.expect("-100000", "9020456432459916251", "1234567");
  }

  @Test
  public void testFloatFileStream() throws Exception {
    FileOutputStream.create(file.path(), FloatSerializer.instance()).write(
        floatIter(0.12f, 0.45f, 23.456745f));

    Iterator<Float> it = FileInputStream.create(file.path(),
        FloatDeserializer.instance());
    summarize(it);

    expectedSummary.expect("0.12", "0.45", "23.456745");
  }

  @Test
  public void testDoubleFileStream() throws Exception {
    FileOutputStream.create(file.path(), DoubleSerializer.instance()).write(
        doubleIter(0.12d, 0.45d, 23.4567455d));

    Iterator<Double> it = FileInputStream.create(file.path(),
        DoubleDeserializer.instance());
    summarize(it);

    expectedSummary.expect("0.12", "0.45", "23.4567455");
  }

  @Test
  public void testEnumFileStream() throws Exception {
    FileOutputStream.create(file.path(), EnumSerializer.instance()).write(
        objectIter(Month.values()));

    Iterator<Month> it = FileInputStream.create(file.path(),
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

    FileOutputStream.create(file.path(), new RecordSerializer()).write(
        objectIter(r1, r2, r3));

    Iterator<Record> it = FileInputStream.create(file.path(),
        new RecordDeserializer());
    summarizeRecords(it);

    expectedSummary
        .expect(
            //
            "  23  true    d 32000     321000          4000000000  12.230000    12.34567000    AUGUST      Hello       null", //
            " 255 false    r 15000     150000          8000000000  24.459999    24.69134000  FEBRUARY      World       null", //
            "null false    r 15000     150000          8000000000  24.459999           null  FEBRUARY       null       null" //
        );
  }

}
