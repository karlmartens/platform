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

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author kmartens
 *
 */
final class RiffleIterator<T> extends UnmodifiableIterator<T> implements
    Iterator<T> {

  private final Iterator<? extends T> _it;
  private final T[] _obs;
  private final Queue<T> _queue;

  private int _i;

  public RiffleIterator(Iterator<? extends T> it, T[] obs) {
    _it = it;
    _obs = Arrays.copyOf(obs, obs.length);
    _queue = new LinkedList<T>();
    _i = 0;
    forward();
  }

  @Override
  public boolean hasNext() {
    return !_queue.isEmpty();
  }

  @Override
  public T next() {
    check();
    final T next = _queue.poll();
    if (_queue.isEmpty())
      forward();
    return next;
  }

  private void forward() {
    if (!_it.hasNext())
      return;
    
    _queue.add(_it.next());
    
    if (_it.hasNext()) {
      _queue.add(_obs[_i]);
      _i = (_i + 1) % _obs.length;
    }    
  }

}
