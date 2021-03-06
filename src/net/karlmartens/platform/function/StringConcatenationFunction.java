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
final class StringConcatenationFunction implements Function<Pair<String, String>, String> {
  
  private static final StringConcatenationFunction _INSTANCE = new StringConcatenationFunction();

  private StringConcatenationFunction() {
    // Nothing to do
  }

  @Override
  public String apply(Pair<String, String> arg) {
    final String a = arg.a();
    final String b = arg.b();
    if (a == null)
      return b;
    
    if (b == null)
      return a;
    
    return new StringBuilder() //
      .append(a) //
      .append(b) //
      .toString();
  }

  public static StringConcatenationFunction getInstance() {
    return _INSTANCE;
  }
}
