package github.com.miralhas.lecturizebackend.domain.service;

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

	public static final int REFRESH_TOKEN_EXPIRATION_DATE_IN_SECONDS = 2592000;

	private final RefreshTokenRepository refreshTokenRepository;

	public RefreshToken getRefreshTokenOrExcepiton(UUID id) {
		return refreshTokenRepository.findById(id)
				.orElseThrow(() -> new RefreshTokenNotFoundException("Refresh Token of id %s not found".formatted(id)));
	}

	public RefreshToken getRefreshTokenByUserOrException(User user) {
		return refreshTokenRepository.findByUser(user)
				.orElseThrow(() -> new RefreshTokenNotFoundException(
						"Refresh Token for the user of email %s was not found".formatted(user.getEmail())
				));
	}

	public RefreshToken validateRefreshToken(String refreshTokenToBeValidated) {
		var refreshToken = getRefreshTokenOrExcepiton(UUID.fromString(refreshTokenToBeValidated));
		refreshToken.validate();
		return refreshToken;
	}

	@Transactional
	public RefreshToken createOrUpdateRefreshToken(User user) {
		var refresh = refreshTokenRepository.findByUser(user);
		if(refresh.isPresent()) {
			return update(refresh.get(), user);
		}
		return save(user);
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
