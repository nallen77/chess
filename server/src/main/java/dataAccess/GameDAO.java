package dataAccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {

    public void createGame(GameData game);

    public GameData readGame(int gameID);

    public void updateGame();

    public void deleteGame();

    public void clear();

    public boolean isGameInList(String gameName, int gameID);

    Collection<GameData> listGames();
}
