package github.com.miralhas.lecturizebackend.domain.exception;

public class UserAlreadyExistsException extends BusinessException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
