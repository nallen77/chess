package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import model.AuthData;
import requests.LoginRequest;
import requests.RegisterRequest;

import java.util.UUID;

public class AuthService {

    private final AuthDAO authDAO;

    public AuthService(AuthDAO authDAO) {
        this.authDAO = authDAO;
    }

    public void clear() {
        authDAO.clear();
    }

    public String makeAuthToken(RegisterRequest registerRequest, LoginRequest loginRequest) {
        AuthData newAuth = new AuthData();
        if(registerRequest != null){
            newAuth.setUsername(registerRequest.getUsername());
        }
        else{
            newAuth.setUsername(loginRequest.getUsername());
        }
        String randomAuth = UUID.randomUUID().toString();
        newAuth.setAuthToken(randomAuth);
        authDAO.createAuth(newAuth);

        return randomAuth;
    }

    public void logout(String authToken) throws DataAccessException {
        if (!authDAO.deleteAuth(authToken)) {
            throw new DataAccessException("Error: unauthorized");
        }
    }

    public void authentication(String authToken) throws DataAccessException {
        if (!authDAO.isAuthInList(authToken)) {
            throw new DataAccessException("Error: unauthorized");
        }
    }
}
