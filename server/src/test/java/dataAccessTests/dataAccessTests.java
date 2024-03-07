package dataAccessTests;

import dataAccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class dataAccessTests {

    static final DatabaseAuthDAO authDAO;
    static final DatabaseGameDAO gameDAO;
    static final DatabaseUserDAO userDAO;

    static {
        try {
            authDAO = new DatabaseAuthDAO();
            gameDAO = new DatabaseGameDAO();
            userDAO = new DatabaseUserDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void clear() throws DataAccessException {
        authDAO.clear();
        gameDAO.clear();
        userDAO.clear();
    }

    @Test
    public void testCreateAuth_Valid() throws DataAccessException {
        AuthData authData = new AuthData();
        authData.setUsername("testName");
        authData.setAuthToken("testAuth");
        authDAO.createAuth(authData);
        assertNotNull(authDAO.readAuth("testAuth"));
    }

    @Test
    public void testCreateAuth_Invalid() throws DataAccessException {
        AuthData authData = new AuthData();
        authData.setUsername("testName");
        authData.setAuthToken("testAuth");
        authDAO.createAuth(authData);
        assertThrows(Exception.class, () -> authDAO.createAuth(authData));
    }

    @Test
    public void testReadAuth_Valid() throws DataAccessException {
        AuthData authData = new AuthData();
        authData.setUsername("testName");
        authData.setAuthToken("testAuth");
        authDAO.createAuth(authData);
        assertNotNull(authDAO.readAuth("testAuth"));
    }

    @Test
    public void testReadAuth_Invalid() throws DataAccessException {
        assertNull(authDAO.readAuth("testAuth"));
    }

    @Test
    public void testIsAuthInList_Valid() throws DataAccessException {
        AuthData authData = new AuthData();
        authData.setUsername("testName");
        authData.setAuthToken("testAuth");
        authDAO.createAuth(authData);
        assertTrue(authDAO.isAuthInList("testAuth"));
    }

    @Test
    public void testIsAuthInList_Invalid() throws DataAccessException {
        assertFalse(authDAO.isAuthInList("testAuth"));
    }

    @Test
    public void testDeleteAuth_Valid() throws DataAccessException {
        AuthData authData = new AuthData();
        authData.setUsername("testName");
        authData.setAuthToken("testAuth");
        authDAO.createAuth(authData);
        authDAO.deleteAuth("testAuth");
        assertFalse(authDAO.isAuthInList("testAuth"));
    }

    @Test
    public void testDeleteAuth_Invalid() throws DataAccessException {
        AuthData authData = new AuthData();
        authData.setUsername("testName");
        authData.setAuthToken("testAuth");
        authDAO.createAuth(authData);
        assertThrows(Exception.class, () -> authDAO.deleteAuth("testAuthBad"));
    }

    @Test
    public void testClearAuth_Valid() throws DataAccessException {
        AuthData authData = new AuthData();
        authData.setUsername("testName");
        authData.setAuthToken("testAuth");
        authDAO.createAuth(authData);
        authDAO.clear();
        assertFalse(authDAO.isAuthInList("testAuth"));
    }

    @Test
    public void testCreateGame_Valid() throws DataAccessException {
        GameData gameData = new GameData("testGame");
        gameDAO.createGame(gameData);
        assertNotNull(gameDAO.listGames());
    }

    @Test
    public void testCreateGame_Invalid() {
        assertThrows(Exception.class, () -> gameDAO.createGame(null));
    }

    @Test
    public void testReadGame_Valid() throws DataAccessException {
        GameData gameData = new GameData(007, "white", "black", "testGame", null);
        gameDAO.createGame(gameData);
        assertNotNull(gameDAO.readGame(007));
    }

    @Test
    public void testReadGame_Invalid() throws DataAccessException {
        assertNull(gameDAO.readGame(007));
    }

    @Test
    public void testClearGame_Valid() throws DataAccessException {
        GameData gameData = new GameData(007, "white", "black", "testGame", null);
        gameDAO.createGame(gameData);
        gameDAO.clear();
        assertFalse(gameDAO.isGameInList("testGame", 007));
    }

    @Test
    public void testIsGameInList_Valid() throws DataAccessException {
        GameData gameData = new GameData(007, "white", "black", "testGame", null);
        gameDAO.createGame(gameData);
        assertTrue(gameDAO.isGameInList("testGame", 007));
    }

    @Test
    public void testIsGameInList_Invalid() throws DataAccessException {
        assertFalse(gameDAO.isGameInList("testGame", 007));
    }

    @Test
    public void testListGames_Valid() throws DataAccessException {
        GameData gameData = new GameData("testGame");
        gameDAO.createGame(gameData);
        assertNotNull(gameDAO.listGames());
    }

    @Test
    public void testListGames_Invalid() throws DataAccessException {
        assertTrue(gameDAO.listGames().isEmpty());
    }

    @Test
    public void testJoinGame_Valid() throws DataAccessException {
        GameData gameData = new GameData(007, "white", "black", "testGame", null);
        gameDAO.createGame(gameData);
        gameDAO.joinGame(gameData);
        assertTrue(gameDAO.readGame(007).getBlackUsername().equals("black"));
    }

    @Test
    public void testJoinGame_Invalid() throws DataAccessException {
        GameData gameData = new GameData(007, "white", "black", "testGame", null);
        gameDAO.createGame(gameData);
        gameData.setGameID(-007);
        assertThrows(Exception.class, () -> gameDAO.joinGame(gameData));
    }

    @Test
    public void testCreateUser_Valid() throws DataAccessException {
        UserData userData = new UserData("testUser", "testPass", "testEmail");
        userDAO.createUser(userData);
        assertNotNull(userDAO.retrieveUser("testUser"));
    }

    @Test
    public void testCreateUser_Invalid() throws DataAccessException {
        UserData userData = new UserData("testUser", "testPass", "testEmail");
        userDAO.createUser(userData);
        assertThrows(Exception.class, () -> userDAO.createUser(userData));
    }

    @Test
    public void testIsUserInList_Valid() throws DataAccessException {
        UserData userData = new UserData("testUser", "testPass", "testEmail");
        userDAO.createUser(userData);
        assertTrue(userDAO.isUserInList("testUser"));
    }

    @Test
    public void testIsUserInList_Invalid() throws DataAccessException {
        UserData userData = new UserData("testUser", "testPass", "testEmail");
        userDAO.createUser(userData);
        assertFalse(userDAO.isUserInList("testUserBad"));
    }

    @Test
    public void testRetrieveUser_Valid() throws DataAccessException {
        UserData userData = new UserData("testUser", "testPass", "testEmail");
        userDAO.createUser(userData);
        assertNotNull(userDAO.retrieveUser("testUser"));
    }

    @Test
    public void testRetrieveUser_Invalid() throws DataAccessException {
        UserData userData = new UserData("testUser", "testPass", "testEmail");
        userDAO.createUser(userData);
        assertNull(userDAO.retrieveUser("testUserBad"));
    }

    @Test
    public void testClearUser_Valid() throws DataAccessException {
        UserData userData = new UserData("testUser", "testPass", "testEmail");
        userDAO.createUser(userData);
        userDAO.clear();
        assertFalse(userDAO.isUserInList("testUser"));
    }

}
