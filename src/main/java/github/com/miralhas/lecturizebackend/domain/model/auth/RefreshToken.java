package github.com.miralhas.lecturizebackend.domain.model.auth;

import github.com.miralhas.lecturizebackend.domain.exception.RefreshTokenExpiredException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

	@Id
	@GeneratedValue(generator = "uuid2")
	private UUID id;

	@CreationTimestamp
	private OffsetDateTime createdAt;

	@Column(nullable = false)
	private OffsetDateTime expiresAt;

	@OneToOne(fetch = FetchType.LAZY)
	private User user;


	public void validate() {
		if (expiresAt.isBefore(OffsetDateTime.now())) {
			throw new RefreshTokenExpiredException("Refresh token of id %s expired at %s".formatted(id, expiresAt.toString()));
		}
	}
}
