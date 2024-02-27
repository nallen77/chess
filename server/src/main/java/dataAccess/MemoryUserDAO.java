package dataAccess;

import model.GameData;
import model.UserData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryUserDAO implements UserDAO{

    public Collection<UserData> userData;

    public MemoryUserDAO() {
        userData = new ArrayList<UserData>();
    }

    @Override
    public void createUser(UserData user) {
        userData.add(user);
    }

    @Override
    public boolean isUserInList(String username) {
        for (UserData user : userData) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public UserData retrieveUser(String username) {
        for (UserData user : userData) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void clear() {
        userData.clear();
    }
}
