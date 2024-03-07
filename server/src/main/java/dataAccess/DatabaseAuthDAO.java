package dataAccess;

import model.AuthData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseAuthDAO implements AuthDAO {

    public DatabaseAuthDAO() throws DataAccessException {
        DatabaseManager.createTables();
    }
    @Override
    public void createAuth(AuthData auth) throws DataAccessException {
        try (Connection connection = DatabaseManager.getConnection()) {
            String query = "INSERT INTO AuthData (authToken, username) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, auth.getAuthToken());
                preparedStatement.setString(2, auth.getUsername());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error: SQL");
        }
    }

    @Override
    public AuthData readAuth(String authToken) throws DataAccessException {
        try (Connection connection = DatabaseManager.getConnection()) {
            String query = "SELECT * FROM AuthData WHERE authToken = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, authToken);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return new AuthData(resultSet.getString("username"), resultSet.getString("authToken"));
                }
            }
        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error: SQL");
        }
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        if (!isAuthInList(authToken)) {
            throw new DataAccessException("Error: unauthorized");
        }
        try (Connection connection = DatabaseManager.getConnection()) {
            String query = "DELETE FROM AuthData WHERE authToken = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, authToken);
                statement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error: SQL");
        }
    }

    @Override
    public boolean isAuthInList(String authToken) throws DataAccessException {
        try (Connection connection = DatabaseManager.getConnection()) {
            String query = "SELECT COUNT(*) AS count FROM AuthData WHERE authToken = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, authToken);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt("count") > 0;
                }
            }
        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error: SQL");
        }
        return false;
    }

    @Override
    public void clear() throws DataAccessException {
        try (Connection connection = DatabaseManager.getConnection()) {
            String query = "DELETE FROM AuthData";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error: SQL");
        }
    }
}
