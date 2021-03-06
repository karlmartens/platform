/**
 *   Copyright 2010,2011 Karl Martens
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

package net.karlmartens.platform.util;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Locale;

public final class NullSafe {

  private NullSafe() {
    // Utility class
  }
  
  @SafeVarargs
  public static <T> T coalesce(T... obs) {
    for (T ob : obs) {
      if (ob != null)
        return ob;
    }
    return null;
  }

  public static String toString(Object value) {
    if (value == null)
      return null;
    
    if (value.getClass().isArray())
      return arrayToString(value);
    
    return value.toString();
  }

  private static String arrayToString(Object value) {
    if (value == null)
      return null;
    
    StringBuilder builder = new StringBuilder();
    builder.append("[");
    for (int i=0; i<Array.getLength(value); i++) {
      if (i>0) {
        builder.append(", ");
      }
      
      Object o = Array.get(value, i);
      builder.append(toString(o));
    }
    builder.append("]");
    
    return builder.toString();
  }

  public static String formatString(Locale locale, String format, Object value) {
    if (value == null)
      return null;

    return String.format(locale, format, value);
  }

  public static boolean equals(Object o1, Object o2) {
    if (o1 == o2)
      return true;

    if (o1 == null || o2 == null)
      return false;

    return o1.equals(o2);
  }

  public static <T> T min(T a, T b, Comparator<T> comparator) {
    if (comparator.compare(a, b) <= 0)
      return a;

    return b;
  }

  public static <T extends Comparable<T>> T min(T a, T b) {
    final Comparator<T> comparator = ComparableComparator.getInstance();
    return min(a, b, comparator);
  }

  public static <T> T max(T a, T b, Comparator<T> comparator) {
    if (comparator.compare(a, b) <= 0)
      return b;

    return a;
  }

  public static <T extends Comparable<T>> T max(T a, T b) {
    final Comparator<T> comparator = ComparableComparator.getInstance();
    return max(a, b, comparator);
  }
}
