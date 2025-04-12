package github.com.miralhas.lecturizebackend.domain.repository;

import github.com.miralhas.lecturizebackend.domain.model.auth.RefreshToken;
import github.com.miralhas.lecturizebackend.domain.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

	@Modifying
	@Query("delete from RefreshToken rt where rt.user = :user")
	void deleteAllUserRefreshTokens(User user);

}