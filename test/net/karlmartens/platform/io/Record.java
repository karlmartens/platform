/**
 *   Copyright 2014 Karl Martens
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

package net.karlmartens.platform.io;

import java.time.Month;
import java.util.Collection;
import java.util.Map;

/**
 * @author kmartens
 *
 */
final class Record {

  public Byte b;
  public Boolean bool;
  public Character c;
  public Short s;
  public Integer i;
  public Long l;
  public Float f;
  public Double d;
  public String str;
  public Month month;
  public String[] strArr;
  public Collection<String> strCol;
  public Map<Character, Integer> map;

}
