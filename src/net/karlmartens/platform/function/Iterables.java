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
 * @param <T>
 *
 */
public final class Iterables {
  
  private Iterables() {
    // Nothing to do
  }
  
  public static <T,S> Iterable<S> map(final Iterable<? extends T> it, final Function<T,S> f) {
    return new Iterable<S>() {
      @Override
      public Iterator<S> iterator() {
        return new MappingIterator<>(it.iterator(), f);
      }
    };
  }
  
  public static <T> Iterable<T> reverse(final Iterable<? extends T> it) {
    return new Iterable<T>() {
      @Override
      public Iterator<T> iterator() {
        return new ReversedIterator<>(it.iterator());
      }
    };
  }
  
  @SafeVarargs
  public static <T> Iterable<T> riffle(final Iterable<? extends T> it, final T... obs) {
    return new Iterable<T>() {
      @Override
      public Iterator<T> iterator() {
        return new RiffleIterator<>(it.iterator(), obs);
      }
    };
  }
  
  public static <T> Iterable<T> select(final Iterable<? extends T> it, final Function<T,Boolean> criteria) {
    return new Iterable<T>() {
      @Override
      public Iterator<T> iterator() {
        return new FilteredIterator<>(it.iterator(), criteria);
      }
    };
  }

  public static <T, S> S foldLeft(Iterable<? extends T> it, S initial, Function<Pair<S, T>, S> f) {
    S acc = initial;
    for (T el : it) {
      acc = f.apply(Pair.of(acc, el));
    }
    return acc;
  }
  
  public static <T,S> S foldRight(Iterable<? extends T> it, S initial, Function<Pair<T, S>, S> f) {
    S acc = initial;
    for (T el : reverse(it)) {
      acc = f.apply(Pair.of(el, acc));
    }
    return acc;
  }
  
  public static <T> T head(Iterable<? extends T> it) {
    return it.iterator().next();
  }
  
  public static <T> Iterable<T> tail(final Iterable<T> it) {
    return new Iterable<T>() {
      @Override
      public Iterator<T> iterator() {
        final Iterator<T> newIterator = it.iterator();
        newIterator.next();
        return newIterator;
      }
    };
  }
  
  public static <T extends Comparable<T>> T max(Iterable<? extends T> it) {
    return foldLeft(tail(it), head(it), Comparables.<T>max());
  }
  
  public static <T extends Comparable<T>> T min(Iterable<T> it) {
    return foldLeft(tail(it), head(it), Comparables.<T>min());
  }

}
