package response;

public class JoinGameResponse {
    private String authToken;
    private int gameID;
    private String playerColor;
    private String errorMessage;

    public JoinGameResponse(String authToken, int gameID, String playerColor, String errorMessage) {
        this.authToken = authToken;
        this.gameID = gameID;
        this.playerColor = playerColor;
        this.errorMessage = errorMessage;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
