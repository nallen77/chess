package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.GameData;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import response.CreateGameResponse;
import response.JoinGameResponse;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class GameService {

    private final GameDAO gameDAO;
    private final AuthDAO authDAO;


    public GameService(GameDAO gameDAO, AuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    public void clear() {
        gameDAO.clear();
    }

    public CreateGameResponse createGame(CreateGameRequest createGameRequest) throws DataAccessException {
        if (createGameRequest.getGameName() == null || createGameRequest.getGameName().equals("")) {
            throw new DataAccessException("Error: bad request");
        }

        if (gameDAO.isGameInList(createGameRequest.getGameName(), 0)) {
            throw new DataAccessException("Error: already taken");
        }

        GameData newGame = new GameData(createGameRequest.getGameName());
        gameDAO.createGame(newGame);
        int newGameID = 0;
        while(newGameID <= 0) {
            newGameID = ThreadLocalRandom.current().nextInt();
        }

        newGame.setGameID(newGameID);
        return new CreateGameResponse(newGame.getGameID());
    }

    public Collection<GameData> listGames() {
        Collection<GameData> games = gameDAO.listGames();
        return games;
    }

    public JoinGameResponse joinGame(JoinGameRequest joinGameRequest) throws DataAccessException {
        if (joinGameRequest.getGameID() <= 0) {
            throw new DataAccessException("Error: bad request");
        }
        if (!gameDAO.isGameInList(null, joinGameRequest.getGameID())) {
            throw new DataAccessException("Error: unauthorized");
        }
        if (joinGameRequest.getPlayerColor() == null || joinGameRequest.getPlayerColor().equals("")) {
            return new JoinGameResponse(joinGameRequest.getAuthToken(), joinGameRequest.getGameID(), null, null);
        }
        if (gameDAO.readGame(joinGameRequest.getGameID()).getBlackUsername() != null &&
                gameDAO.readGame(joinGameRequest.getGameID()).getWhiteUsername() != null) {
            throw new DataAccessException("Error: already taken");
        }
        if (joinGameRequest.getPlayerColor().equals("WHITE") && gameDAO.readGame(joinGameRequest.getGameID()).getWhiteUsername() == null) {
            gameDAO.readGame(joinGameRequest.getGameID()).setWhiteUsername(authDAO.readAuth(joinGameRequest.getAuthToken()).getUsername());
            return new JoinGameResponse(joinGameRequest.getAuthToken(), joinGameRequest.getGameID(), "WHITE", null);
        }
        else if (joinGameRequest.getPlayerColor().equals("BLACK") && gameDAO.readGame(joinGameRequest.getGameID()).getBlackUsername() == null) {
            gameDAO.readGame(joinGameRequest.getGameID()).setBlackUsername(authDAO.readAuth(joinGameRequest.getAuthToken()).getUsername());
            return new JoinGameResponse(joinGameRequest.getAuthToken(), joinGameRequest.getGameID(), "BLACK", null);
        }
        else {
            throw new DataAccessException("Error: already taken");
        }
    }
}
