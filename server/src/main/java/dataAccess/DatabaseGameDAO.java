package dataAccess;

import model.GameData;

import java.util.Collection;

public class DatabaseGameDAO implements GameDAO {
    @Override
    public void createGame(GameData game) {

    }

    @Override
    public GameData readGame(int gameID) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean isGameInList(String gameName, int gameID) {
        return false;
    }

    @Override
    public Collection<GameData> listGames() {
        return null;
    }
}
