package dataAccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryAuthDAO implements AuthDAO {

    public Collection<AuthData> authData;

    public MemoryAuthDAO() {
        authData = new ArrayList<>();
    }

    @Override
    public void createAuth(AuthData auth) {
        authData.add(auth);
    }

    @Override
    public AuthData readAuth(String authToken) {
        for (AuthData auth : authData) {
            if (auth.getAuthToken().equals(authToken)) {
                return auth;
            }
        }
        return null;
    }

    @Override
    public boolean deleteAuth(String authToken) {
        for (AuthData auth : authData) {
            if (auth.getAuthToken().equals(authToken)) {
                authData.remove(auth);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isAuthInList(String authToken) {
        for (AuthData auth : authData) {
            if (auth.getAuthToken().equals(authToken)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void clear() {
        authData.clear();
    }
}
