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

import java.util.Comparator;

/**
 * @author kmartens
 *
 */
class MaxFunction<T> implements Function<Pair<T, T>, T> {

  private final Comparator<T> _comparator;

  MaxFunction(Comparator<T> comparator) {
    _comparator = comparator;
  }
  
  @Override
  public T apply(Pair<T, T> arg) {
    final T a = arg.a();
    final T b = arg.b();
    if (_comparator.compare(a, b) >= 0)
      return a;
    
    return b;
  }

}
