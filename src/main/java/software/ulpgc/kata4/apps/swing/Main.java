package software.ulpgc.kata4.apps.swing;

import software.ulpgc.kata4.apps.swing.view.SwingMainFrame;
import software.ulpgc.kata4.architecture.control.HistogramGenerator;
import software.ulpgc.kata4.architecture.control.commands.Command;
import software.ulpgc.kata4.architecture.control.commands.CommandName;
import software.ulpgc.kata4.architecture.control.commands.DisplayRandomMovieCommand;
import software.ulpgc.kata4.architecture.model.Histogram;
import software.ulpgc.kata4.architecture.model.Movie;
import software.ulpgc.kata4.architecture.persistence.DeserializationException;
import software.ulpgc.kata4.architecture.persistence.Loader;
import software.ulpgc.kata4.architecture.persistence.Saver;
import software.ulpgc.kata4.architecture.persistence.SerializationException;
import software.ulpgc.kata4.architecture.persistence.movie.MoviesFileType;
import software.ulpgc.kata4.architecture.persistence.movie.loaders.MovieLoaderCreator;
import software.ulpgc.kata4.architecture.persistence.movie.loaders.MovieLoaderFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final Map<CommandName, Command> COMMANDS = new HashMap<>();
    private static final MovieLoaderFactory MOVIE_LOADER_FACTORY = createMovieLoaderFactory();

    public static void main(String[] args) throws IOException {
        ProgramArguments arguments = new ProgramArguments(args);
        if (!arguments.valid()) exitWithErrorMessage(ProgramArguments.validFormat());

        displayHistogramFrom(arguments.file(), arguments.fileType());
    }

    private static void displayHistogramFrom(File file, MoviesFileType type) throws IOException {
        Optional<Loader<Movie>> movieLoader = MOVIE_LOADER_FACTORY.get(type, file);
        if (movieLoader.isEmpty()) exitWithErrorMessage(
                "Could not found a loader for the file %s with type %s".formatted(file.toString(), type.toString())
        );

        display(from(
                movieLoader.get(),
                m -> Stream.of(m.type()).map(Movie.TitleType::name),
                "Histogram of Type of Movies"
        ));
    }

    private static void display(Histogram histogram) {
        SwingMainFrame mainFrame = new SwingMainFrame(COMMANDS);
        COMMANDS.put(
                CommandName.DisplayRandomMovie, new DisplayRandomMovieCommand(
                        123456789,
                        MOVIE_LOADER_FACTORY,
                        mainFrame.getTypeDialog(),
                        mainFrame.getFileDialog(),
                        mainFrame.getMovieDisplay()
                )
        );
        mainFrame.put(histogram);
        mainFrame.setVisible(true);
    }

    private static void exitWithErrorMessage(String message) {
        System.err.println("Error: " + message);
        System.exit(1);
    }

    private static MovieLoaderFactory createMovieLoaderFactory() {
        return new MovieLoaderFactory()
                .register(MoviesFileType.TSV_WITH_HEADER, MovieLoaderCreator::createTsvWithHeaderMovieLoader)
                .register(MoviesFileType.TSV, MovieLoaderCreator::createTsvWithoutHeaderMovieLoader)
                .register(MoviesFileType.SQLITE, MovieLoaderCreator::createSQLiteMovieLoader);
    }

    private static void readAndWriteMovies(Loader<Movie> loader, Saver<Movie> saver) throws IOException {
        try (loader; saver) {
            while (loader.hasNext()) {
                try {
                    saver.save(loader.load());
                } catch (SerializationException | DeserializationException e) {
                    LOGGER.severe(e.getMessage());
                }
            }
        }
    }

    private static Histogram from(Loader<Movie> loader, Function<Movie, Stream<String>> mapper, String title) throws IOException {
        HistogramGenerator generator = new HistogramGenerator();
        try (loader) {
            while (loader.hasNext()) {
                try {
                    mapper.apply(loader.load()).forEach(generator::feed);
                } catch (DeserializationException e) {
                    LOGGER.severe(e.getMessage());
                }
            }
        }
        return generator.withTitle(title).build();
    }

}
