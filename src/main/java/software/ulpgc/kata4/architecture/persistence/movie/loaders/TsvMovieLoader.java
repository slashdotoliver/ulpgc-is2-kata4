package software.ulpgc.kata4.architecture.persistence.movie.loaders;

import software.ulpgc.kata4.architecture.model.entities.Movie;
import software.ulpgc.kata4.architecture.persistence.DeserializationException;
import software.ulpgc.kata4.architecture.persistence.Deserializer;
import software.ulpgc.kata4.architecture.persistence.Loader;
import software.ulpgc.kata4.architecture.persistence.movie.deserializers.TsvMovieDeserializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TsvMovieLoader implements Loader<Movie> {

    private final Deserializer<String, Movie> deserializer;
    private final BufferedReader reader;
    private String nextLine;

    public TsvMovieLoader(File file, boolean hasHeader) throws IOException {
        deserializer = new TsvMovieDeserializer();
        reader = new BufferedReader(new FileReader(file));
        if (hasHeader) readHeader();

        nextLine = reader.readLine();
    }

    @Override
    public Movie load() throws DeserializationException, IOException {
        if (!hasNext()) return null;
        try {
            return deserializer.deserialize(nextLine);
        } finally {
            nextLine = reader.readLine();
        }
    }

    @Override
    public boolean hasNext() {
        return nextLine != null;
    }

    private void readHeader() throws IOException {
        reader.readLine();
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
