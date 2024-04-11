package response;

public class LoginResponse {

    private String username;

    private String authToken;

    public LoginResponse(String username, String authToken) {
        this.authToken = authToken;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
