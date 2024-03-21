package ui;

import com.google.gson.Gson;
import exception.ResponseException;
import requests.LoginRequest;
import requests.RegisterRequest;
import response.LoginResponse;
import response.RegisterResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ServerFacade {

    private final String serverUrl;
    static String authToken = null;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }


    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }


    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }


    public void register(String username, String password, String email) throws ResponseException {
        RegisterResponse response = this.makeRequest("POST", "/user",
                new RegisterRequest(username, password, email), RegisterResponse.class);
        authToken = response.getAuthToken();
    }

    public void login(String username, String password) throws ResponseException {
        LoginResponse response = this.makeRequest("POST", "/session",
                new LoginRequest(username, password), LoginResponse.class);
        authToken = response.getAuthToken();
    }

//
//    public void logout() {
//
//    }
//
//    public void create(String gameName) {
//
//    }
//
//    public void list() {
//
//    }
//
//    public void join(String id) {
//
//    }
//
//    public void observe(String gameID) {
//
//    }
}
