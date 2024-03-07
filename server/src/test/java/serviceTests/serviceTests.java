package serviceTests;

import dataAccess.*;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.LoginRequest;
import requests.RegisterRequest;
import response.CreateGameResponse;
import response.ListGamesResponse;
import response.LoginResponse;
import response.RegisterResponse;
import services.AuthService;
import services.GameService;
import services.UserService;

import javax.xml.crypto.Data;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class serviceTests {

    GameDAO memoryGameDAO = new MemoryGameDAO();
    UserDAO memoryUserDAO = new MemoryUserDAO();
    AuthDAO memoryAuthDAO = new MemoryAuthDAO();
    UserService userService = new UserService(memoryUserDAO);
    AuthService authService = new AuthService(memoryAuthDAO);
    GameService gameService = new GameService(memoryGameDAO, memoryAuthDAO);



    @BeforeEach
    public void clear() throws DataAccessException {
        userService.clear();
        gameService.clear();
        authService.clear();
    }

    @Test
    public void testRegister_ValidRequest() throws DataAccessException {

        RegisterRequest validRequest = new RegisterRequest("username", "password", "email");
        RegisterResponse response = userService.register(validRequest);
        assertEquals("username", response.getUsername());
    }
    @Test
    public void testRegister_InValidRequest() throws DataAccessException {

        RegisterRequest invalidRequest = new RegisterRequest("", "password", "email");
        assertThrows(Exception.class, () -> userService.register(invalidRequest));
    }

    @Test
    public void testLogin_ValidRequest() throws DataAccessException{
        RegisterRequest validRequest = new RegisterRequest("username", "password", "email");
        userService.register(validRequest);
        LoginRequest validLoginRequest = new LoginRequest("username", "password");

        assertNotNull(userService.login(validLoginRequest).getUsername());
    }

    @Test
    public void testLogin_InvalidRequest() throws DataAccessException{
        RegisterRequest validRequest = new RegisterRequest("username", "password", "email");
        userService.register(validRequest);
        LoginRequest invalidLoginRequest = new LoginRequest("Nathan", "password");

        assertThrows(Exception.class, () ->userService.login(invalidLoginRequest).getAuthToken());

    }

    @Test
    public void testClear() throws DataAccessException{
        RegisterRequest validRequest = new RegisterRequest("username", "password", "email");
        userService.register(validRequest);
        userService.clear();

        assertNull(memoryUserDAO.retrieveUser("username"));
    }


    @Test
    public void testLogout_validRequest() throws DataAccessException{
        RegisterRequest validRequest = new RegisterRequest("username", "password", "email");
        userService.register(validRequest);

        LoginRequest validLoginRequest = new LoginRequest("username", "password");
        LoginResponse loginResponse = userService.login(validLoginRequest);
        AuthData auth = new AuthData();
        auth.setAuthToken("Nathan123");
        auth.setUsername(loginResponse.getUsername());
        memoryAuthDAO.createAuth(auth);
        authService.logout("Nathan123");

        assertFalse(memoryAuthDAO.isAuthInList("Nathan123"));
    }

    @Test
    public void testLogout_invalidRequest() throws DataAccessException{
        RegisterRequest validRequest = new RegisterRequest("username", "password", "email");
        userService.register(validRequest);

        LoginRequest validLoginRequest = new LoginRequest("username", "password");
        LoginResponse loginResponse = userService.login(validLoginRequest);
        AuthData auth = new AuthData();
        auth.setAuthToken("Nathan123");
        auth.setUsername(loginResponse.getUsername());
        memoryAuthDAO.createAuth(auth);
        assertThrows(Exception.class, () ->authService.logout("Nathan1234"));


    }


    @Test
    public void testCreateGame_ValidRequest() throws DataAccessException{
        RegisterRequest validRequest = new RegisterRequest("username", "password", "email");
        userService.register(validRequest);

        LoginRequest validLoginRequest = new LoginRequest("username", "password");
        LoginResponse loginResponse = userService.login(validLoginRequest);
        AuthData auth = new AuthData();
        auth.setAuthToken("Nathan123");
        auth.setUsername(loginResponse.getUsername());
        memoryAuthDAO.createAuth(auth);

        CreateGameRequest request = new CreateGameRequest("Nathan_Game", auth.getAuthToken());
        CreateGameResponse response = gameService.createGame(request);
        assert(response.getGameID() >= 0);
    }

    @Test
    public void testCreateGame_InvalidRequest() throws DataAccessException{
        RegisterRequest validRequest = new RegisterRequest("username", "password", "email");
        userService.register(validRequest);

        LoginRequest validLoginRequest = new LoginRequest("username", "password");
        LoginResponse loginResponse = userService.login(validLoginRequest);
        AuthData auth = new AuthData();
        auth.setAuthToken("Nathan123");
        auth.setUsername(loginResponse.getUsername());
        memoryAuthDAO.createAuth(auth);

        CreateGameRequest request = new CreateGameRequest("", auth.getAuthToken());
        assertThrows(Exception.class, () ->gameService.createGame(request));
    }

    @Test
    public void testListGames_ValidRequest() throws DataAccessException{
        RegisterRequest validRequest = new RegisterRequest("username", "password", "email");
        userService.register(validRequest);

        LoginRequest validLoginRequest = new LoginRequest("username", "password");
        LoginResponse loginResponse = userService.login(validLoginRequest);
        AuthData auth = new AuthData();
        auth.setAuthToken("Nathan123");
        auth.setUsername(loginResponse.getUsername());
        memoryAuthDAO.createAuth(auth);
        CreateGameRequest request = new CreateGameRequest("Nathan_Game", auth.getAuthToken());
        CreateGameResponse response = gameService.createGame(request);

        Collection<GameData> listGames = gameService.listGames();
        assertNotNull(listGames);
    }



    @Test
    public void testJoinGame_validRequest() throws DataAccessException{
        RegisterRequest validRequest = new RegisterRequest("username", "password", "email");
        userService.register(validRequest);

        LoginRequest validLoginRequest = new LoginRequest("username", "password");
        LoginResponse loginResponse = userService.login(validLoginRequest);
        AuthData auth = new AuthData();
        auth.setAuthToken("Nathan123");
        auth.setUsername(loginResponse.getUsername());
        memoryAuthDAO.createAuth(auth);
        CreateGameRequest request = new CreateGameRequest("Nathan_Game", auth.getAuthToken());
        CreateGameResponse response = gameService.createGame(request);

        JoinGameRequest join = new JoinGameRequest("", auth.getAuthToken(), response.getGameID());
        assertNotNull(gameService.joinGame(join).getAuthToken());

    }

    @Test
    public void testJoinGame_InvalidRequest() throws DataAccessException{
        RegisterRequest validRequest = new RegisterRequest("username", "password", "email");
        userService.register(validRequest);

        LoginRequest validLoginRequest = new LoginRequest("username", "password");
        LoginResponse loginResponse = userService.login(validLoginRequest);
        AuthData auth = new AuthData();
        auth.setAuthToken("Nathan123");
        auth.setUsername(loginResponse.getUsername());
        memoryAuthDAO.createAuth(auth);
        CreateGameRequest request = new CreateGameRequest("Nathan_Game", auth.getAuthToken());
        CreateGameResponse response = gameService.createGame(request);

        JoinGameRequest join = new JoinGameRequest("", auth.getAuthToken(), response.getGameID() +1);
        assertThrows(Exception.class, () -> gameService.joinGame(join));

    }

    @Test
    public void testMakeAuthToken_ValidRequest() throws DataAccessException{
        RegisterRequest validRequest = new RegisterRequest("username", "password", "email");
        authService.makeAuthToken(validRequest, null);
        assertNotNull(authService.makeAuthToken(validRequest, null));

    }

    @Test
    public void testAuthentication_ValidRequest() throws DataAccessException{
        AuthData auth = new AuthData();
        auth.setAuthToken("Nathan123");
        auth.setUsername("Nathan");
        memoryAuthDAO.createAuth(auth);

        assertTrue(authService.authentication(auth.getAuthToken()));
    }


    @Test
    public void testAuthentication_InvalidRequest() throws DataAccessException{
        AuthData auth = new AuthData();
        auth.setAuthToken("Nathan123");
        auth.setUsername("Nathan");
        memoryAuthDAO.createAuth(auth);

        assertThrows(Exception.class, ()->authService.authentication(auth.getAuthToken() + "1"));
    }









}
