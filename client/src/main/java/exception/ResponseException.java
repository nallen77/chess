package exception;

public class ResponseException {
    final private int statusCode;

    public ResponseException(int statusCode, String message) {
        super();
        this.statusCode = statusCode;
    }

    public int StatusCode() {
        return statusCode;
    }
}