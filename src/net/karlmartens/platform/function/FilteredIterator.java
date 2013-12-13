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

import static java.lang.Boolean.TRUE;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author kmartens
 *
 */
final class FilteredIterator<T> extends UnmodifiableIterator<T> {

  private final Iterator<? extends T> _it;
  private final Function<T, Boolean> _criteria;
  private T _next;

  public FilteredIterator(Iterator<? extends T> it, Function<T, Boolean> criteria) {
    _it = it;
    _criteria = criteria;
    forward();
  }

  @Override
  public boolean hasNext() {
    return _next != null;
  }

  @Override
  public T next() {
    if (!hasNext())
      throw new NoSuchElementException();
    
    final T next = _next;
    forward();
    return next;
  }

  private void forward() {
    while (_it.hasNext()) {
      T candidate = _it.next();
      if (TRUE.equals(_criteria.apply(candidate))) {
        _next = candidate;
        return;
      }
    }
    _next = null;
  }

}
