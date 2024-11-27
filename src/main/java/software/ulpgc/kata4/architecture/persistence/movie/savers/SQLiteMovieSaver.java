package software.ulpgc.kata4.architecture.persistence.movie.savers;

import software.ulpgc.kata4.architecture.model.Movie;
import software.ulpgc.kata4.architecture.persistence.Saver;
import software.ulpgc.kata4.architecture.persistence.SerializationException;
import software.ulpgc.kata4.architecture.persistence.movie.serializers.SQLiteMovieSerializer;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteMovieSaver implements Saver<Movie> {

    private static final String CREATE_TABLE_TITLES = """
            CREATE TABLE IF NOT EXISTS titles(
                id TEXT PRIMARY KEY,
                type TEXT NOT NULL,
                primary_title TEXT NOT NULL,
                original_title TEXT NOT NULL,
                is_adult TEXT NOT NULL CHECK (is_adult IN ('true', 'false')),
                start_year TEXT,
                end_year TEXT,
                runtime_duration TEXT,
                genres TEXT
            );
            """;

    private final Connection connection;
    private final SQLiteMovieSerializer serializer;

    public SQLiteMovieSaver(File dbFile) throws IOException {
        try {
            connection = openConnection(dbFile);
            prepareDatabase();
            serializer = new SQLiteMovieSerializer(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection openConnection(File dbFile) throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
    }

    private void prepareDatabase() throws SQLException {
        connection.createStatement().execute(CREATE_TABLE_TITLES);
        connection.setAutoCommit(false);
    }

    @Override
    public void save(Movie movie) throws IOException, SerializationException {
        try {
            serializer.serialize(movie).execute();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void close() throws IOException {
        try {
            connection.commit();
            serializer.close();
            connection.close();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
