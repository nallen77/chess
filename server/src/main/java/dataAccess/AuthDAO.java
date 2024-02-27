package dataAccess;

import model.AuthData;

public interface AuthDAO {

    public void createAuth(AuthData auth);
    public AuthData readAuth(String authToken);
    public boolean deleteAuth(String authToken);

    public boolean isAuthInList(String authToken);

    public void clear();

}
