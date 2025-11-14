package github.com.miralhas.lecturizebackend.domain.exception;

public abstract class ResourceNotFoundException extends BusinessException {

	public ResourceNotFoundException(String message) {
		super(message);
	}

	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
