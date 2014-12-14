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

package net.karlmartens.platform.test;

import static java.nio.file.StandardOpenOption.APPEND;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author kmartens
 *
 */
public class FileRule implements TestRule {

    private final Path _path;
    private final ByteBuffer _buffer;

    private FileChannel _channel;

    private FileRule(Path path) {
        _path = path;
        _buffer = ByteBuffer.allocate(1024);
    }

    public Path path() {
        return _path;
    }

    public void putByte(byte value) {
        if (_buffer.remaining() < 1)
            flush();

        _buffer.put(value);
    }

    public void putShort(short value) {
        if (_buffer.remaining() < 2)
            flush();

        _buffer.putShort(value);
    }

    public void putInt(int value) {
        if (_buffer.remaining() < 4)
            flush();

        _buffer.putInt(value);
    }

    public void putLong(long value) {
        if (_buffer.remaining() < 8)
            flush();

        _buffer.putLong(value);
    }

    public void putFloat(float value) {
        if (_buffer.remaining() < 4)
            flush();

        _buffer.putFloat(value);
    }

    public void putDouble(double value) {
        if (_buffer.remaining() < 4)
            flush();

        _buffer.putDouble(value);
    }

    public void putChar(char value) {
        if (_buffer.remaining() < 4)
            flush();

        _buffer.putChar(value);
    }

    public void put(ByteBuffer bb) {
        while (bb.hasRemaining()) {
            if (!_buffer.hasRemaining())
                flush();

            _buffer.put(bb.get());
        }
    }

    public void putBytes(byte... bytes) {
        for (Byte b : bytes) {
            putByte(b);
        }
    }

    public void putChars(char... chars) {
        for (char c : chars) {
            putChar(c);
        }
    }

    public void putShorts(short... shorts) {
        for (short s : shorts) {
            putShort(s);
        }
    }

    public void putInts(int... ints) {
        for (int i : ints) {
            putInt(i);
        }
    }

    public void putLongs(long... longs) {
        for (long l : longs) {
            putLong(l);
        }
    }

    public void putFloats(float... floats) {
        for (float f : floats) {
            putFloat(f);
        }
    }

    public void putDoubles(double... doubles) {
        for (double d : doubles) {
            putDouble(d);
        }
    }

    private void flush() {
        _buffer.flip();

        try {
            _channel.write(_buffer);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        _buffer.clear();
    }

    public void close() {
        flush();

        try {
            _channel.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                _channel = FileChannel.open(_path, APPEND);
                try {
                    base.evaluate();
                } finally {
                    _channel.close();
                    Files.deleteIfExists(_path);
                }
            }
        };
    }

    public static FileRule name(String prefix, String postfix) {
        try {
            return new FileRule(Files.createTempFile(prefix, postfix));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
