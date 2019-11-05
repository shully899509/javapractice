package main.java.services;

import main.java.datarepository.Datasource;
import main.java.model.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieService {
    private Connection connection;

    private static final String MOVIES_TABLE = "MOVIES";
    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static final String DIRECTOR_ID = "DIRECTOR_ID";
    private static final int INDEX_ID = 1;
    private static final int INDEX_NAME = 2;
    private static final int INDEX_DIRECTOR_ID = 3;

    private Datasource datasource;

    public MovieService() {
        this.datasource = new Datasource();
        this.connection = datasource.open();
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeDatasource() {
        datasource.close();
    }

    public List<Movie> getMovies() throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("SELECT " + ID + ',' + NAME + ',' + DIRECTOR_ID + " FROM " + MOVIES_TABLE);
        return getMovies(preparedStatement);
    }

    public List<Movie> getMovieByName(String name, int directorId) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("SELECT " + ID + ',' + NAME + " FROM " + MOVIES_TABLE + " WHERE " + NAME + " = ?");
        preparedStatement.setString(1, name);
        return getMovies(preparedStatement);
    }

    private List<Movie> getMovies(PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet rs = preparedStatement.executeQuery()) {
            List<Movie> movies = new ArrayList<>();
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getInt(INDEX_ID));
                movie.setName(rs.getString(INDEX_NAME));
                movie.setDirectorId(rs.getInt(INDEX_DIRECTOR_ID));
                movies.add(movie);
            }
            return movies;
        } catch (SQLException e) {
            System.err.println("Query failed: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            if (!preparedStatement.isClosed()) {
                preparedStatement.close();
            }
        }
    }

    public Movie insertMovie(Movie movie) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + MOVIES_TABLE + '(' + NAME + ',' + DIRECTOR_ID + ") VALUES (?,?)")) {
            preparedStatement.setString(1, movie.getName());
            preparedStatement.setInt(2, movie.getDirectorId());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                return new Movie(rs.getInt(INDEX_ID), rs.getString(INDEX_NAME), rs.getInt(INDEX_DIRECTOR_ID));
            } else {
                throw new RuntimeException("Error inserting Movie: " + movie.getId() + " " + movie.getName() + " " + movie.getDirectorId());
            }

        } catch (SQLException e) {
            throw new RuntimeException("", e);
        }
    }

    public void deleteMovie(Movie movie) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + MOVIES_TABLE + " WHERE " + ID + " = ?")) {
            preparedStatement.setInt(1, movie.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting row from Movies table: " + e.getMessage());
        }
    }

    public Movie updateMovie(Movie movie, String nameReplace) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + MOVIES_TABLE + " SET " + NAME + " = ? WHERE " + ID + " = ? ")) {
            preparedStatement.setString(1, nameReplace);
            preparedStatement.setInt(2, movie.getId());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                return new Movie(rs.getInt(INDEX_ID), rs.getString(INDEX_NAME), rs.getInt(INDEX_DIRECTOR_ID));
            } else {
                throw new RuntimeException("Error updating Movie: " + movie.getId() + " " + movie.getName() + " " + movie.getDirectorId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
