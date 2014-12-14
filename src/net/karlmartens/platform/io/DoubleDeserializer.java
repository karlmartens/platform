package net.karlmartens.platform.io;

import net.karlmartens.platform.io.FileStream.ReadBuffer;

public final class DoubleDeserializer implements Deserializer<Double> {

    private static final DoubleDeserializer _INSTANCE = new DoubleDeserializer();

    private DoubleDeserializer() {
        // Reduced visibility
    }

    @Override
    public Double read(ReadBuffer buffer) {
        return buffer.getDouble();
    }

    public static DoubleDeserializer instance() {
        return _INSTANCE;
    }

}
