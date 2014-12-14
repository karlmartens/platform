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

import net.karlmartens.platform.io.FileStream.ReadBuffer;

/**
 * @author kmartens
 *
 */
public class ByteDeserializer implements Deserializer<Byte> {

    private static final ByteDeserializer _INSTANCE = new ByteDeserializer();
    
    private ByteDeserializer() {
        // Reduced visibility
    }

    @Override
    public Byte read(ReadBuffer buffer) {
        return buffer.getByte();
    }

    public static ByteDeserializer instance() {
        return _INSTANCE;
    }

}
