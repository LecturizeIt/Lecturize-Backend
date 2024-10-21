package github.com.miralhas.lecturizebackend.api.exception_handler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import github.com.miralhas.lecturizebackend.domain.exception.BusinessException;
import github.com.miralhas.lecturizebackend.domain.exception.ResourceNotFoundException;
import github.com.miralhas.lecturizebackend.domain.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUncaughtException(Exception ex, WebRequest webRequest) {
        log.error("Internal Server Error Exception:", ex);
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var detail = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se " + "o problema persistir, entre em contato com o administrador do sistema.";
        var problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle("Erro de Sistema");
        problemDetail.setType(URI.create("https://localhost:8080/errors/erro-de-sistema"));
        return problemDetail;
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ProblemDetail handleUserAlreadyExistsException(UserAlreadyExistsException ex, WebRequest webRequest) {
        var errors = ex.getErrors();
        var detail = ex.getMessage();
        var status = HttpStatus.CONFLICT;
        var problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle("User Already Exists");
        problemDetail.setType(URI.create("https://localhost:8080/errors/user-already-exists"));
        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException ex, WebRequest webRequest) {
        var detail = ex.getMessage();
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, detail);
        problemDetail.setTitle("Forbidden");
        problemDetail.setType(URI.create("http://localhost:8080/forbidden-access"));
        return problemDetail;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Recurso não encontrado");
        problemDetail.setType(URI.create("http://localhost:8080/error/recurso-nao-encontrado"));
        return problemDetail;
    }

    @ExceptionHandler(BusinessException.class)
    public ProblemDetail handleBusinessException(BusinessException ex, WebRequest webRequest) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Requisição Inválida");
        problemDetail.setType(URI.create("http://localhost:8080/error/requisicao-invalida"));
        return problemDetail;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentialsException(BadCredentialsException ex, WebRequest webRequest) {
        String detail = messageSource.getMessage("PasswordComparisonAuthenticator.badCredentials", new Object[]{}, LocaleContextHolder.getLocale());
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, detail);
        problemDetail.setTitle("Autenticação Inválida");
        problemDetail.setType(URI.create("http://localhost:8080/error/authentication"));
        return problemDetail;
    }

    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuthenticationException(AuthenticationException ex, WebRequest webRequest) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        problemDetail.setTitle("Autenticação Inválida");
        problemDetail.setType(URI.create("http://localhost:8080/error/authentication"));
        return problemDetail;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var errorsMap = new HashMap<String, String>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            errorsMap.put(error.getField(), message);
        });
        var detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente";
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail);
        problemDetail.setTitle("Campos Inválidos");
        problemDetail.setType(URI.create("http://localhost:8080/error/campos-invalidos"));
        problemDetail.setProperty("errors", errorsMap);
        return super.handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var detail = "O recurso /%s que você tentou acessar, é inexistente.".formatted(ex.getResourcePath());
        var problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle("Recurso Inexistente");
        problemDetail.setType(URI.create("https://localhost:8080/errors/recurso-inexistente"));
        return super.handleExceptionInternal(ex, problemDetail, headers, status, request);
    }


    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String detail = String.format("Método HTTP '%s' não é suportado por este endpoint. " + "Por favor, use um dos seguintes métodos suportados: %s", ex.getMethod(), ex.getSupportedHttpMethods());
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.METHOD_NOT_ALLOWED, detail);
        problemDetail.setTitle("Método HTTP não permitido");
        problemDetail.setType(URI.create("https://localhost:8080/errors/metodo-nao-suportado"));
        return super.handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Object[] args = new Object[]{ex.getPropertyName(), ex.getValue(), ex.getRequiredType().getSimpleName()};
        String defaultDetail = "Falha ao converter '" + args[0] + "' de valor: '" + args[1] + "'. " + "Corrija e informe um valor compatível com o tipo '" + args[2] + "'";
        ProblemDetail body = this.createProblemDetail(ex, status, defaultDetail, null, args, request);
        return this.handleExceptionInternal(ex, body, headers, status, request);
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (ex.getCause() instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) ex.getCause(), headers, status, request);
        }
        var detail = "O corpo da requisição está inválido. Verifique erros de sintaxe";
        var problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle("Mensagem incompreensivel");
        problemDetail.setType(URI.create("https://localhost:8080/errors/mensagem-incompreensivel"));
        return super.handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String path = joinPath(ex.getPath());
        String detail = String.format("A propriedade '%s' recebeu o valor '%s' que é de um tipo inválido. " + "Corrija e informe um valor compatível com o tipo '%s'", path, ex.getValue(), ex.getTargetType().getSimpleName());
        var problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle("Mensagem incompreensivel");
        problemDetail.setType(URI.create("https://localhost:8080/errors/mensagem-incompreensivel"));
        return super.handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

    private String joinPath(List<Reference> path) {
        return path.stream().map(Reference::getFieldName).filter(Objects::nonNull).collect(Collectors.joining("."));
    }

}