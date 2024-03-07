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
    @Override
    public void createGame(GameData game) {
        try (Connection connection = DatabaseManager.getConnection()) {
            String query = "INSERT INTO GameData (gameID, whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?)";
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
        }
    }

    @Override
    public GameData readGame(int gameID) {
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
        }
        return null;
    }

    @Override
    public void clear() {
        try (Connection connection = DatabaseManager.getConnection()) {
            String query = "DELETE FROM GameData";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isGameInList(String gameName, int gameID) {
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
        }
        return false;
    }

    @Override
    public Collection<GameData> listGames() {
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
        }
        return games;

    }
}
