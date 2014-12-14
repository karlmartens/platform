package net.karlmartens.platform.io;

import net.karlmartens.platform.io.FileStream.ReadBuffer;

public final class ShortDeserializer implements Deserializer<Short> {

    private static final ShortDeserializer _INSTANCE = new ShortDeserializer();
    
    private ShortDeserializer() {
        // Reduced visibility
    }

    @Override
    public Short read(ReadBuffer buffer) {
        return buffer.getShort();
    }

    public static ShortDeserializer instance() {
        return _INSTANCE;
    }

}
