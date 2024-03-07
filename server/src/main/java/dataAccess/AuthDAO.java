package dataAccess;

import model.AuthData;

public interface AuthDAO {

    public void createAuth(AuthData auth) throws DataAccessException;
    public AuthData readAuth(String authToken) throws DataAccessException;
    public void deleteAuth(String authToken) throws DataAccessException;

    public boolean isAuthInList(String authToken) throws DataAccessException;

    public void clear() throws DataAccessException;

}
