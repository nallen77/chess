package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class DatabaseGameDAO implements GameDAO {

    public DatabaseGameDAO() throws DataAccessException {
        DatabaseManager.createTables();
    }

    @Override
    public void createGame(GameData game) throws DataAccessException {
        try (Connection connection = DatabaseManager.getConnection()) {
            String query = "INSERT INTO GameData (gameID, whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, game.getGameID());
                statement.setString(2, game.getWhiteUsername());
                statement.setString(3, game.getBlackUsername());
                statement.setString(4, game.getGameName());
                ChessGame chessGame = game.getGame();
                Gson gson = new Gson();
                statement.setString(5, gson.toJson(chessGame));
                statement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error: SQL");
        }
    }

    @Override
    public GameData readGame(int gameID) throws DataAccessException {
        try (Connection connection = DatabaseManager.getConnection()) {
            String query = "SELECT * FROM GameData WHERE gameID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, gameID);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    var game = resultSet.getString("game");
                    Gson gson = new Gson();
                    ChessGame chessGame = gson.fromJson(game, ChessGame.class);
                    return new GameData(
                            gameID,
                            resultSet.getString("whiteUsername"),
                            resultSet.getString("blackUsername"),
                            resultSet.getString("gameName"),
                            chessGame
                    );
                }
            }
        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error: SQL");
        }
        return null;
    }

    @Override
    public void clear() throws DataAccessException {
        try (Connection connection = DatabaseManager.getConnection()) {
            String query = "DELETE FROM GameData";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error: SQL");
        }
    }

    @Override
    public boolean isGameInList(String gameName, int gameID) throws DataAccessException {
        try (Connection connection = DatabaseManager.getConnection()) {
            String query = "SELECT COUNT(*) AS count FROM GameData WHERE gameName = ? OR gameID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, gameName);
                statement.setInt(2, gameID);
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
    public Collection<GameData> listGames() throws DataAccessException {
        Collection<GameData> games = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection()) {
            String query = "SELECT * FROM GameData";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    var game = resultSet.getString("game");
                    Gson gson = new Gson();
                    ChessGame chessGame = gson.fromJson(game, ChessGame.class);
                    GameData gameData = new GameData(
                            resultSet.getInt("gameID"),
                            resultSet.getString("whiteUsername"),
                            resultSet.getString("blackUsername"),
                            resultSet.getString("gameName"),
                            chessGame
                    );
                    games.add(gameData);
                }
            }
        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error: SQL");
        }
        return games;

    }

    @Override
    public void joinGame(GameData gameData) throws DataAccessException {
        var updateStatement = "UPDATE GameData SET blackUsername = ?, whiteUsername = ? WHERE gameID = ?";
        var gameID = gameData.getGameID();

        if (readGame(gameID) == null) {
            throw new DataAccessException("Error: gameID is invalid");
        }

        var whiteUsername = gameData.getWhiteUsername();
        var blackUsername = gameData.getBlackUsername();
        try (var connection = DatabaseManager.getConnection();
             var preparedStatement = connection.prepareStatement(updateStatement)) {
            preparedStatement.setString(1, blackUsername);
            preparedStatement.setString(2, whiteUsername);
            preparedStatement.setInt(3, gameID);
            preparedStatement.executeUpdate();
        } catch (DataAccessException | SQLException e) {
            throw new DataAccessException("Error with statement: " + updateStatement);
        }
    }
}
