package dataAccess;

import model.AuthData;

public interface AuthDAO {

    public void createAuth(AuthData auth);
    public void readAuth();
    public void updateAuth();
    public boolean deleteAuth(String authToken);

    public void clear();

}
