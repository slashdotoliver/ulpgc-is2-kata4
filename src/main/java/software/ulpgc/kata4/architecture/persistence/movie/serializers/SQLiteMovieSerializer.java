package software.ulpgc.kata4.architecture.persistence.movie.serializers;

import software.ulpgc.kata4.architecture.model.Movie;
import software.ulpgc.kata4.architecture.persistence.DeserializationException;
import software.ulpgc.kata4.architecture.persistence.SerializationException;
import software.ulpgc.kata4.architecture.persistence.Serializer;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Year;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class SQLiteMovieSerializer implements Serializer<Movie, PreparedStatement>, Closeable {

    private final static String INSERT_A_TITLE = """
            INSERT INTO titles(
                id,
                type,
                primary_title,
                original_title,
                is_adult,
                start_year,
                end_year,
                runtime_duration,
                genres
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
            """;

    private final PreparedStatement insertTitle;

    public SQLiteMovieSerializer(Connection connection) throws SQLException {
        insertTitle = connection.prepareStatement(INSERT_A_TITLE);
    }

    @Override
    public PreparedStatement serialize(Movie movie) throws SerializationException {
        try {
            setString(1, movie.id(), t -> t);
            setString(2, movie.type(), Movie.TitleType::name);
            setString(3, movie.primaryTitle(), t -> t);
            setString(4, movie.originalTitle(), t -> t);
            setString(5, movie.isAdult(), b -> b ? "true" : "false");
            setOptionalString(6, movie.startYear(), Year::toString);
            setOptionalString(7, movie.endYear(), Year::toString);
            setOptionalString(8, movie.runtimeDuration(), Duration::toString);
            setString(9, movie.genres(), g -> g.stream().map(Movie.Genre::name).collect(joining()));
        } catch (SQLException e) {
            throw new SerializationException(e);
        }
        return insertTitle;
    }

    private <V> void setString(int parameterIndex, V value, Function<V, String> mapper) throws SQLException {
        insertTitle.setString(parameterIndex, mapper.apply(value));
    }

    private void setNullString(int parameterIndex, JDBCType type) throws SQLException {
        insertTitle.setNull(parameterIndex, type.ordinal());
    }

    private <V> void setOptionalString(int parameterIndex, Optional<V> value, Function<V, String> mapper) throws SQLException {
        if (value.isPresent())
            setString(parameterIndex, value.get(), mapper);
        else
            setNullString(parameterIndex, JDBCType.VARCHAR);
    }

    @Override
    public void close() throws IOException {
        try {
            insertTitle.close();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
