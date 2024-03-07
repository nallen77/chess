package dataAccess;

import model.UserData;

public interface UserDAO {

    public void createUser(UserData user) throws DataAccessException;

    public boolean isUserInList(String username) throws DataAccessException;

    public UserData retrieveUser(String username) throws DataAccessException;

    public void clear() throws DataAccessException;
}
