package response;

public class CreateGameResponse {

    private final int gameID;
    private String authToken;
    public CreateGameResponse(int gameID) {
        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
