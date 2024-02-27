package dataAccess;

import model.UserData;

public interface UserDAO {

    public void createUser(UserData user);

    public boolean isUserInList(String username);

    public UserData retrieveUser(String username);

    public void updateUser();

    public void deleteUser();

    public void clear();
}
