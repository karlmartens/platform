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
final class StringConcatenationPartialFunction implements Function<String, String> {
  
  private final String _left;
  private final String _right;
  private final Function<Pair<String,String>, String> _f;

  StringConcatenationPartialFunction(String left, String right) {
    _left = left;
    _right = right;
    _f = StringConcatenationFunction.getInstance();
  }

  @Override
  public String apply(String arg) {
    final String left = coalesce(_left, arg);
    final String right = coalesce(_right, arg);
    return _f.apply(Pair.of(left, right));
  }

}
