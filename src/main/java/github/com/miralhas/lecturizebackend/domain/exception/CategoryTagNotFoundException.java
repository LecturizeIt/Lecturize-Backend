package github.com.miralhas.lecturizebackend.domain.exception;

public class CategoryTagNotFoundException extends ResourceNotFoundException {

    public CategoryTagNotFoundException(Long id) {
        super("Tag de código %d não encontrada".formatted(id));
    }

    public CategoryTagNotFoundException(Long id, Throwable cause) {
        super("Tag de código %d não encontrada".formatted(id), cause);
    }
}
