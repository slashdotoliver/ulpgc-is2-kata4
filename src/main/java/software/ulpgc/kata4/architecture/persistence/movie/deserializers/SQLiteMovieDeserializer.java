package software.ulpgc.kata4.architecture.persistence.movie.deserializers;

import software.ulpgc.kata4.architecture.model.Movie;
import software.ulpgc.kata4.architecture.persistence.DeserializationException;
import software.ulpgc.kata4.architecture.persistence.Deserializer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SQLiteMovieDeserializer implements Deserializer<ResultSet, Movie> {
    @Override
    public Movie deserialize(ResultSet movies) throws DeserializationException {
        try {
            return new Movie(
                    movies.getString(1),
                    type(movies.getString(2)),
                    movies.getString(3),
                    movies.getString(4),
                    isAdult(movies.getString(5)),
                    year(movies.getString(6)),
                    year(movies.getString(7)),
                    duration(movies.getString(8)),
                    genres(movies.getString(9))
            );
        } catch (
                IllegalArgumentException |
                DateTimeParseException |
                SQLException e
        ) {
            throw new DeserializationException(e);
        }
    }

    private Optional<Duration> duration(String field) throws DateTimeParseException {
        if (field == null) return Optional.empty();
        return Optional.of(Duration.parse(field));
    }

    private Optional<Year> year(String field) throws DateTimeParseException {
        if (field == null) return Optional.empty();
        return Optional.of(Year.parse(field));
    }

    private boolean isAdult(String field) {
        return field.equals("true");
    }

    private List<Movie.Genre> genres(String fields) throws IllegalArgumentException {
        if (fields.isBlank()) return Collections.emptyList();
        return Arrays.stream(fields.split(","))
                .map(Movie.Genre::valueOf)
                .toList();
    }

    private Movie.TitleType type(String field) throws IllegalArgumentException {
        return Movie.TitleType.valueOf(field);
    }
}
