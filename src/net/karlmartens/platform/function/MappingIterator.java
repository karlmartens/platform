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

import java.util.Iterator;

/**
 * @author kmartens
 *
 */
final class MappingIterator<T, S> extends UnmodifiableIterator<S> {

  private Iterator<? extends T> _it;
  private Function<T, S> _f;

  public MappingIterator(Iterator<? extends T> it, Function<T, S> f) {
    _it = it;
    _f = f;
  }

  @Override
  public boolean hasNext() {
    return _it.hasNext();
  }

  @Override
  public S next() {
    return _f.apply(_it.next());
  }

}
