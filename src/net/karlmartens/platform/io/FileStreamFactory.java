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

import java.nio.file.Path;

/**
 * @author kmartens
 *
 */
public final class FileStreamFactory {

    private final Deserializers _deserializers;
    
    private FileStreamFactory(Deserializers deserializers) {
        _deserializers = deserializers;
    }

    public FileStreamFactory useGenericObjectDeserializer() {
        _deserializers.useGenericObjectDeserializer();
        return this;
    }

    public <T> FileStreamFactory addDeserializer(Class<T> type, Deserializer<T> deserializer) {
        _deserializers.addDeserializer(type, deserializer);
        return this;
    }

    public <T> FileStream<T> stream(Path path, Class<T> type) {
        return FileStream.create(path, getDeserializer(type));
    }

    private <T> Deserializer<T> getDeserializer(Class<T> type) {
        return _deserializers.getDeserializer(type);
    }

    public static FileStreamFactory create() {
        return new FileStreamFactory(new Deserializers()//
                .addDeserializer(byte.class, ByteDeserializer.instance()) //
                .addDeserializer(Byte.class, ByteDeserializer.instance()) //
                .addDeserializer(boolean.class, BooleanDeserializer.instance()) //
                .addDeserializer(Boolean.class, BooleanDeserializer.instance()) //
                .addDeserializer(char.class, CharDeserializer.instance()) //
                .addDeserializer(Character.class, CharDeserializer.instance()) //
                .addDeserializer(short.class, ShortDeserializer.instance()) //
                .addDeserializer(Short.class, ShortDeserializer.instance()) //
                .addDeserializer(int.class, IntDeserializer.instance()) //
                .addDeserializer(Integer.class, IntDeserializer.instance()) //
                .addDeserializer(long.class, LongDeserializer.instance()) //
                .addDeserializer(Long.class, LongDeserializer.instance()) //
                .addDeserializer(float.class, FloatDeserializer.instance()) //
                .addDeserializer(Float.class, FloatDeserializer.instance()) //
                .addDeserializer(double.class, DoubleDeserializer.instance()) //
                .addDeserializer(Double.class, DoubleDeserializer.instance()) //
                .addDeserializer(String.class, StringDeserializer.instance()) //
        );
    }

}
