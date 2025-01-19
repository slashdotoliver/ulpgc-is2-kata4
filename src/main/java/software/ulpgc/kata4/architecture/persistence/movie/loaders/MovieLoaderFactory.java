package software.ulpgc.kata4.architecture.persistence.movie.loaders;

import software.ulpgc.kata4.architecture.model.entities.Movie;
import software.ulpgc.kata4.architecture.persistence.Loader;
import software.ulpgc.kata4.architecture.persistence.movie.MoviesFileType;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MovieLoaderFactory {

    private final Map<MoviesFileType, MovieLoaderBuilder> loaderBuilders = new HashMap<>();

    public Optional<Loader<Movie>> get(MoviesFileType type, File file) {
        return Optional.ofNullable(loaderBuilders.get(type).build(file));
    }

    public MovieLoaderFactory register(MoviesFileType type, MovieLoaderBuilder builder) {
        loaderBuilders.put(type, builder);
        return this;
    }

    public interface MovieLoaderBuilder {
        Loader<Movie> build(File file);
    }

}
