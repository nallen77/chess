package dataAccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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

//    @Override
//    public void deleteAuth(String authToken) throws DataAccessException {
//        for (AuthData auth : authData) {
//            if (auth.getAuthToken().equals(authToken)) {
//                authData.remove(auth);
//            }
//        }
//        throw new DataAccessException("Error: unauthorized");
//    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        Iterator<AuthData> iterator = authData.iterator();
        while (iterator.hasNext()) {
            AuthData auth = iterator.next();
            if (auth.getAuthToken().equals(authToken)) {
                iterator.remove();
                return;
            }
        }
        throw new DataAccessException("Error: unauthorized");
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
