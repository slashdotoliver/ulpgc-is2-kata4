package software.ulpgc.kata4.architecture.commands;

import software.ulpgc.kata4.architecture.model.entities.Movie;
import software.ulpgc.kata4.architecture.persistence.DeserializationException;
import software.ulpgc.kata4.architecture.persistence.Loader;
import software.ulpgc.kata4.architecture.persistence.movie.MoviesFileType;
import software.ulpgc.kata4.architecture.persistence.movie.loaders.MovieLoaderFactory;
import software.ulpgc.kata4.architecture.persistence.movie.loaders.RandomMovieLoader;
import software.ulpgc.kata4.architecture.view.MovieDisplay;
import software.ulpgc.kata4.architecture.view.MovieFileDialog;
import software.ulpgc.kata4.architecture.view.MovieFileTypeDialog;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

public class DisplayRandomMovieCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger(DisplayRandomMovieCommand.class.getName());

    private final MovieLoaderFactory loaderFactory;
    private final MovieFileTypeDialog fileTypeDialog;
    private final MovieFileDialog fileDialog;
    private final MovieDisplay movieDisplay;

    private long seed;

    public DisplayRandomMovieCommand(
            long initialSeed,
            MovieLoaderFactory loaderFactory,
            MovieFileTypeDialog fileTypeDialog,
            MovieFileDialog fileDialog,
            MovieDisplay movieDisplay
    ) {
        this.seed = initialSeed;
        this.loaderFactory = loaderFactory;
        this.fileTypeDialog = fileTypeDialog;
        this.fileDialog = fileDialog;
        this.movieDisplay = movieDisplay;
    }

    @Override
    public void execute() {
        MoviesFileType type = fileTypeDialog.get();
        Optional<File> file = fileDialog.get();
        if (file.isEmpty()) return;

        Optional<Loader<Movie>> loader = loaderFactory.get(type, file.get());
        if (loader.isEmpty()) return;
        Optional<Movie> result = randomMovie(loader.get());

        if (result.isEmpty()) return;
        movieDisplay.show(result.get());
    }

    private Optional<Movie> randomMovie(Loader<Movie> loader) {
        try (Loader<Movie> randomMovieLoader = new RandomMovieLoader(loader, seed++)) {
            return Optional.ofNullable(randomMovieLoader.load());
        } catch (IOException | DeserializationException e) {
            LOGGER.severe("Failed loading or deserializing a movie: " + e.getMessage());
        }
        return Optional.empty();
    }
}
