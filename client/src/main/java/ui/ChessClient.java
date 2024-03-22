package ui;

import com.google.gson.Gson;
import exception.ResponseException;

import java.util.Arrays;

public class ChessClient {

    private String username = null;
    private String password = null;
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
                    case "create" -> create(params);
                    case "list" -> list();
                    case "join" -> join(params);
                    case "observe" -> observe(params);
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
        try {
            if (params.length == 3) {
                state = State.SIGNEDIN;
                username = params[0];
                password = params[1];
                String email = params[2];
                server.register(username, password, email);
                return String.format("You registered as %s.", username);
            }
        } catch (ResponseException e) {
            return "Register error exception: " + e.getMessage();
        }
        return "Register error: incorrect usage";
    }

    public String login(String... params) throws ResponseException {
        try {
            if (params.length == 2) {
                state = State.SIGNEDIN;
                username = params[0];
                password = params[1];
                server.login(username, password);
                return String.format("You logged in as %s.", username);
            }
        }
        catch (ResponseException e) {
            return "Login error exception: " + e.getMessage();
        }
        return "Login error: incorrect usage";
    }

    public String create(String... params) throws ResponseException {
        try {
            if (params.length == 1) {
                assertSignedIn();
                String gameName = params[0];
                server.create(gameName);
                return String.format("Created game %s.", gameName);
            }
        } catch (ResponseException e) {
            return "Create Game error: " + e.getMessage();
        }
        return "Create Game error: incorrect usage";
    }

    public String list() throws ResponseException {
        try {
            assertSignedIn();
            var chessGames = server.list();
            var result = new StringBuilder();
            var gson = new Gson();
            for (var game : chessGames) {
                result.append(gson.toJson(game)).append('\n');
            }
            return result.toString();
        } catch (ResponseException e) {
            return "List Games error exception: " + e.getMessage();
        }
    }


    public String join(String... params) throws ResponseException {
        try {
            if (params.length == 2) {
                assertSignedIn();
                int gameID = Integer.parseInt(params[0]);
                String playerColor = params[1].toUpperCase();
                server.join(gameID, playerColor);
                //TODO draw the board
                return String.format("Joined game %s as the %s player", gameID, playerColor);
            }
        } catch (ResponseException e) {
            return "Join Game error exception: " + e.getMessage();
        }
        return "Join Game error: incorrect usage";
    }

    public String observe(String... params) throws ResponseException {
        try {
            if (params.length == 1) {
                assertSignedIn();
                int gameID = Integer.parseInt(params[0]);
                server.observe(gameID);
                //TODO draw the board
                return String.format("Observing game %d", gameID);
            }
        } catch (ResponseException e) {
            return "Observe Game error exception: " + e.getMessage();
        }
        return "Observe Game error: incorrect usage";
    }

    public String logout() throws ResponseException {
        try {
            assertSignedIn();
            server.logout();
            state = State.SIGNEDOUT;
            return String.format("%s logged out", username);
        } catch (ResponseException e) {
            return "Logout error exception: " + e.getMessage();
        }
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
