package github.com.miralhas.lecturizebackend.domain.exception;

public class RefreshTokenExpiredException extends BusinessException {

  public RefreshTokenExpiredException(String message) {
    super(message);
  }

  public RefreshTokenExpiredException(String message, Throwable cause) {
    super(message, cause);
  }

}
