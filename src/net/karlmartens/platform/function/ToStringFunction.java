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
final class ToStringFunction<T> implements Function<T, String> {

  private static final Function<Object, String> _INSTANCE = new ToStringFunction<Object>();

  private ToStringFunction() {
    // Nothing to do
  }
  
  @Override
  public String apply(T arg) {
    return arg.toString();
  }

  @SuppressWarnings("unchecked")
  public static <T> Function<T, String> getInstance() {
    return (Function<T, String>) _INSTANCE ;
  }

}
