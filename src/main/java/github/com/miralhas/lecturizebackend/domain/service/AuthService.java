package github.com.miralhas.lecturizebackend.domain.service;

import github.com.miralhas.lecturizebackend.api.dto.input.LoginInput;
import github.com.miralhas.lecturizebackend.domain.exception.UserAlreadyExistsException;
import github.com.miralhas.lecturizebackend.domain.model.Role;
import github.com.miralhas.lecturizebackend.domain.model.User;
import github.com.miralhas.lecturizebackend.domain.repository.RoleRepository;
import github.com.miralhas.lecturizebackend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public User findUserByEmailOrException(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> {
                    var message = "Usuário de email '%s' não foi encontrado".formatted(email);
                    return new UsernameNotFoundException(message);
                });
    }

    public Jwt authenticate(LoginInput loginInput) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(
                loginInput.getEmail(), loginInput.getPassword()
        );
        var authenticationResult = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticationResult);
        return tokenService.generateToken(authenticationResult);
    }

    @Transactional
    public User create(User user) {
        checkIfUsernameOrEmailAreAvailiable(user);
        var userRole = roleService.getUserRole();
        user.setRoles(Set.of(userRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    private void checkIfUsernameOrEmailAreAvailiable(User user) {
        userRepository.findUserByEmail(user.getEmail())
                .map(u -> {
                    throw new UserAlreadyExistsException(String.format("E-mail '%s' já está em uso", user.getEmail()));
                });
        userRepository.findUserByUsername(user.getUsername())
                .map(u -> {
                    throw new UserAlreadyExistsException(String.format("Username '%s' já está em uso", user.getUsername()));
                });
    }
}