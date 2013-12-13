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

import java.util.Collection;

/**
 * @author kmartens
 *
 */
final class CollectionConcatenationFunction<T extends Collection<S>, S> implements Function<Pair<T, S>, T> {

  private static final CollectionConcatenationFunction<Collection<Object>, Object> _INSTANCE = new CollectionConcatenationFunction<Collection<Object>, Object>();

  private CollectionConcatenationFunction() {
    // Nothing to do
  }
  
  @Override
  public T apply(Pair<T, S> arg) {
    final T a = arg.a();
    final S b = arg.b();
    a.add(b);
    return a;
  }

  @SuppressWarnings("unchecked")
  public static <T extends Collection<S>, S> CollectionConcatenationFunction<T,S> getInstance() {
    return (CollectionConcatenationFunction<T, S>) _INSTANCE;
  }

}
