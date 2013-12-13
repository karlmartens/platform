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
import java.util.HashSet;

/**
 * @author kmartens
 *
 */
public final class Criteria {
  
  private Criteria() {
    // Nothing to do
  }
  
  public static final Function<Object, Boolean> ALL = new Function<Object, Boolean>() {
    @Override
    public Boolean apply(Object arg) {
      return Boolean.TRUE;
    }
  };
  
  public static final Function<Object, Boolean> NONE = new Function<Object, Boolean>() {
    @Override
    public Boolean apply(Object arg) {
      return Boolean.FALSE;
    }
  };
  
  @SuppressWarnings("unchecked")
  public static <T> Function<T, Boolean> all() {
    return (Function<T, Boolean>) ALL;
  }
  
  @SuppressWarnings("unchecked")
  public static <T> Function<T, Boolean> none() {
    return (Function<T, Boolean>) NONE;
  }
  
  
  public static <T> Function<T, Boolean> and(final Function<? super T, Boolean> f1, final Function<? super T, Boolean> f2) {
    return new Function<T, Boolean>() {
      @Override
      public Boolean apply(T arg) {
        if (!Boolean.TRUE.equals(f1.apply(arg)))
          return false;
        
        return Boolean.TRUE.equals(f2.apply(arg));
      }
    };
  }
  
  public static <T> Function<T, Boolean> or(final Function<? super T, Boolean> f1, final Function<? super T, Boolean> f2) {
    return new Function<T, Boolean>() {
      @Override
      public Boolean apply(T arg) {
        if (Boolean.TRUE.equals(f1.apply(arg)))
          return true;
        
        return Boolean.TRUE.equals(f2.apply(arg));
      }
    };
  }

  public static <T> Function<T, Boolean> not(final Function<? super T, Boolean> f) {
    return new Function<T, Boolean>() {
      @Override
      public Boolean apply(T arg) {
        return !Boolean.TRUE.equals(f.apply(arg));
      }
    };
  }
  
  public static <T> Function<T, Boolean> accepting(Iterable<? extends T> it) {
    final HashSet<T> accepts = Iterables.foldLeft(it, new HashSet<T>(), Collections.<HashSet<T>, T>concat());
    return new Function<T, Boolean>() {
      @Override
      public Boolean apply(T arg) {
        return accepts.contains(arg);
      }
    };
  }
  
  public static <T> Function<T, Boolean> accepting(T[] arr) {
    return accepting(Arrays.asList(arr));
  }
  
}
