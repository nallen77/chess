package dataAccess;

import model.AuthData;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseAuthDAO implements AuthDAO {
    @Override
    public void createAuth(AuthData auth) {
        try (Connection connection = DatabaseManager.getConnection()) {
            String query = "INSERT INTO AuthData (authToken, username) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, auth.getAuthToken());
                preparedStatement.setString(2, auth.getUsername());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AuthData readAuth(String authToken) {
        try (Connection connection = DatabaseManager.getConnection()) {
            String query = "SELECT * FROM AuthData WHERE authToken = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, authToken);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return new AuthData(resultSet.getString("authToken"), resultSet.getString("username"));
                }
            }
        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteAuth(String authToken) {
        try (Connection connection = DatabaseManager.getConnection()) {
            String query = "DELETE FROM AuthData WHERE authToken = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, authToken);
                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isAuthInList(String authToken) {
        return false;
    }

    @Override
    public void clear() {

    }
}
