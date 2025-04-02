package github.com.miralhas.lecturizebackend.domain.exception;

public class ImageNotFoundException extends ResourceNotFoundException {

    public static final String MESSAGE = "Imagem da palestra de código %s não encontrada";

    public ImageNotFoundException(Long id) {
        super(String.format(MESSAGE, id));
    }

    public ImageNotFoundException(String fileName) {
        super(String.format(MESSAGE, fileName));
    }

    public ImageNotFoundException(Long id, Throwable cause) {
        super(String.format(MESSAGE, id), cause);
    }

}
