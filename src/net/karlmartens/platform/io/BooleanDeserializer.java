package net.karlmartens.platform.io;

import net.karlmartens.platform.io.FileStream.ReadBuffer;

public final class BooleanDeserializer implements Deserializer<Boolean> {

    private static final BooleanDeserializer _INSTANCE = new BooleanDeserializer();
    
    private BooleanDeserializer() {
        // Reduced visibility
    }

    @Override
    public Boolean read(ReadBuffer buffer) {
        return buffer.getByte() == 1;
    }

    public static BooleanDeserializer instance() {
        return _INSTANCE;
    }

}
