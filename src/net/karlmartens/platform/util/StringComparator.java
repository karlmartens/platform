/**
 *   Copyright 2011 Karl Martens
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

import java.util.Comparator;

public final class StringComparator implements Comparator<String> {

  private final boolean _ignoreCase;

  public StringComparator(boolean ignoreCase) {
    _ignoreCase = ignoreCase;
  }

  @Override
  public int compare(String o1, String o2) {
    if (o1 == o2)
      return 0;

    if (o1 == null)
      return -1;

    if (o2 == null)
      return 1;

    if (_ignoreCase) {
      return o1.compareToIgnoreCase(o2);
    }

    return o1.compareTo(o2);
  }

}
