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



/**
 * @author kmartens
 *
 */
final class RangeIterator extends UnmodifiableIterator<Integer> {

  private final int _max;
  private final int _step;

  private int _next;

  public RangeIterator(int min, int max, int step) {
    _next = min;
    _max = max;
    _step = step;
  }

  @Override
  public boolean hasNext() {
    return _next <= _max;
  }

  @Override
  public Integer next() {
    check();
    final Integer next = _next;
    _next += _step;
    return next;
  }

}
