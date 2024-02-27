package server;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import services.AuthService;
import services.GameService;
import services.UserService;
import spark.*;

public class Server {

    private final GameService gameService;
    private final UserService userService;
    private final AuthService authService;

    public Server(GameDAO gameDAO, UserDAO userDAO, AuthDAO authDAO) {
        this.gameService = new GameService(gameDAO);
        this.userService = new UserService(userDAO);
        this.authService = new AuthService(authDAO);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::clear);

        Spark.awaitInitialization();
        return Spark.port();
    }

    private Object clear(Request request, Response response) {
        gameService.clear();
        userService.clear();
        authService.clear();

        return "{}";
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}