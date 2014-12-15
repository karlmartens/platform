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

package net.karlmartens.platform.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author kmartens
 *
 */
public class ArrayIterator<T> implements Iterator<T> {

  private int _i;
  private T[] _arr;

  public ArrayIterator(T[] arr) {
    _arr = arr;
    _i = 0;
  }

  @Override
  public boolean hasNext() {
    return _arr != null && _i < _arr.length;
  }

  @Override
  public T next() {
    if (!hasNext())
      throw new NoSuchElementException();
    
    T next = _arr[_i++];
    if (_i >= _arr.length) {
      _arr = null;
    }

    return next;
  }

  public static Iterator<Byte> valueOf(byte... bs) {
    Byte[] arr = new Byte[bs.length];
    for (int i=0; i<arr.length; i++) {
      arr[i] = bs[i];
    }
    return new ArrayIterator<Byte>(arr);
  }

  public static Iterator<Character> valueOf(char... cs) {
    Character[] arr = new Character[cs.length];
    for (int i=0; i<arr.length; i++) {
      arr[i] = cs[i];
    }
    return new ArrayIterator<Character>(arr);
  }

  public static Iterator<Short> valueOf(short... ss) {
    Short[] arr = new Short[ss.length];
    for (int i=0; i<arr.length; i++) {
      arr[i] = ss[i];
    }
    return new ArrayIterator<Short>(arr);
  }

  public static Iterator<Integer> valueOf(int... is) {
    Integer[] arr = new Integer[is.length];
    for (int i=0; i<arr.length; i++) {
      arr[i] = is[i];
    }
    return new ArrayIterator<Integer>(arr);
  }

  public static Iterator<Long> valueOf(long... ls) {
    Long[] arr = new Long[ls.length];
    for (int i=0; i<arr.length; i++) {
      arr[i] = ls[i];
    }
    return new ArrayIterator<Long>(arr);
  }

  public static Iterator<Float> valueOf(float... fs) {
    Float[] arr = new Float[fs.length];
    for (int i=0; i<arr.length; i++) {
      arr[i] = fs[i];
    }
    return new ArrayIterator<Float>(arr);
  }

  public static Iterator<Double> valueOf(double... ds) {
    Double[] arr = new Double[ds.length];
    for (int i=0; i<arr.length; i++) {
      arr[i] = ds[i];
    }
    return new ArrayIterator<Double>(arr);
  }

}
