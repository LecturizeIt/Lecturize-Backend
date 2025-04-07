package github.com.miralhas.lecturizebackend.domain.exception;

public class RefreshTokenNotFoundException extends ResourceNotFoundException {

	public RefreshTokenNotFoundException(String message) {
		super(message);
	}

	public RefreshTokenNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
