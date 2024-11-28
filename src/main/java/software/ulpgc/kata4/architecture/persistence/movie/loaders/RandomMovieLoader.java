package software.ulpgc.kata4.architecture.persistence.movie.loaders;

import software.ulpgc.kata4.architecture.model.Movie;
import software.ulpgc.kata4.architecture.persistence.DeserializationException;
import software.ulpgc.kata4.architecture.persistence.Loader;

import java.io.IOException;
import java.util.Random;

public class RandomMovieLoader implements Loader<Movie> {

    private final Loader<Movie> loader;
    private final Random random;

    private int step;
    private int maxEntry;
    private Movie nextMovie;

    public RandomMovieLoader(Loader<Movie> loader, long seed) throws IOException, DeserializationException {
        this.loader = loader;
        random = new Random(seed);
        nextRandomNumbers(random);
        nextMovie = nextRandomMovie();
    }

    private void nextRandomNumbers(Random random) {
        step = random.nextInt(10, 30);
        maxEntry = random.nextInt(0, 10_000);
    }

    @Override
    public boolean hasNext() {
        return nextMovie != null;
    }

    @Override
    public Movie load() throws IOException, DeserializationException {
        var result = nextMovie;
        nextMovie = nextRandomMovie();
        return result;
    }

    private Movie nextRandomMovie() throws IOException, DeserializationException {
        Movie movie = loader.load();
        for (int i = 0; i < maxEntry; i++) {
            if (i % step == 0) movie = loader.load();
            else loader.load();
        }
        nextRandomNumbers(random);
        return movie;
    }

    @Override
    public void close() throws IOException {
        loader.close();
    }
}
