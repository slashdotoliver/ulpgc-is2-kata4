package software.ulpgc.kata4.architecture.persistence.movie;

import software.ulpgc.kata4.architecture.model.Movie;
import software.ulpgc.kata4.architecture.persistence.Loader;
import software.ulpgc.kata4.architecture.persistence.movie.loaders.SQLiteMovieLoader;
import software.ulpgc.kata4.architecture.persistence.movie.loaders.TsvMovieLoader;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class MovieLoaderCreator {

    private static final Logger LOGGER = Logger.getLogger(MovieLoaderCreator.class.getName());

    public static Loader<Movie> createTsvWithHeaderMovieLoader(File file) {
        return createTsvMovieLoader(file, true);
    }

    public static Loader<Movie> createTsvWithoutHeaderMovieLoader(File file) {
        return createTsvMovieLoader(file, false);
    }

    private static Loader<Movie> createTsvMovieLoader(File file, boolean hasHeader) {
        try {
            return new TsvMovieLoader(file, hasHeader);
        } catch (IOException e) {
            LOGGER.severe(loaderCreationErrorMessage(TsvMovieLoader.class.getSimpleName(), e));
        }
        return null;
    }

    public static Loader<Movie> createSQLiteMovieLoader(File file) {
        try {
            return new SQLiteMovieLoader(file);
        } catch (IOException e) {
            LOGGER.severe(loaderCreationErrorMessage(SQLiteMovieLoader.class.getSimpleName(), e));
        }
        return null;
    }

    private static String loaderCreationErrorMessage(String loaderName, IOException e) {
        return "Failed while creating a %s with the error: %s".formatted(loaderName, e.getMessage());
    }
}
