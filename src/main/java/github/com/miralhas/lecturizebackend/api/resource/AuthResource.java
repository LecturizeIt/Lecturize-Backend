package github.com.miralhas.lecturizebackend.api.resource;

import github.com.miralhas.lecturizebackend.api.dto.AuthenticationDTO;
import github.com.miralhas.lecturizebackend.api.dto.UserDTO;
import github.com.miralhas.lecturizebackend.api.dto.input.CreateUserInput;
import github.com.miralhas.lecturizebackend.api.dto.input.LoginInput;
import github.com.miralhas.lecturizebackend.api.dto.input.RefreshTokenInput;
import github.com.miralhas.lecturizebackend.api.dto_mapper.UserMapper;
import github.com.miralhas.lecturizebackend.api.dto_mapper.UserUnmapper;
import github.com.miralhas.lecturizebackend.config.swagger.interfaces.SwaggerAuthResource;
import github.com.miralhas.lecturizebackend.domain.model.auth.User;
import github.com.miralhas.lecturizebackend.domain.service.AuthService;
import github.com.miralhas.lecturizebackend.domain.service.RefreshTokenService;
import github.com.miralhas.lecturizebackend.domain.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthResource implements SwaggerAuthResource {

    private final UserUnmapper userUnmapper;
    private final AuthService authService;
    private final UserMapper userMapper;
    private final RefreshTokenService refreshTokenService;
    private final TokenService tokenService;

    @Override
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO register(@RequestBody @Valid CreateUserInput createUserInput) {
        User user = userUnmapper.toDomainObject(createUserInput);
        user = authService.create(user);
        return userMapper.toModel(user);
    }

    @Override
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationDTO login(@RequestBody @Valid LoginInput loginInput) {
        return authService.authenticate(loginInput);
    }

    @Override
    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationDTO refreshToken(@RequestBody @Valid RefreshTokenInput refreshTokenInput) {
        var refreshToken = refreshTokenService.validateRefreshToken(refreshTokenInput.getRefreshToken());
        var user = authService.findUserByEmailOrException(refreshToken.getUser().getEmail());
        var newAccessToken = tokenService.generateToken(user);
        var newRefreshToken = refreshTokenService.update(refreshToken,user);
        return new AuthenticationDTO(
                newAccessToken.getTokenValue(),
                newRefreshToken.getId().toString(),
                TokenService.TOKEN_EXPIRATION_TIME,
                RefreshTokenService.REFRESH_TOKEN_EXPIRATION_DATE_IN_SECONDS
        );
    }

}
