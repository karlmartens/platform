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
public class EnumDeserializer<T> implements Deserializer<T> {
    
    private final Class<T> _type;
    private final IntDeserializer _intDeserializer;

    EnumDeserializer(Class<T> type) {
        _type = type;        
        _intDeserializer = IntDeserializer.instance();
    }

    @Override
    public T read(ReadBuffer buffer) {
        int ordinal = _intDeserializer.read(buffer);
        return _type.getEnumConstants()[ordinal];
    }
    
    public static <T extends Enum<T>> EnumDeserializer<T> create(Class<T> type) {
        return new EnumDeserializer<>(type);
    }

}
