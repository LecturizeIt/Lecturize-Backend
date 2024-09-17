package github.com.miralhas.lecturizebackend.domain.exception;

public class LectureNotFoundException extends ResourceNotFoundException {

    public LectureNotFoundException(Long id) {
        super("Palestra de código %d não encontrada".formatted(id));
    }

    public LectureNotFoundException(Long id, Throwable cause) {
        super("Palestra de código %d não encontrada".formatted(id), cause);
    }
    
}
