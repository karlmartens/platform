/**
 *   Copyright 2013 Karl Martens
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

package net.karlmartens.platform.function;

import java.util.Iterator;

/**
 * @author kmartens
 *
 */
public final class Numbers {
  
  public static Function<Integer, Boolean> even() {
    return EvenIntegerFunction.getInstance();
  }

  public static <T extends Number> Function<Pair<T,T>, Integer> multiply() {
    return MultiplyIntegerFunction.getInstance();
  }

  public static <T extends Number> Function<Pair<T,T>, Integer> subtract() {
    return SubtractIntegerFunction.getInstance();
  }
  
  public static <T extends Number> Function<T, Integer> multiply(int multiplier) {
    return new MultiplyIntegerPartialFunction<>(null, multiplier);
  }
  
  public static Iterable<Integer> range(final int min, final int mox) {
    return range(min, mox, 1);
  }

  public static Iterable<Integer> range(final int min, final int max, final int step) {
    return new Iterable<Integer>() {
      @Override
      public Iterator<Integer> iterator() {
        return new RangeIterator(min, max, step);
      }
    };
  }
  
}
