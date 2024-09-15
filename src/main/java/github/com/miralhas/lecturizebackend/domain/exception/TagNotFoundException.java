package github.com.miralhas.lecturizebackend.domain.exception;

public class TagNotFoundException extends ResourceNotFoundException {

    public TagNotFoundException(Long id) {
        super("Tag de código %d não encontrada".formatted(id));
    }

    public TagNotFoundException(Long id, Throwable cause) {
        super("Tag de código %d não encontrada".formatted(id), cause);
    }
}
