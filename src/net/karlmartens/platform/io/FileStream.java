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

import static java.nio.file.StandardOpenOption.READ;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author kmartens
 *
 */
public class FileStream<T> implements Iterator<T> {

	private final ReadBuffer _buffer;
	private final Deserializer<T> _deserializer;
	
	private boolean _hasNext = true;
	private T _next;

	private FileStream(ReadBuffer buffer, Deserializer<T> deserializer) {
		_buffer = buffer;
		_deserializer = deserializer;
		advance();
	}
	
	@Override
	public boolean hasNext() {
		return _hasNext;
	}

	@Override
	public T next() {
		if (!hasNext())
			throw new NoSuchElementException();
		
		final T next = _next;
		advance();
		return next;
	}
	
	private void advance() {
		if (!_buffer.hasRemaining()) {
		        _hasNext = false;
		        _next = null;
			return;
		}
		
		_next = _deserializer.read(_buffer);
	}

	public static <T> FileStream<T> create(Path path, Deserializer<T> deserializer) {
		try {
			FileChannel channel = FileChannel.open(path, READ);
			return new FileStream<>(new ReadBuffer(channel), deserializer);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static class ReadBuffer {

		private final FileChannel _channel;
		private final ByteBuffer _buffer;
		
		private ReadBuffer(FileChannel channel) {
			_channel = channel;
			_buffer = ByteBuffer.allocate(8096);
			_buffer.flip();
		}
		
		public boolean hasRemaining() {
			if (!_buffer.hasRemaining()) {
				read();
			}
			return _buffer.hasRemaining();
		}
		
		public byte getByte() {
			checkRequired(1);
			return _buffer.get();
		}
		
		public char getChar() {
			checkRequired(2);
			return _buffer.getChar();
		}
		
		public short getShort() {
			checkRequired(2);
			return _buffer.getShort();
		}
		
		public int getInt() {
			checkRequired(4);
			return _buffer.getInt();
		}
		
		public long getLong() {
			checkRequired(8);
			return _buffer.getLong();
		}
		
		public float getFloat() {
			checkRequired(4);
			return _buffer.getFloat();
		}
		
		public double getDouble() {
			checkRequired(8);
			return _buffer.getDouble();
		}
		
		public byte[] getBytes(byte[] arr) {
			int offset = 0;
			while (offset < arr.length) {
				if (!_buffer.hasRemaining())
					read();
				
				int length = Math.min(_buffer.remaining(), arr.length - offset);
				if (length == 0)
					throw new BufferUnderflowException();
				
				_buffer.get(arr, offset, length);
				offset += length;
			}
			return arr;
		}
		
		private void checkRequired(int numBytes) {
			if (_buffer.remaining() < numBytes) {
				read();
			}
		}

		private void read() {
			_buffer.compact();
			try {
				_channel.read(_buffer);
				_buffer.flip();
			} catch (IOException e) {
				throw new RuntimeException();
			}
		}		
	}
	
	
}
