package dataAccess;

import model.AuthData;

public class DatabaseAuthDAO implements AuthDAO {
    @Override
    public void createAuth(AuthData auth) {

    }

    @Override
    public AuthData readAuth(String authToken) {
        return null;
    }

    @Override
    public boolean deleteAuth(String authToken) {
        return false;
    }

    @Override
    public boolean isAuthInList(String authToken) {
        return false;
    }

    @Override
    public void clear() {

    }
}
