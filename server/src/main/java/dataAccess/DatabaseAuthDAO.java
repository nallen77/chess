package dataAccess;

import model.AuthData;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
        return null;
    }

    @Override
    public boolean deleteAuth(String authToken) {
        return false;
    }

    @Override
    public boolean isAuthInList(String authToken) {
        return false;
    }

    @Override
    public void clear() {

    }
}
