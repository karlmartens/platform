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

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import net.karlmartens.platform.io.FileOutputStream.WriteBuffer;

/**
 * @author kmartens
 *
 */
public class StringSerializer implements Serializer<String> {

  private static final StringSerializer _INSTANCE = new StringSerializer();
  
  private StringSerializer() {
    // Reduced visibility
  }

  @Override
  public void write(WriteBuffer buffer, String value) {
    ByteBuffer bb = StandardCharsets.UTF_8.encode(value);
    buffer.putInt(bb.limit());
    buffer.putBytes(bb);
  }
  
  public static StringSerializer instance() {
    return _INSTANCE;
  }

}
