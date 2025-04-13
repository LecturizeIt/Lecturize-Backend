package github.com.miralhas.lecturizebackend.domain.service;

import github.com.miralhas.lecturizebackend.api.dto.AuthenticationDTO;
import github.com.miralhas.lecturizebackend.api.dto.input.LoginInput;
import github.com.miralhas.lecturizebackend.domain.exception.UserAlreadyExistsException;
import github.com.miralhas.lecturizebackend.domain.model.auth.User;
import github.com.miralhas.lecturizebackend.domain.repository.UserRepository;
import github.com.miralhas.lecturizebackend.domain.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final RoleService roleService;
    private final RefreshTokenService refreshTokenService;

    public User findUserByEmailOrException(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> {
            var message = "Usuário de email '%s' não foi encontrado".formatted(email);
            return new UsernameNotFoundException(message);
        });
    }

    @Transactional
    public AuthenticationDTO authenticate(LoginInput loginInput) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(loginInput.getEmail(), loginInput.getPassword());
        var authenticationResult = authenticationManager.authenticate(authenticationToken);
        var user = ((SecurityUser) authenticationResult.getPrincipal()).getUser();
        SecurityContextHolder.getContext().setAuthentication(authenticationResult);
        var jwt = tokenService.generateToken(user);
        var refreshToken = refreshTokenService.save(user);
        return new AuthenticationDTO(
                jwt.getTokenValue(),
                refreshToken.getId().toString(),
                TokenService.TOKEN_EXPIRATION_TIME,
                RefreshTokenService.REFRESH_TOKEN_EXPIRATION_DATE_IN_SECONDS
        );
    }

    @Transactional
    public User create(User user) {
        checkIfUsernameOrEmailAreAvailiable(user);
        var userRole = roleService.getUserRole();
        user.setRoles(Set.of(userRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return user;
    }

    private void checkIfUsernameOrEmailAreAvailiable(User user) {
        Map<String, String> errors = new HashMap<>();
        userRepository.findUserByEmail(user.getEmail())
                .ifPresent(u -> errors.put("email", "E-mail '%s' já está em uso".formatted(u.getEmail())));
        userRepository.findUserByUsername(user.getUsername())
                .ifPresent(u -> errors.put("username", "Username '%s' já está em uso".formatted(u.getUsername())));
        if (!errors.isEmpty()) {
            throw new UserAlreadyExistsException(errors);
        }
    }

}
