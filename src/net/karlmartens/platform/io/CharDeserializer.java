package net.karlmartens.platform.io;

import net.karlmartens.platform.io.FileStream.ReadBuffer;

public final class CharDeserializer implements Deserializer<Character> {

    private static final CharDeserializer _INSTANCE = new CharDeserializer();

    private CharDeserializer() {
        // Reduced visibility
    }

    @Override
    public Character read(ReadBuffer buffer) {
        return buffer.getChar();
    }

    public static CharDeserializer instance() {
        return _INSTANCE;
    }

}
