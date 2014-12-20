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

import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

import net.karlmartens.platform.test.ExpectedSummary;
import net.karlmartens.platform.test.FileRule;
import net.karlmartens.platform.util.ArrayIterator;
import net.karlmartens.platform.util.NullSafe;

import org.junit.Rule;

/**
 * @author kmartens
 *
 */
public abstract class AbstractFileStreamTest {

  @Rule
  public FileRule file = FileRule.name("", ".dat");

  @Rule
  public ExpectedSummary expectedSummary = ExpectedSummary.none();

  protected final Iterator<Byte> byteIter(byte... bs) {
    return ArrayIterator.valueOf(bs);
  }

  protected final Iterator<Character> charIter(char... cs) {
    return ArrayIterator.valueOf(cs);
  }

  protected final Iterator<Short> shortIter(short... ss) {
    return ArrayIterator.valueOf(ss);
  }

  protected final Iterator<Integer> intIter(int... is) {
    return ArrayIterator.valueOf(is);
  }

  protected final Iterator<Long> longIter(long... ls) {
    return ArrayIterator.valueOf(ls);
  }

  protected final Iterator<Float> floatIter(float... fs) {
    return ArrayIterator.valueOf(fs);
  }

  protected final Iterator<Double> doubleIter(double... ds) {
    return ArrayIterator.valueOf(ds);
  }

  @SafeVarargs
  protected final <T> Iterator<T> objectIter(T... os) {
    return new ArrayIterator<T>(os);
  }

  protected final boolean[] boolArray(boolean... arr) {
    return arr;
  }

  protected final short[] shortArray(short... arr) {
    return arr;
  }

  protected final int[] intArray(int... arr) {
    return arr;
  }

  protected final long[] longArray(long... arr) {
    return arr;
  }

  protected final void summarize(Iterator<?> it) {
    while (it.hasNext()) {
      expectedSummary.actual("%s", it.next());
    }
  }

  protected final void summarizeBoolArrays(Iterator<boolean[]> it) {
    while (it.hasNext()) {
      expectedSummary.actual("%s", Arrays.toString(it.next()));
    }
  }

  protected final void summarizeShortArrays(Iterator<short[]> it) {
    while (it.hasNext()) {
      expectedSummary.actual("%s", Arrays.toString(it.next()));
    }
  }

  protected final void summarizeIntArrays(Iterator<int[]> it) {
    while (it.hasNext()) {
      expectedSummary.actual("%s", Arrays.toString(it.next()));
    }
  }

  protected final void summarizeLongArrays(Iterator<long[]> it) {
    while (it.hasNext()) {
      expectedSummary.actual("%s", Arrays.toString(it.next()));
    }
  }

  protected final void summarizeBytes(Iterator<Byte> it) {
    while (it.hasNext()) {
      int unsigned = it.next() & 0xff;
      expectedSummary.actual("%s", unsigned);
    }
  }

  protected final void summarizeRecords(Iterator<Record> it) {
    while (it.hasNext()) {
      Record r = it.next();
      expectedSummary.actual(
          "%1$4s %2$5s %3$4s %4$5s %5$10s %6$19s %7$10s %8$14s %9$9s %10$10s %11$10s %12$10s",
          r.b == null ? null : r.b & 0xff, //
          NullSafe.toString(r.bool), //
          NullSafe.toString(r.c), //
          NullSafe.toString(r.s), //
          NullSafe.toString(r.i), //
          NullSafe.toString(r.l), //
          NullSafe.formatString(Locale.US, "%1$.6f", r.f), //
          NullSafe.formatString(Locale.US, "%1$.8f", r.d), //
          NullSafe.toString(r.month), //
          NullSafe.toString(r.str), //
          NullSafe.toString(r.strArr), //
          NullSafe.toString(r.strCol)
          );
    }
  }

}
