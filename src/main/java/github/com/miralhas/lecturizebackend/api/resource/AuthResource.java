package github.com.miralhas.lecturizebackend.api.resource;

import github.com.miralhas.lecturizebackend.api.dto.LoginDTO;
import github.com.miralhas.lecturizebackend.api.dto.UserDTO;
import github.com.miralhas.lecturizebackend.api.dto.input.CreateUserInput;
import github.com.miralhas.lecturizebackend.api.dto.input.LoginInput;
import github.com.miralhas.lecturizebackend.api.dto_mapper.LectureMapper;
import github.com.miralhas.lecturizebackend.api.dto_mapper.UserMapper;
import github.com.miralhas.lecturizebackend.api.dto_mapper.UserUnmapper;
import github.com.miralhas.lecturizebackend.domain.model.auth.User;
import github.com.miralhas.lecturizebackend.domain.repository.UserRepository;
import github.com.miralhas.lecturizebackend.domain.service.AuthService;
import github.com.miralhas.lecturizebackend.domain.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthResource {

    private final UserUnmapper userUnmapper;
    private final AuthService authService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO register(@RequestBody @Valid CreateUserInput createUserInput) {
        User user = userUnmapper.toDomainObject(createUserInput);
        user = authService.create(user);
        return userMapper.toModel(user);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginDTO login(@RequestBody @Valid LoginInput loginInput) {
        var jwt = authService.authenticate(loginInput);
        return new LoginDTO(jwt.getTokenValue(), TokenService.TOKEN_EXPIRATION_TIME);
    }
}
