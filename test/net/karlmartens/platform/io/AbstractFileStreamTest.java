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

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

import net.karlmartens.platform.test.ExpectedSummary;
import net.karlmartens.platform.test.FileRule;
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
          "%1$4s %2$5s %3$4s %4$5s %5$10s %6$19s %7$10s %8$14s %9$9s %10$10s %11$10s",
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
          NullSafe.toString(r.strArr) //
          );
    }
  }

  protected final void putBool(boolean bool) {
    file.putByte(bool ? (byte) 1 : (byte) 0);
  }

  protected final void putBools(boolean... bools) {
    for (boolean b : bools) {
      putBool(b);
    }
  }

  protected final void putBoolArray(boolean... arr) {
    file.putInt(arr.length);
    putBools(arr);
  }

  protected final void putBoolArrays(boolean[]... bs) {
    for (boolean[] b : bs) {
      putBoolArray(b);
    }
  }

  protected final void putString(Charset charset, String str) {
    ByteBuffer bb = charset.encode(str);
    file.putInt(bb.remaining());
    file.put(bb);
  }

  protected final void putStrings(Charset charset, String... strs) {
    for (String str : strs) {
      putString(charset, str);
    }
  }

  protected final <T extends Enum<T>> void putEnum(T evalue) {
    file.putInt(evalue.ordinal());
  }

  protected final <T extends Enum<T>> void putEnums(
      @SuppressWarnings("unchecked") T... evalues) {
    for (T evalue : evalues) {
      putEnum(evalue);
    }
  }

  protected final void putShortArray(short... arr) {
    file.putInt(arr.length);
    file.putShorts(arr);
  }

  protected final void putShortArrays(short[]... ss) {
    for (short[] s : ss) {
      putShortArray(s);
    }
  }

  protected final void putIntArray(int... arr) {
    file.putInt(arr.length);
    file.putInts(arr);
  }

  protected final void putIntArrays(int[]... as) {
    for (int[] a : as) {
      putIntArray(a);
    }
  }

  protected final void putLongArray(long... arr) {
    file.putInt(arr.length);
    file.putLongs(arr);
  }

  protected final void putLongArrays(long[]... ls) {
    for (long[] l : ls) {
      putLongArray(l);
    }
  }

  protected final void putObject(Object o) {
    if (o == null)
      return;

    Class<?> type = o.getClass();
    if (Byte.class.equals(type)) { 
      file.putByte((Byte) o);
    } else if (Boolean.class.equals(type)) {
      putBool((Boolean) o);
    } else if (Character.class.equals(type)) {
      file.putChar((Character)o);
    } else if (Short.class.equals(type)) {
      file.putShort((Short)o);
    } else if (Integer.class.equals(type)) {
      file.putInt((Integer)o);
    } else if (Long.class.equals(type)) {
      file.putLong((Long)o);
    } else if (Float.class.equals(type)) {
      file.putFloat((Float)o);
    } else if (Double.class.equals(type)) {
      file.putDouble((Double)o);
    } else if (String.class.equals(type)) {
      putString(StandardCharsets.UTF_8, (String)o);
    } else if (type.isEnum()) {
      file.putInt(((Enum<?>)o).ordinal());
    } else if (type.isArray()) { 
      int length = Array.getLength(o);
      file.putInt(length);
      for (int i = 0; i<length; i++) {
        putObject(Array.get(o, i));
      }
    } else {
      throw new IllegalStateException();
    }
  }

  protected final void putRecord(Record r) {
    Object[] objs = { r.b, r.bool, r.c, r.s, r.i, r.l, r.f, r.d, r.str, r.month, r.strArr };
    byte[] nulls = new byte[((int) Math.ceil(objs.length / 8.0))];
    for (int i = 0; i < objs.length; i++) {
      if (objs[i] == null) {
        int idx = i / 8;
        int bit = i % 8;
        nulls[idx] |= 1 << bit;
      }
    }
    file.putBytes(nulls);

    for (Object o : objs) {
      putObject(o);
    }
  }

  protected final void putRecords(Record... records) {
    for (Record r : records) {
      putRecord(r);
    }
  }

}
