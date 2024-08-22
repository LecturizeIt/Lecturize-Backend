package github.com.miralhas.lecturizebackend.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtDecoder jwtDecoder;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;
    private final CustomAccessDeniedHandlerImpl customAccessDeniedHandler;


    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        var authProvider = new DaoAuthenticationProvider(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(authProvider);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {
                    CorsConfigurationSource source = request -> {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(List.of("*"));
                        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                        config.setAllowedHeaders(List.of("*"));
                        return config;
                    };
                    cors.configurationSource(source);
                })
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(resourceServer -> {
                    resourceServer.jwt(jwt -> {
                        jwt.decoder(jwtDecoder);
                        jwt.jwtAuthenticationConverter(jwtAuthenticationConverter);
                    });
                    resourceServer.accessDeniedHandler(customAccessDeniedHandler);
                    resourceServer.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
                })
                .authorizeHttpRequests(authz -> {
//                    authz.requestMatchers(HttpMethod.GET, "/**").permitAll();
//                    authz.requestMatchers(HttpMethod.POST, "/api/auth/login", "/api/auth/register").permitAll();
//                    authz.anyRequest().authenticated();
                    authz.anyRequest().permitAll();
                })
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
