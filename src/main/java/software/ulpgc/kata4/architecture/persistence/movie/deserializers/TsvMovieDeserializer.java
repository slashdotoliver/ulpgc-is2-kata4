package software.ulpgc.kata4.architecture.persistence.movie.deserializers;

import software.ulpgc.kata4.architecture.model.entities.Movie;
import software.ulpgc.kata4.architecture.persistence.DeserializationException;
import software.ulpgc.kata4.architecture.persistence.Deserializer;

import java.time.Duration;
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TsvMovieDeserializer implements Deserializer<String, Movie> {

    private static final String EMPTY_FIELD = "\\N";

    @Override
    public Movie deserialize(String line) throws DeserializationException {
        String[] columns = line.split("\t");
        try {
            return new Movie(
                    columns[0],
                    type(format(columns[1])),
                    columns[2],
                    columns[3],
                    isAdult(format(columns[4])),
                    year(format(columns[5])),
                    year(format(columns[6])),
                    duration(format(columns[7])),
                    genres(format(columns[8]))
            );
        } catch (
                ArrayIndexOutOfBoundsException |
                IllegalArgumentException |
                DateTimeParseException exception
        ) {
            throw new DeserializationException(exception);
        }
    }

    private static String format(String value) {
        return value.trim().toUpperCase().replace('-', '_');
    }

    private static List<Movie.Genre> genres(String fields) throws IllegalArgumentException {
        if (fields.equals(EMPTY_FIELD)) return Collections.emptyList();
        return Arrays
                .stream(fields.split(","))
                .map(Movie.Genre::valueOf)
                .toList();
    }

    private static Movie.TitleType type(String column) throws IllegalArgumentException {
        return Movie.TitleType.valueOf(column);
    }

    private static Optional<Duration> duration(String minutes) throws DateTimeParseException {
        return minutes.equals(EMPTY_FIELD) ? Optional.empty() : Optional.of(Duration.parse("PT" + minutes + "M"));
    }

    private static Optional<Year> year(String column) throws DateTimeParseException {
        return column.equals(EMPTY_FIELD) ? Optional.empty() : Optional.of(Year.parse(column));
    }

    private static boolean isAdult(String column) {
        return column.equals("1");
    }
}
