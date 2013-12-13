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
final class SubtractIntegerFunction <T extends Number> implements Function<Pair<T, T>, Integer> {

  private static final SubtractIntegerFunction<Number> _INSTANCE = new SubtractIntegerFunction<>();

  private SubtractIntegerFunction() {
    // Nothing to do
  }
  
  @Override
  public Integer apply(Pair<T, T> arg) {
    return Integer.valueOf(arg.a().intValue() - arg.b().intValue());
  }

  @SuppressWarnings("unchecked")
  public static <T extends Number> SubtractIntegerFunction<T> getInstance() {
    return (SubtractIntegerFunction<T>) _INSTANCE;
  }

}
