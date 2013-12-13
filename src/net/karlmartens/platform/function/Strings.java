/**
 *   Copyright 2012 Karl Martens
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

import static net.karlmartens.platform.function.Iterables.foldLeft;
import static net.karlmartens.platform.function.Iterables.map;
import static net.karlmartens.platform.function.Iterables.riffle;

import java.util.Arrays;
/**
 * @author karl
 * 
 */
public final class Strings {
  
  public static <T extends Object> Function<T, String> asString() {
    return ToStringFunction.getInstance();
  }

  public static Function<Pair<String, String>, String> concat() {
    return StringConcatenationFunction.getInstance();
  }
  
  public static Function<String, String> append(final String str) {
    return new StringConcatenationPartialFunction(null, str);
  }
  
  public static String intersperse(Iterable<?> it, String str) {
    return foldLeft(riffle(map(it, asString()), str), "", concat());
  }

  public static String lines(String... lines) {
    return lines(Arrays.asList(lines));
  }

  public static String lines(Iterable<String> list) {
    return foldLeft(map(list, append("\n")), null, concat());
  }

}
