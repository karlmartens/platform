package net.karlmartens.platform.io;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import net.karlmartens.platform.io.FileStream.ReadBuffer;

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
