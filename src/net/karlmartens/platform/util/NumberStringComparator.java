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

import java.text.BreakIterator;
import java.text.Collator;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class NumberStringComparator implements Comparator<String> {

  private final NumberFormat _format;
  private final Collator _collator;
  private final double _tolerance;
  private final BreakIterator _bi;

  public NumberStringComparator() {
    this(NumberFormat.getInstance());
  }

  public NumberStringComparator(NumberFormat format) {
    this(format, getDefaultCollator(), 0.00001);
  }

  private static Collator getDefaultCollator() {
    final Collator c = Collator.getInstance();
    c.setStrength(Collator.PRIMARY);
    return c;
  }

  public NumberStringComparator(NumberFormat format, Collator collator,
      double tolerance) {
    _format = format;
    _collator = collator;
    _tolerance = tolerance;
    _bi = BreakIterator.getWordInstance();
  }

  @Override
  public int compare(String o1, String o2) {
    if (o1 == o2)
      return 0;

    if (o1 == null)
      return -1;

    if (o2 == null)
      return 1;

    final String[] w1 = words(o1);
    final String[] w2 = words(o2);

    for (int i = 0; i < Math.min(w1.length, w2.length); i++) {
      try {
        final double n1 = _format.parse(w1[i]).doubleValue();
        final double n2 = _format.parse(w2[i]).doubleValue();
        if (Math.abs(n1 - n2) > _tolerance) {
          if (n1 < n2) {
            return -1;
          }

          return 1;
        }
      } catch (ParseException e) {
        // Ignore error compare string with base comparator
      }

      final int c = _collator.compare(w1[i], w2[i]);
      if (c != 0)
        return c;
    }

    return w1.length - w2.length;
  }

  private String[] words(String text) {
    final List<String> result = new ArrayList<>();
    _bi.setText(text);

    int start = _bi.first();
    int end = _bi.next();
    while (end != BreakIterator.DONE) {
      String word = text.substring(start, end);
      if (Character.isLetterOrDigit(word.charAt(0))) {
        result.add(word);
      }
      start = end;
      end = _bi.next();
    }
    return result.toArray(new String[0]);
  }
}
