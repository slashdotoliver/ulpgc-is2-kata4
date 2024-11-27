package software.ulpgc.kata4.architecture.persistence;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public interface Loader<Element> extends Closeable {
    boolean hasNext() throws IOException;

    Element load() throws IOException, DeserializationException;

    @Override
    void close() throws IOException;
}
