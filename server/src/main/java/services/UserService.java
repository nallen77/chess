package services;

import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.UserData;
import requests.LoginRequest;
import requests.RegisterRequest;
import response.LoginResponse;
import response.RegisterResponse;

public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void clear() {
        userDAO.clear();
    }

    public RegisterResponse register(RegisterRequest registerRequest) throws DataAccessException {
        if (registerRequest.getUsername() == null || registerRequest.getPassword() == null || registerRequest.getEmail() == null) {
            throw new DataAccessException("Error: bad request");
        }

        if (registerRequest.getUsername().equals("") || registerRequest.getPassword().equals("") || registerRequest.getEmail().equals("")) {
            throw new DataAccessException("Error: bad request");
        }

        if (userDAO.isUserInList(registerRequest.getUsername())) {
            throw new DataAccessException("Error: already taken");
        }

        UserData currentUser = new UserData(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getEmail());
        userDAO.createUser(currentUser);
        return new RegisterResponse(null, registerRequest.getUsername());

    }

    public LoginResponse login(LoginRequest loginRequest) throws DataAccessException {
        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null ||
                loginRequest.getUsername().equals("") || loginRequest.getPassword().equals("")) {
            throw new DataAccessException("Error: unauthorized");
        }

        if (!userDAO.isUserInList(loginRequest.getUsername()) ||
                !userDAO.retrieveUser(loginRequest.getUsername()).getPassword().equals(loginRequest.getPassword())) {
            throw new DataAccessException("Error: unauthorized");
        }

        return new LoginResponse(loginRequest.getUsername(), null);
    }
}
