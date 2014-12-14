package net.karlmartens.platform.io;

import net.karlmartens.platform.io.FileStream.ReadBuffer;

public final class IntDeserializer implements Deserializer<Integer> {

    private static final IntDeserializer _INSTANCE = new IntDeserializer();
    
    private IntDeserializer() {
        // Reduced visibility
    }

    @Override
    public Integer read(ReadBuffer buffer) {
        return buffer.getInt();
    }

    public static IntDeserializer instance() {
        return _INSTANCE;
    }

}
