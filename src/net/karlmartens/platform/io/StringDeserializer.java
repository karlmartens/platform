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

import net.karlmartens.platform.io.FileInputStream.ReadBuffer;

/**
 * @author kmartens
 *
 */
public class StringDeserializer implements Deserializer<String> {

    private static final StringDeserializer _INSTANCE = new StringDeserializer();

    private final IntDeserializer _intDeserializer;
    
    private StringDeserializer() {
        _intDeserializer = IntDeserializer.instance();
    }

    @Override
    public String read(ReadBuffer buffer) {
        int size = _intDeserializer.read(buffer);
        ByteBuffer bb = ByteBuffer.wrap(buffer.getBytes(new byte[size]));
        return StandardCharsets.UTF_8.decode(bb).toString();
    }

    public static StringDeserializer instance() {
        return _INSTANCE;
    }

}
