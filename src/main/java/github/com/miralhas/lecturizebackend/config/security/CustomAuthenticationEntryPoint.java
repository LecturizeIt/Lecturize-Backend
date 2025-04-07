package github.com.miralhas.lecturizebackend.config.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException, ServletException {
		Map<String, String> parameters = new LinkedHashMap<>();
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());

		if (ex instanceof OAuth2AuthenticationException oauth2Exception) {
			problem.setProperty("isTokenInvalid", true);
			OAuth2Error error = oauth2Exception.getError();
			parameters.put("error", error.getErrorCode());
			if (StringUtils.hasText(error.getDescription())) {
				parameters.put("error_description", error.getDescription());
			}
			if (StringUtils.hasText(error.getUri())) {
				parameters.put("error_uri", error.getUri());
			}
			if (error instanceof BearerTokenError bearerTokenError) {
				if (StringUtils.hasText(bearerTokenError.getScope())) {
					parameters.put("scope", bearerTokenError.getScope());
				}
			}
		}

		problem.setTitle("Unauthorized");
		problem.setInstance(URI.create(request.getRequestURI()));
		problem.setType(URI.create("http://localhost:8080/jwt-error"));
		String problemDetailAsString = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_ABSENT).writeValueAsString(problem);
		String wwwAuthenticate = computeWWWAuthenticateHeaderValue(parameters);
		response.addHeader("WWW-Authenticate", wwwAuthenticate);
		response.setStatus(problem.getStatus());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.getWriter().write(problemDetailAsString);
	}

	private static String computeWWWAuthenticateHeaderValue(Map<String, String> parameters) {
		StringBuilder wwwAuthenticate = new StringBuilder();
		wwwAuthenticate.append("Bearer");
		if (!parameters.isEmpty()) {
			wwwAuthenticate.append(" ");
			int i = 0;
			for (Iterator<Map.Entry<String, String>> var3 = parameters.entrySet().iterator(); var3.hasNext(); ++i) {
				Map.Entry<String, String> entry = var3.next();
				wwwAuthenticate.append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
				if (i != parameters.size() - 1) {
					wwwAuthenticate.append(", ");
				}
			}
		}
		return wwwAuthenticate.toString();
	}

}
