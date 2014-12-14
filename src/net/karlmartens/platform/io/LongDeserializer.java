package net.karlmartens.platform.io;

import net.karlmartens.platform.io.FileStream.ReadBuffer;

public final class LongDeserializer implements Deserializer<Long> {

    private static final LongDeserializer _INSTANCE = new LongDeserializer();

    private LongDeserializer() {
        // Reduced visibility
    }

    @Override
    public Long read(ReadBuffer buffer) {
        return buffer.getLong();
    }

    public static LongDeserializer instance() {
        return _INSTANCE;
    }

}
