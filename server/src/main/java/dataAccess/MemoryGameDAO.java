package dataAccess;

import model.GameData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryGameDAO implements GameDAO {

    public Collection<GameData> gameData;

    public MemoryGameDAO() {
        gameData = new ArrayList<GameData>();
    }

    @Override
    public void createGame(GameData game) {
        gameData.add(game);
    }

    @Override
    public GameData readGame(int gameID) {
        for (GameData game : gameData) {
            if (game.getGameID() == gameID) {
                return game;
            }
        }
        return null;
    }

    public void clear() {
        gameData.clear();
    }

    @Override
    public boolean isGameInList(String gameName, int gameID) {
        for (GameData game : gameData) {
            if (game.getGameName().equals(gameName) || (game.getGameID() == gameID)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<GameData> listGames() {
        return gameData;
    }

    @Override
    public void joinGame(GameData gameData) {

    }
}
