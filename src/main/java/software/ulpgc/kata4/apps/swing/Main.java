package software.ulpgc.kata4.apps.swing;

import software.ulpgc.kata4.apps.swing.view.MainFrame;
import software.ulpgc.kata4.architecture.control.HistogramGenerator;
import software.ulpgc.kata4.architecture.model.Histogram;
import software.ulpgc.kata4.architecture.model.Movie;
import software.ulpgc.kata4.architecture.persistence.DeserializationException;
import software.ulpgc.kata4.architecture.persistence.Loader;
import software.ulpgc.kata4.architecture.persistence.Saver;
import software.ulpgc.kata4.architecture.persistence.SerializationException;
import software.ulpgc.kata4.architecture.persistence.movie.loaders.TsvMovieLoader;
import software.ulpgc.kata4.architecture.persistence.movie.savers.SQLiteMovieSaver;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {
        ProgramArguments arguments = new ProgramArguments(args);
        if (!arguments.valid()) exitWithErrorMessage(ProgramArguments.validFormat());

        Loader<Movie> reader = new TsvMovieLoader(arguments.file(), true);
        display(from(
                reader,
                m -> Stream.of(m.type()).map(Movie.TitleType::name),
                "Histogram of Type of Movies"
        ));
    }

    private static void display(Histogram histogram) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.put(histogram);
        mainFrame.setVisible(true);
    }

    private static void exitWithErrorMessage(String message) {
        System.err.println("Error: " + message);
        System.exit(1);
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
