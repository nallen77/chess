package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataAccess.*;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.LoginRequest;
import requests.RegisterRequest;
import response.*;
import services.AuthService;
import services.GameService;
import services.UserService;
import spark.*;

public class Server {

    private final GameService gameService;
    GameDAO databaseGameDAO;
    private final UserService userService;
    UserDAO databaseUserDAO;
    private final AuthService authService;
    AuthDAO databaseAuthDAO;

    public Server() {
        try{
            databaseAuthDAO = new DatabaseAuthDAO();
            databaseGameDAO = new DatabaseGameDAO();
            databaseUserDAO = new DatabaseUserDAO();
            gameService = new GameService(databaseGameDAO, databaseAuthDAO);
            userService = new UserService(databaseUserDAO);
            authService = new AuthService(databaseAuthDAO);
        }
        catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::clear);
        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
        Spark.delete("/session", this::logout);
        Spark.post("/game", this::createGame);
        Spark.get("/game", this::listGames);
        Spark.put("/game", this::joinGame);

        Spark.awaitInitialization();
        return Spark.port();
    }

    private Object joinGame(Request request, Response response) {
        try {
            String authToken = request.headers("Authorization");

            var joinGameRequest = new Gson().fromJson(request.body(), JoinGameRequest.class);
            joinGameRequest.setAuthToken(authToken);
            authService.authentication(authToken);

            JoinGameResponse joinGameResponse = gameService.joinGame(joinGameRequest);

            return new Gson().toJson(joinGameResponse);
        }
        catch (DataAccessException e) {
            if (e.getMessage().equals("Error: unauthorized")){
                response.status(401);
                JsonObject error = new JsonObject();
                error.addProperty("error", "JoinGame" );
                error.addProperty("message", e.getMessage());
                return new Gson().toJson(error);

            }
            else if (e.getMessage().equals("Error: already taken")){
                response.status(403);
                JsonObject error = new JsonObject();
                error.addProperty("error", "JoinGame" );
                error.addProperty("message", e.getMessage());
                return new Gson().toJson(error);

            }
            else if (e.getMessage().equals("Error: bad request")){
                response.status(400);
                JsonObject error = new JsonObject();
                error.addProperty("error", "JoinGame" );
                error.addProperty("message", e.getMessage());
                return new Gson().toJson(error);

            }
            else{
                response.status(500);
                JsonObject error = new JsonObject();
                error.addProperty("error", "JoinGame" );
                error.addProperty("message", e.getMessage());
                return new Gson().toJson(error);

            }
        }
    }

    private Object listGames(Request request, Response response) {
        try {
            String authToken = request.headers("Authorization");

            ListGamesResponse listGamesResponse = new ListGamesResponse(gameService.listGames());
            authService.authentication(authToken);

            return new Gson().toJson(listGamesResponse);
        }
        catch (DataAccessException e) {
            if (e.getMessage().equals("Error: unauthorized")){
                response.status(401);
                JsonObject error = new JsonObject();
                error.addProperty("error", "ListGames" );
                error.addProperty("message", e.getMessage());
                return new Gson().toJson(error);

            }
            else{
                response.status(500);
                JsonObject error = new JsonObject();
                error.addProperty("error", "ListGames" );
                error.addProperty("message", e.getMessage());
                return new Gson().toJson(error);

            }
        }
    }

    private Object createGame(Request request, Response response) {
        try {
            String authToken = request.headers("Authorization");

            var createGameRequest = new Gson().fromJson(request.body(), CreateGameRequest.class);
            createGameRequest.setAuthToken(authToken);
            CreateGameResponse createGameResponse = gameService.createGame(createGameRequest);
            authService.authentication(authToken);

            return new Gson().toJson(createGameResponse);
        }
        catch (DataAccessException e) {
            if(e.getMessage().equals("Error: bad request")){
                response.status(400);
                JsonObject error = new JsonObject();
                error.addProperty("error", "CreateGame Bad Request" );
                error.addProperty("message", e.getMessage());
                return new Gson().toJson(error);
            }

            else if (e.getMessage().equals("Error: unauthorized")){
                response.status(401);
                JsonObject error = new JsonObject();
                error.addProperty("error", "CreateGame" );
                error.addProperty("message", e.getMessage());
                return new Gson().toJson(error);

            }
            else{
                response.status(500);
                JsonObject error = new JsonObject();
                error.addProperty("error", "CreateGame" );
                error.addProperty("message", e.getMessage());
                return new Gson().toJson(error);

            }
        }
    }

    private Object logout(Request request, Response response) {
        try {
            authService.logout(request.headers("Authorization"));
            return "{}";
        }
        catch (DataAccessException e) {
            if(e.getMessage().equals("Error: unauthorized")){
                response.status(401);
                JsonObject error = new JsonObject();
                error.addProperty("error", "Logout" );
                error.addProperty("message", e.getMessage());
                return new Gson().toJson(error);
            }
            else{
                response.status(500);
                JsonObject error = new JsonObject();
                error.addProperty("error", "Logout" );
                error.addProperty("message", e.getMessage());
                return new Gson().toJson(error);

            }
        }
    }

    private Object login(Request request, Response response) {
        try {
            var loginRequest = new Gson().fromJson(request.body(), LoginRequest.class);
            LoginResponse loginResponse = userService.login(loginRequest);
            loginResponse.setAuthToken(authService.makeAuthToken(null, loginRequest));
            return new Gson().toJson(loginResponse);
        }
        catch (DataAccessException e) {
            if(e.getMessage().equals("Error: unauthorized")){
                response.status(401);
                JsonObject error = new JsonObject();
                error.addProperty("error", "Login" );
                error.addProperty("message", e.getMessage());
                return new Gson().toJson(error);
            }
            else{
                response.status(500);
                JsonObject error = new JsonObject();
                error.addProperty("error", "Login" );
                error.addProperty("message", e.getMessage());
                return new Gson().toJson(error);

            }
        }
    }

    private Object register(Request request, Response response) {
        try {
            var registerRequest = new Gson().fromJson(request.body(), RegisterRequest.class);
            RegisterResponse registerResponse = userService.register(registerRequest);
            registerResponse.setAuthToken(authService.makeAuthToken(registerRequest, null));

            return new Gson().toJson(registerResponse);
        }
        catch (DataAccessException e) {
            if(e.getMessage().equals("Error: bad request")){
                response.status(400);
                JsonObject error = new JsonObject();
                error.addProperty("error", "Register Bad Request" );
                error.addProperty("message", e.getMessage());
                return new Gson().toJson(error);
            }

            else if (e.getMessage().equals("Error: already taken")){
                response.status(403);
                JsonObject error = new JsonObject();
                error.addProperty("error", "Register" );
                error.addProperty("message", e.getMessage());
                return new Gson().toJson(error);

            }
            else{
                response.status(500);
                JsonObject error = new JsonObject();
                error.addProperty("error", "Register" );
                error.addProperty("message", e.getMessage());
                return new Gson().toJson(error);

            }
        }
    }

    private Object clear(Request request, Response response) throws DataAccessException {
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