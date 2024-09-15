package github.com.miralhas.lecturizebackend.config.start;

import github.com.miralhas.lecturizebackend.domain.model.auth.Role;
import github.com.miralhas.lecturizebackend.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
@RequiredArgsConstructor
public class AuthStart implements CommandLineRunner {

	private final RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
		checkRole(Role.Value.USER);
		checkRole(Role.Value.ADMIN);
	}

	private void checkRole(Role.Value value) {
		roleRepository.findRoleByName(value).ifPresentOrElse(role -> {}, () -> {
			var roleEntity = new Role();
			roleEntity.setName(value);
			roleRepository.save(roleEntity);
		});
	}
}
