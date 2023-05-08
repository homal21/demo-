package project.bookstore.exception;

import org.springframework.http.HttpStatus;

public class AppApiException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public AppApiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
