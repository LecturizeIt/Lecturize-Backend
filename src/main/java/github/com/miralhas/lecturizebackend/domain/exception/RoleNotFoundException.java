package github.com.miralhas.lecturizebackend.domain.exception;

public class RoleNotFoundException extends ResourceNotFoundException {

    public RoleNotFoundException(String name) {
        super("Role de nome %s não encontrada".formatted(name));
    }

    public RoleNotFoundException(String name, Throwable cause) {
        super("Role de nome %s não encontrada".formatted(name), cause);
    }
}
