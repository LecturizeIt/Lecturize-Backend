package github.com.miralhas.lecturizebackend.domain.exception;

public class CommentNotFoundException extends ResourceNotFoundException {

    public CommentNotFoundException(Long id) {
        super("Comentário de código %s não encontrado".formatted(id));
    }

    public CommentNotFoundException(Long id, Throwable cause) {
        super("Comentário de código %s não encontrado".formatted(id), cause);
    }
}
