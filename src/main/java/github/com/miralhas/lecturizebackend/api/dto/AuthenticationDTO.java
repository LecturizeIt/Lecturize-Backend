package github.com.miralhas.lecturizebackend.api.dto;

public record AuthenticationDTO(
		String accessToken,
		String refreshToken,
		int accessTokenExpiresIn,
		int refreshTokenExpiresIn
) {
}
