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
final class MultiplyIntegerFunction<T extends Number> implements Function<Pair<T, T>, Integer> {

  private static final MultiplyIntegerFunction<Number> _INSTANCE = new MultiplyIntegerFunction<>();

  private MultiplyIntegerFunction() {
    // Nothing to do
  }
  
  @Override
  public Integer apply(Pair<T, T> arg) {
    return Integer.valueOf(arg.a().intValue() * arg.b().intValue());
  }

  @SuppressWarnings("unchecked")
  public static <T extends Number> MultiplyIntegerFunction<T> getInstance() {
    return (MultiplyIntegerFunction<T>) _INSTANCE;
  }

}
