package github.com.miralhas.lecturizebackend.infrastructure.exception;

public class EmailException extends RuntimeException {

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailException(String message) {
        super(message);
    }
}
