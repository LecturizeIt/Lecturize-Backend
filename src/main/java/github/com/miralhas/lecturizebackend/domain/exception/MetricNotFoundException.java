package github.com.miralhas.lecturizebackend.domain.exception;

public class MetricNotFoundException extends ResourceNotFoundException {

    public MetricNotFoundException(Long id) {
        super("Métrica de código %d não encontrada".formatted(id));
    }

    public MetricNotFoundException(Long id, Throwable cause) {
        super("Métrica de código %d não encontrada".formatted(id), cause);
    }
    
}
