package software.ulpgc.kata4.architecture.persistence.movie.loaders;

import software.ulpgc.kata4.architecture.model.Movie;
import software.ulpgc.kata4.architecture.persistence.DeserializationException;
import software.ulpgc.kata4.architecture.persistence.Loader;
import software.ulpgc.kata4.architecture.persistence.movie.deserializers.SQLiteMovieDeserializer;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteMovieLoader implements Loader<Movie> {

    private static final String SELECT_ALL_TITLES_SQL = "SELECT * FROM titles;";
    private final SQLiteMovieDeserializer deserializer;
    private final Connection connection;
    private final ResultSet cursor;

    public SQLiteMovieLoader(File dbFile) {
        deserializer = new SQLiteMovieDeserializer();
        try {
            connection = openConnection(dbFile);
            cursor = connection.prepareStatement(SELECT_ALL_TITLES_SQL).executeQuery();
            cursor.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection openConnection(File dbFile) throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
    }

    @Override
    public boolean hasNext() throws IOException {
        try {
            return !cursor.isAfterLast();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    @Override
    public Movie load() throws IOException, DeserializationException {
        if (!hasNext()) return null;
        Movie movie = deserializer.deserialize(cursor);
        try {
            cursor.next();
            return movie;
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
