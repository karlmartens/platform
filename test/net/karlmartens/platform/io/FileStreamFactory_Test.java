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
import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeMap;

import org.junit.Test;

/**
 * @author kmartens
 *
 */
public class FileStreamFactory_Test extends AbstractFileStreamTest {

  private FileStreamFactory _factory = FileStreamFactory.create();

  @Test
  public void testByteFileStream() throws Exception {
    _factory.outputStream(file.path(), Byte.class).write(
        byteIter((byte) 0, (byte) 32, (byte) 255));

    Iterator<Byte> it = _factory.inputStream(file.path(), Byte.class);
    summarizeBytes(it);

    expectedSummary.expect("0", "32", "255");
  }

  @Test
  public void testBooleanFileStream() throws Exception {
    _factory.outputStream(file.path(), Byte.class).write(
        byteIter((byte) 0, (byte) 1, (byte) 2, (byte) 255));

    Iterator<Boolean> it = _factory.inputStream(file.path(), Boolean.class);
    summarize(it);

    expectedSummary.expect("false", "true", "false", "false");
  }

  @Test
  public void testCharFileStream() throws Exception {
    _factory.outputStream(file.path(), Character.class).write(
        charIter('H', 'o', 'お'));

    Iterator<Character> it = _factory.inputStream(file.path(), Character.class);
    summarize(it);

    expectedSummary.expect("H", "o", "お");
  }

  @Test
  public void testStringFileStream() throws Exception {
    _factory.outputStream(file.path(), String.class).write(
        objectIter("Hello", "World", "お元気ですか？"));

    Iterator<String> it = _factory.inputStream(file.path(), String.class);
    summarize(it);

    expectedSummary.expect("Hello", "World", "お元気ですか？");
  }

  @Test
  public void testShortFileStream() throws Exception {
    _factory.outputStream(file.path(), Short.class).write(
        shortIter((short) 0, (short) 12, (short) 20456));

    Iterator<Short> it = _factory.inputStream(file.path(), Short.class);
    summarize(it);

    expectedSummary.expect("0", "12", "20456");
  }

  @Test
  public void testIntFileStream() throws Exception {
    _factory.outputStream(file.path(), Integer.class).write(
        intIter(0, 12, 20456));

    Iterator<Integer> it = _factory.inputStream(file.path(), Integer.class);
    summarize(it);

    expectedSummary.expect("0", "12", "20456");
  }

  @Test
  public void testLongFileStream() throws Exception {
    _factory.outputStream(file.path(), Long.class).write(
        longIter(-100000L, 9020456432459916251L, 1234567L));

    Iterator<Long> it = _factory.inputStream(file.path(), Long.class);
    summarize(it);

    expectedSummary.expect("-100000", "9020456432459916251", "1234567");
  }

  @Test
  public void testFloatFileStream() throws Exception {
    _factory.outputStream(file.path(), Float.class).write(
        floatIter(0.12f, 0.45f, 23.456745f));

    Iterator<Float> it = _factory.inputStream(file.path(), Float.class);
    summarize(it);

    expectedSummary.expect("0.12", "0.45", "23.456745");
  }

  @Test
  public void testDoubleFileStream() throws Exception {
    _factory.outputStream(file.path(), Double.class).write(
        doubleIter(0.12d, 0.45d, 23.4567455d));

    Iterator<Double> it = _factory.inputStream(file.path(), Double.class);
    summarize(it);

    expectedSummary.expect("0.12", "0.45", "23.4567455");
  }

  @Test
  public void testEnumFileStream() throws Exception {
    _factory.outputStream(file.path(), Month.class).write(
        objectIter(Month.values()));

    Iterator<Month> it = _factory//
        .useGenericObjectSerialization()//
        .inputStream(file.path(), Month.class);
    summarize(it);

    expectedSummary.expect("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY",
        "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER",
        "DECEMBER");
  }

  @Test
  public void testBoolArrayFileStream() throws Exception {
    _factory.outputStream(file.path(), boolean[].class).write(
        objectIter(boolArray(true, false), boolArray(false, true)));

    Iterator<boolean[]> it = _factory.inputStream(file.path(), boolean[].class);
    summarizeBoolArrays(it);

    expectedSummary.expect("[true, false]", "[false, true]");
  }

  @Test
  public void testShortArrayFileStream() throws Exception {
    _factory.outputStream(file.path(), short[].class).write(
        objectIter(shortArray((short) 3, (short) 6, (short) 4),
            shortArray((short) 1, (short) 2, (short) 3)));

    Iterator<short[]> it = _factory.inputStream(file.path(), short[].class);
    summarizeShortArrays(it);

    expectedSummary.expect("[3, 6, 4]", "[1, 2, 3]");
  }

  @Test
  public void testIntArrayFileStream() throws Exception {
    _factory.outputStream(file.path(), int[].class).write(
        objectIter(intArray(3, 6, 4), intArray(1, 2, 3)));

    Iterator<int[]> it = _factory.inputStream(file.path(), int[].class);
    summarizeIntArrays(it);

    expectedSummary.expect("[3, 6, 4]", "[1, 2, 3]");
  }

  @Test
  public void testLongArrayFileStream() throws Exception {
    _factory.outputStream(file.path(), long[].class).write(
        objectIter(longArray(3, 6, 4), longArray(1, 2, 3)));

    Iterator<long[]> it = _factory.inputStream(file.path(), long[].class);
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
    r1.strArr = new String[] { "A", "B", "C" };
    r1.strCol = Arrays.asList("1", "2", "3");
    r1.map = new TreeMap<>();
    r1.map.put('A', 1);
    r1.map.put('B', 2);
    r1.map.put('C', 3);

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

    _factory.useGenericObjectSerialization()
        .outputStream(file.path(), Record.class).write(objectIter(r1, r2, r3));

    Iterator<Record> it = _factory.inputStream(file.path(), Record.class);

    summarizeRecords(it);

    expectedSummary
        .expect(
            //
            "  23  true    d 32000     321000          4000000000  12.230000    12.34567000    AUGUST      Hello  [A, B, C]  [1, 2, 3] {A=1, B=2, C=3}", //
            " 255 false    r 15000     150000          8000000000  24.459999    24.69134000  FEBRUARY      World       null       null            null", //
            "null false    r 15000     150000          8000000000  24.459999           null  FEBRUARY       null         []       null            null" //
        );
  }

}
