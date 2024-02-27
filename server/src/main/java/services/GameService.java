package services;

import dataAccess.GameDAO;
import requests.RegisterRequest;
import response.RegisterResponse;

public class GameService {

    private final GameDAO gameDAO;


    public GameService(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    public void clear() {
        gameDAO.clear();
    }


}
