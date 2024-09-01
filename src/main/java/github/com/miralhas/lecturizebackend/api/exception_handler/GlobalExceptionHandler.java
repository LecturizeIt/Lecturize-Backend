package github.com.miralhas.lecturizebackend.api.exception_handler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import github.com.miralhas.lecturizebackend.domain.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUncaughtException(Exception ex, WebRequest webRequest) {
        if (ex instanceof AccessDeniedException authError) throw authError;
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var detail = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se "
                + "o problema persistir, entre em contato com o administrador do sistema.";
        var problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle("Erro de Sistema");
        problemDetail.setType(URI.create("https://localhost:8080/errors/erro-de-sistema"));
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
        String detail = messageSource.getMessage(
                "PasswordComparisonAuthenticator.badCredentials", new Object[]{}, LocaleContextHolder.getLocale()
        );
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
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

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
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        if (ex.getCause() instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) ex.getCause(), headers, status, request);
        }

        var detail = "O corpo da requisição está inválido. Verifique erros de sintaxe";
        var problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle("Mensagem incompreensivel");
        problemDetail.setType(URI.create("https://localhost:8080/errors/mensagem-incompreensivel"));
        return super.handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(
            InvalidFormatException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String path = joinPath(ex.getPath());
        String detail = String.format("A propriedade '%s' recebeu o valor '%s' que é de um tipo inválido. " +
                "Corrija e informe um valor compatível com o tipo '%s'", path, ex.getValue(), ex.getTargetType().getSimpleName());
        var problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle("Mensagem incompreensivel");
        problemDetail.setType(URI.create("https://localhost:8080/errors/mensagem-incompreensivel"));
        return super.handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

    private String joinPath(List<Reference> path) {
        return path.stream()
                .filter(f -> Objects.nonNull(f.getFieldName()))
                .map(Reference::getFieldName)
                .collect(Collectors.joining("."));
    }
}
