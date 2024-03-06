package dataAccess;

import model.UserData;

public class DatabaseUserDAO implements UserDAO {
    @Override
    public void createUser(UserData user) {

    }

    @Override
    public boolean isUserInList(String username) {
        return false;
    }

    @Override
    public UserData retrieveUser(String username) {
        return null;
    }

    @Override
    public void clear() {

    }
}
