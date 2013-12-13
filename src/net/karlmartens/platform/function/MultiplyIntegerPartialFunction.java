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

import static net.karlmartens.platform.util.NullSafe.coalesce;

/**
 * @author kmartens
 *
 */
final class MultiplyIntegerPartialFunction<T extends Number> implements Function<T, Integer> {

  private final MultiplyIntegerFunction<Integer> _f = MultiplyIntegerFunction.getInstance();
  private final Integer _multiplcand;
  private final Integer _multiplier;

  public MultiplyIntegerPartialFunction(Integer multiplcand, Integer multiplier) {
    _multiplcand = multiplcand;
    _multiplier = multiplier;
  }

  @Override
  public Integer apply(T arg) {
    final int value = arg.intValue();
    final int a = coalesce(_multiplcand, value);
    final int b = coalesce(_multiplier, value);
    return _f.apply(Pair.of(a, b));
  }

}
