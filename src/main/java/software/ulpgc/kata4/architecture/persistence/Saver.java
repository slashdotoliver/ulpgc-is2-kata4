package software.ulpgc.kata4.architecture.persistence;

import java.io.Closeable;
import java.io.IOException;

public interface Saver<Element> extends Closeable {
    void save(Element value) throws IOException, SerializationException;

    @Override
    void close() throws IOException;
}
