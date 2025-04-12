package github.com.miralhas.lecturizebackend.domain.service;

import github.com.miralhas.lecturizebackend.domain.exception.RefreshTokenExpiredException;
import github.com.miralhas.lecturizebackend.domain.exception.RefreshTokenNotFoundException;
import github.com.miralhas.lecturizebackend.domain.model.auth.RefreshToken;
import github.com.miralhas.lecturizebackend.domain.model.auth.User;
import github.com.miralhas.lecturizebackend.domain.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshTokenService {

	public static final int REFRESH_TOKEN_EXPIRATION_DATE_IN_SECONDS = 604800; // one week

	private final RefreshTokenRepository refreshTokenRepository;

	public RefreshToken getRefreshTokenOrExcepiton(UUID id) {
		return refreshTokenRepository.findById(id)
				.orElseThrow(() -> new RefreshTokenNotFoundException("Refresh Token of id %s not found".formatted(id)));
	}

	@Transactional(noRollbackFor = RefreshTokenExpiredException.class)
	public RefreshToken validateRefreshToken(String refreshTokenToBeValidated) {
		var refreshToken = getRefreshTokenOrExcepiton(UUID.fromString(refreshTokenToBeValidated));
		if (refreshToken.isInvalid()) {
			refreshTokenRepository.deleteAllUserRefreshTokens(refreshToken.getUser());
			refreshTokenRepository.flush();
			throw new RefreshTokenExpiredException("Refresh token of id %s expired at %s"
					.formatted(refreshToken.getId(), refreshToken.getExpiresAt()));
		}
		return refreshToken;
	}

	@Transactional
	public RefreshToken save(User user) {
		var refreshToken = RefreshToken.builder()
				.user(user)
				.expiresAt(OffsetDateTime.now().plusSeconds(REFRESH_TOKEN_EXPIRATION_DATE_IN_SECONDS))
				.build();
		return refreshTokenRepository.save(refreshToken);
	}

	@Transactional
	public RefreshToken update(RefreshToken refreshToken, User user) {
		refreshTokenRepository.delete(refreshToken);
		refreshTokenRepository.flush();
		return save(user);
	}

}
