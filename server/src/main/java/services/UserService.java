package services;

import dataAccess.UserDAO;

public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void clear() {
        userDAO.clear();
    }
}
