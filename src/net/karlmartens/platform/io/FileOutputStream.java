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

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.Iterator;

/**
 * @author kmartens
 *
 */
public class FileOutputStream<T> {

  private final WriteBuffer _buffer;
  private final Serializer<T> _serializer;

  private FileOutputStream(WriteBuffer buffer, Serializer<T> serializer) {
      _buffer = buffer;
      _serializer = serializer;
  }
  
  public void write(Iterator<T> it) {
    while (it.hasNext()) {
      T value = it.next();
      _serializer.write(_buffer, value);
    }
    
    _buffer.write();
    _buffer.close();
  }
  
  public static <T> FileOutputStream<T> create(Path path, Serializer<T> serializer) {
    try {
      FileChannel channel = FileChannel.open(path, CREATE, TRUNCATE_EXISTING, WRITE);
      return new FileOutputStream<>(new WriteBuffer(channel), serializer);
    } catch (IOException ex) {
        throw new RuntimeException(ex);
    }
  }
  
  public static class WriteBuffer {

    private final FileChannel _channel;
    private final ByteBuffer _buffer;
    
    private WriteBuffer(FileChannel channel) {
        _channel = channel;
        _buffer = ByteBuffer.allocate(8096);
        _buffer.flip();
    }

    public void putByte(byte value) {
      checkRequired(1);
      _buffer.put(value);
    }
    
    public void putBytes(ByteBuffer bb) {
      while (bb.hasRemaining()) {
        putByte(bb.get());
      }
    }

    public void putChar(char value) {
      checkRequired(2);
      _buffer.putChar(value);
    }
    
    public void putShort(short value) {
      checkRequired(2);
      _buffer.putShort(value);
    }
    
    public void putInt(int value) {
      checkRequired(4);
      _buffer.putInt(value);
    }
    
    public void putLong(long value) {
      checkRequired(8);
      _buffer.putLong(value);
    }

    public void putFloat(float value) {
      checkRequired(4);
      _buffer.putFloat(value);
    }

    public void putDouble(double value) {
      checkRequired(8);
      _buffer.putDouble(value);
    }
    
    private void checkRequired(int numBytes) {
        if (_buffer.remaining() < numBytes) {
            write();
        }
    }
    
    private void write() {
      _buffer.flip();
      try {
        _channel.write(_buffer);
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
      _buffer.clear();
    }

    private void close() {
      try {
        _channel.close();
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    }
    
  }
  
}
