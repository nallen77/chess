package ui;

import exception.ResponseException;

import java.util.Arrays;

public class ChessClient {

    private String username = null;
    private String password = null;
    private String email = null;
    private String gameName = null;
    private String gameID = null;
    private final ServerFacade server;
    private final String serverUrl;
    private State state = State.SIGNEDOUT;
    public ChessClient(String serverUrl, Repl repl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    public String eval(String line) {
        try {
            var tokens = line.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            if (state == State.SIGNEDOUT) {
                return switch (cmd) {
                    case "register" -> register(params);
                    case "login" -> login(params);
                    case "quit" -> "quit";
                    default -> help();
                };
            }
            else {
                return switch (cmd) {
                    case "create <NAME>" -> create(params);
                    case "list" -> list();
                    case "join <ID>" -> join(params);
                    case "observe <ID>" -> observe(params);
                    case "logout" -> logout();
                    case "quit" -> "quit";
                    default -> help();
                };
            }
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String register(String... params) throws ResponseException {
        if (params.length >= 1) {
            state = State.SIGNEDIN;
            username = String.join("-", params);
            password = ;//TODO
            email = ;//TODO
            server.register(username, password, email);
            return String.format("You registered as %s.", username);
        }
        throw new ResponseException(400, "Failed to register");
    }

    public String login(String... params) throws ResponseException {
        if (params.length >= 1) {
            state = State.SIGNEDIN;
            username = String.join("-", params);
            password = ;//TODO
            server.login(username);
            return String.format("You logged in as %s.", username);
        }
        throw new ResponseException(400, "Failed to login");
    }

    public String create(String... params) throws ResponseException {
        assertSignedIn();
        gameName = String.join("-", params);
        server.create(gameName);
        return String.format("Created game %s.", gameName);
    }

    public String list() throws ResponseException {
        assertSignedIn();
        server.list();
        return "Game List:";//TODO what should this return?
    }

    public String join(String... params) throws ResponseException { //TODO [WHITE|BLACK|<empty>] ??
        assertSignedIn();
        gameID = String.join("-", params);
        server.join(gameID);
        return String.format("Joined game with ID: %s", gameID);
    }

    public String observe(String... params) throws ResponseException {
        assertSignedIn();
        gameID = String.join("-", params);
        server.observe(gameID);
        return String.format("Observing game with ID: %s", gameID);
    }

    public String logout() throws ResponseException {
        assertSignedIn();
        server.logout();
        state = State.SIGNEDOUT;
        return String.format("%s logged out", username);
    }

    public String help() {
        if (state == State.SIGNEDOUT) {
            return """
                    - register <USERNAME> <PASSWORD> <EMAIL>
                    - login <USERNAME> <PASSWORD>
                    - quit
                    """;
        }
        return """
                - create <NAME>
                - list
                - join <ID> [WHITE|BLACK|<empty>]
                - observe <ID>
                - logout
                - quit
                """;
    }

    private void assertSignedIn() throws ResponseException {
        if (state == State.SIGNEDOUT) {
            throw new ResponseException(400, "You must login to perform this action");
        }
    }
}
