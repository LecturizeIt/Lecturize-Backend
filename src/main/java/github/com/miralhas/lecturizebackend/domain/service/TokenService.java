package github.com.miralhas.lecturizebackend.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenService {

    public static final int TOKEN_EXPIRATION_TIME = 86400;
    private final JwtEncoder jwtEncoder;

    public Jwt generateToken(Authentication authentication) {
        Instant now = Instant.now();
        var scopes = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("lecturize-it")
                .subject(authentication.getName())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(TOKEN_EXPIRATION_TIME))
                .claim("scope", scopes)
                .build();

        var header = JwsHeader.with(SignatureAlgorithm.RS256).type("JWT").build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims));
    }

}