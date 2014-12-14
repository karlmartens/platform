package net.karlmartens.platform.io;

import java.nio.file.Path;

public final class FileStreamFactory {

    private final Deserializers _deserializers;
    
    private FileStreamFactory(Deserializers deserializers) {
        _deserializers = deserializers;
    }

    public FileStreamFactory useGenericObjectDeserializer() {
        _deserializers.useGenericObjectDeserializer();
        return this;
    }

    public <T> FileStreamFactory addDeserializer(Class<T> type, Deserializer<T> deserializer) {
        _deserializers.addDeserializer(type, deserializer);
        return this;
    }

    public <T> FileStream<T> stream(Path path, Class<T> type) {
        return FileStream.create(path, getDeserializer(type));
    }

    private <T> Deserializer<T> getDeserializer(Class<T> type) {
        return _deserializers.getDeserializer(type);
    }

    public static FileStreamFactory create() {
        return new FileStreamFactory(new Deserializers()//
                .addDeserializer(byte.class, ByteDeserializer.instance()) //
                .addDeserializer(Byte.class, ByteDeserializer.instance()) //
                .addDeserializer(boolean.class, BooleanDeserializer.instance()) //
                .addDeserializer(Boolean.class, BooleanDeserializer.instance()) //
                .addDeserializer(char.class, CharDeserializer.instance()) //
                .addDeserializer(Character.class, CharDeserializer.instance()) //
                .addDeserializer(short.class, ShortDeserializer.instance()) //
                .addDeserializer(Short.class, ShortDeserializer.instance()) //
                .addDeserializer(int.class, IntDeserializer.instance()) //
                .addDeserializer(Integer.class, IntDeserializer.instance()) //
                .addDeserializer(long.class, LongDeserializer.instance()) //
                .addDeserializer(Long.class, LongDeserializer.instance()) //
                .addDeserializer(float.class, FloatDeserializer.instance()) //
                .addDeserializer(Float.class, FloatDeserializer.instance()) //
                .addDeserializer(double.class, DoubleDeserializer.instance()) //
                .addDeserializer(Double.class, DoubleDeserializer.instance()) //
                .addDeserializer(String.class, StringDeserializer.instance()) //
        );
    }

}
