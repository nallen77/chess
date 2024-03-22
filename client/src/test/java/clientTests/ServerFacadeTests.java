package clientTests;

import exception.ResponseException;
import model.GameData;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() throws ResponseException {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        var sererURL = "http://localhost:" + port;
        facade = new ServerFacade(sererURL);
        facade.clear();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void register_Valid() throws ResponseException {
        assertDoesNotThrow(() -> facade.register("test1", "p1", "e1"));
        facade.logout();
    }

    @Test
    public void register_Invalid() throws ResponseException {
        facade.register("test2", "p1", "e1");
        facade.logout();
        assertThrows(ResponseException.class, () -> facade.register("test2", "p1", "e1"));
    }

    @Test
    public void login_Valid() throws ResponseException {
        facade.register("loginTest", "p", "e");
        facade.logout();
        assertDoesNotThrow(() -> facade.login("loginTest", "p"));
    }

    @Test
    public void login_Invalid() {
        assertThrows(ResponseException.class, () -> facade.login("loginTest", "p1"));
    }

    @Test
    public void logout_Valid() throws ResponseException {
        facade.register("logoutTest", "p", "e");
        assertDoesNotThrow(() -> facade.logout());
    }

    @Test
    public void logout_Invalid() throws ResponseException {
        facade.register("logoutTest1", "p", "e");
        facade.logout();
        assertThrows(ResponseException.class, () -> facade.logout());
    }

    @Test
    public void create_Valid() throws ResponseException {
        facade.register("createTest", "p", "e");
        assertDoesNotThrow(() -> facade.create("TestGame1"));
    }

    @Test
    public void create_Invalid() throws ResponseException {
        facade.register("createTest2", "p", "e");
        facade.logout();
        assertThrows(ResponseException.class, () -> facade.create("TestGame2"));
    }

    @Test
    public void list_Valid() throws ResponseException {
        facade.register("listTest", "p", "e");
        facade.create("TestGame3");
        assertNotNull(facade.list());
    }

    @Test
    public void list_Invalid() throws ResponseException {
        facade.register("listTest2", "p", "e");
        facade.create("TestGame4");
        facade.logout();
        assertThrows(ResponseException.class, () -> facade.list());
    }

    @Test
    public void join_Valid() throws ResponseException {
        facade.register("JoinTest", "p", "e");
        facade.create("JoinTestGame");
        Collection<GameData> chessGames = facade.list();
        if (chessGames.contains("JoinTestGame")) {
            for (GameData game : chessGames) {
                if (game.getGameName().equals("JoinTestGame")) {
                    assertDoesNotThrow(() -> facade.join(game.getGameID(), "white"));
                }
            }
        }
    }

    @Test
    public void join_Invalid() throws ResponseException {
        facade.register("JoinTest2", "p", "e");
        facade.create("JoinTestGame2");
        assertThrows(ResponseException.class, () -> facade.join(7, "black"));
    }

    @Test
    public void observe_Valid() throws ResponseException {
        facade.register("ObserveTest", "p", "e");
        facade.create("ObserveTestGame");
        Collection<GameData> chessGames = facade.list();
        if (chessGames.contains("ObserveTestGame")) {
            for (GameData game : chessGames) {
                if (game.getGameName().equals("ObserveTestGame")) {
                    assertDoesNotThrow(() -> facade.observe(game.getGameID()));
                }
            }
        }
    }

    @Test
    public void observe_Invalid() throws ResponseException {
        facade.register("ObserveTest2", "p", "e");
        facade.create("ObserveTestGame2");
        assertThrows(ResponseException.class, () -> facade.observe(7));
    }
}