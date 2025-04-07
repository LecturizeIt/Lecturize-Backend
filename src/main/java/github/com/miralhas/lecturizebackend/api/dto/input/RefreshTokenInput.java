package github.com.miralhas.lecturizebackend.api.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
public class RefreshTokenInput {

	@UUID
	@NotBlank
	private String refreshToken;

}
