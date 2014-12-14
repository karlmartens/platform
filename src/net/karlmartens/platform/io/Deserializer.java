package net.karlmartens.platform.io;

import net.karlmartens.platform.io.FileStream.ReadBuffer;

public interface Deserializer<T> {

	T read(ReadBuffer buffer);

}
