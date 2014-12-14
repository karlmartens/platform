package net.karlmartens.platform.io;

import net.karlmartens.platform.io.FileStream.ReadBuffer;

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
