package ui;

import java.util.Arrays;

public class ChessClient {

    private String visitorName = null;
    private final ServerFacade server;
    private final String serverUrl;
    private State state = State.SIGNEDOUT;
    public ChessClient(String serverUrl, Repl repl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
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
                    case "list" -> list(params);
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
            visitorName = String.join("-", params);
            ws = new WebSocketFacade(serverUrl, notificationHandler);
            ws.enterPetShop(visitorName);
            return String.format("You signed in as %s.", visitorName);
        }
        throw new ResponseException(400, "Expected: <yourname>");
    }

    public String login(String... params) {
        return "";
    }


    public String logout() throws ResponseException {
        assertSignedIn();
        ws.leavePetShop(visitorName);
        ws = null;
        state = State.SIGNEDOUT;
        return String.format("%s logged out", visitorName);
    }

    public String create(String... params) {
        return null;
    }

    public String list(String... params) {
        return null;
    }

    public String join(String... params) {
        return null;
    }

    public String observe(String... params) {
        return null;
    }

    public String help() {
        if (state == State.SIGNEDOUT) {
            return """
                    - register
                    - login <yourname>
                    - quit
                    """;
        }
        return """
                - create <NAME>
                - list
                - join <ID>
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
