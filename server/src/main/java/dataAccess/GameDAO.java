package dataAccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {

    public void createGame(GameData game) throws DataAccessException;

    public GameData readGame(int gameID) throws DataAccessException;

    public void clear() throws DataAccessException;

    public boolean isGameInList(String gameName, int gameID) throws DataAccessException;

    Collection<GameData> listGames() throws DataAccessException;

    public void joinGame(GameData gameData) throws DataAccessException;
}
