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
public class FileStreamFactory_Test extends AbstractFileStreamTest {

  private FileStreamFactory _factory = FileStreamFactory.create();

  @Test
  public void testByteFileStream() throws Exception {
    file.putBytes((byte) 0, (byte) 32, (byte) 255);
    file.close();

    Iterator<Byte> it = _factory.stream(file.path(), Byte.class);
    summarizeBytes(it);

    expectedSummary.expect("0", "32", "255");
  }

  @Test
  public void testBooleanFileStream() throws Exception {
    file.putBytes((byte) 0, (byte) 1, (byte) 2, (byte) 255);
    file.close();

    Iterator<Boolean> it = _factory.stream(file.path(), Boolean.class);
    summarize(it);

    expectedSummary.expect("false", "true", "false", "false");
  }

  @Test
  public void testCharFileStream() throws Exception {
    file.putChars('H', 'o', 'お');
    file.close();

    Iterator<Character> it = _factory.stream(file.path(), Character.class);
    summarize(it);

    expectedSummary.expect("H", "o", "お");
  }

  @Test
  public void testStringFileStream() throws Exception {
    putStrings(StandardCharsets.UTF_8, "Hello", "World", "お元気ですか？");
    file.close();

    Iterator<String> it = _factory.stream(file.path(), String.class);
    summarize(it);

    expectedSummary.expect("Hello", "World", "お元気ですか？");
  }

  @Test
  public void testShortFileStream() throws Exception {
    file.putShorts((short) 0, (short) 12, (short) 20456);
    file.close();

    Iterator<Short> it = _factory.stream(file.path(), Short.class);
    summarize(it);

    expectedSummary.expect("0", "12", "20456");
  }

  @Test
  public void testIntFileStream() throws Exception {
    file.putInts(0, 12, 20456);
    file.close();

    Iterator<Integer> it = _factory.stream(file.path(), Integer.class);
    summarize(it);

    expectedSummary.expect("0", "12", "20456");
  }

  @Test
  public void testLongFileStream() throws Exception {
    file.putLongs(-100000L, 9020456432459916251L, 1234567L);
    file.close();

    Iterator<Long> it = _factory.stream(file.path(), Long.class);
    summarize(it);

    expectedSummary.expect("-100000", "9020456432459916251", "1234567");
  }

  @Test
  public void testFloatFileStream() throws Exception {
    file.putFloats(0.12f, 0.45f, 23.456745f);
    file.close();

    Iterator<Float> it = _factory.stream(file.path(), Float.class);
    summarize(it);

    expectedSummary.expect("0.12", "0.45", "23.456745");
  }

  @Test
  public void testDoubleFileStream() throws Exception {
    file.putDoubles(0.12d, 0.45d, 23.4567455d);
    file.close();

    Iterator<Double> it = _factory.stream(file.path(), Double.class);
    summarize(it);

    expectedSummary.expect("0.12", "0.45", "23.4567455");
  }

  @Test
  public void testEnumFileStream() throws Exception {
    putEnums(Month.values());
    file.close();

    Iterator<Month> it = _factory//
        .useGenericObjectDeserializer()//
        .stream(file.path(), Month.class);
    summarize(it);

    expectedSummary.expect("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY",
        "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER",
        "DECEMBER");
  }

  @Test
  public void testBoolArrayFileStream() throws Exception {
    putBoolArrays(boolArray(true, false), boolArray(false, true));
    file.close();

    Iterator<boolean[]> it = _factory.stream(file.path(), boolean[].class);
    summarizeBoolArrays(it);

    expectedSummary.expect("[true, false]", "[false, true]");
  }

  @Test
  public void testShortArrayFileStream() throws Exception {
    putShortArrays(shortArray((short) 3, (short) 6, (short) 4),
        shortArray((short) 1, (short) 2, (short) 3));
    file.close();

    Iterator<short[]> it = _factory.stream(file.path(), short[].class);
    summarizeShortArrays(it);

    expectedSummary.expect("[3, 6, 4]", "[1, 2, 3]");
  }

  @Test
  public void testIntArrayFileStream() throws Exception {
    putIntArrays(intArray(3, 6, 4), intArray(1, 2, 3));
    file.close();

    Iterator<int[]> it = _factory.stream(file.path(), int[].class);
    summarizeIntArrays(it);

    expectedSummary.expect("[3, 6, 4]", "[1, 2, 3]");
  }

  @Test
  public void testLongArrayFileStream() throws Exception {
    putLongArrays(longArray(3, 6, 4), longArray(1, 2, 3));
    file.close();

    Iterator<long[]> it = _factory.stream(file.path(), long[].class);
    summarizeLongArrays(it);

    expectedSummary.expect("[3, 6, 4]", "[1, 2, 3]");
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
    r1.strArr = new String[] {"A", "B", "C"};

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
    r3.strArr = new String[] {};

    putRecords(r1, r2, r3);
    file.close();

    Iterator<Record> it = _factory//
        .useGenericObjectDeserializer() //
        .stream(file.path(), Record.class);

    summarizeRecords(it);

    expectedSummary
        .expect(
            //
            "  23  true    d 32000     321000          4000000000  12.230000    12.34567000    AUGUST      Hello  [A, B, C]", //
            " 255 false    r 15000     150000          8000000000  24.459999    24.69134000  FEBRUARY      World       null", //
            "null false    r 15000     150000          8000000000  24.459999           null  FEBRUARY       null         []" //
        );
  }

}
