package github.com.miralhas.lecturizebackend.domain.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class UserAlreadyExistsException extends BusinessException {

    private final Map<String, String> errors;

    public UserAlreadyExistsException(Map<String, String> errors) {
        super("The provided username or email is already in use. Please choose a different one.");
        this.errors = errors;
    }

    public UserAlreadyExistsException(Map<String, String> errors, Throwable cause) {
        super("The provided username or email is already in use. Please choose a different one.", cause);
        this.errors = errors;
    }
    
}
