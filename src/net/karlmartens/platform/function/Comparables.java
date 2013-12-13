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

import net.karlmartens.platform.util.ComparableComparator;

/**
 * @author kmartens
 *
 */
public final class Comparables {
  
  private Comparables() {
    // Nothing to do
  }

  public static <T extends Comparable<T>> Function<Pair<T, T>, T> max() {
    return new MaxFunction<>(new ComparableComparator<T>());
  }
  
  public static <T extends Comparable<T>> Function<Pair<T, T>, T> min() {
    return new MinFunction<>(new ComparableComparator<T>());
  }
  

}
