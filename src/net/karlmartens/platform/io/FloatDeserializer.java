package net.karlmartens.platform.io;

import net.karlmartens.platform.io.FileStream.ReadBuffer;

public final class FloatDeserializer implements Deserializer<Float> {

    private static final FloatDeserializer _INSTANCE = new FloatDeserializer();

    private FloatDeserializer() {
        // Reduced visibility
    }

    @Override
    public Float read(ReadBuffer buffer) {
        return buffer.getFloat();
    }

    public static FloatDeserializer instance() {
        return _INSTANCE;
    }

}
