package github.com.miralhas.lecturizebackend.domain.service;

import github.com.miralhas.lecturizebackend.api.dto_mapper.UserMapper;
import github.com.miralhas.lecturizebackend.domain.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenService {

    public static final int TOKEN_EXPIRATION_TIME = 30;
    private final JwtEncoder jwtEncoder;
    private final UserMapper userMapper;

    public Jwt generateToken(Authentication authentication) {
        Instant now = Instant.now();
        var user = ((SecurityUser) authentication.getPrincipal()).getUser();
        var userMapped = userMapper.toModel(user);
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("lecturize-it")
                .subject(userMapped.getEmail())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(TOKEN_EXPIRATION_TIME))
                .claim("user", userMapped)
                .build();
        var header = JwsHeader.with(SignatureAlgorithm.RS256).type("JWT").build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims));
    }
}
