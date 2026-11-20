package cl.voltech.usuarios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import cl.voltech.usuarios.service.GoogleTokenValidator;

@Configuration
public class GoogleJwtConfig {

    @Bean
    public JwtDecoder jwtDecoder(GoogleTokenValidator googleTokenValidator) {
        NimbusJwtDecoder decoder = NimbusJwtDecoder
                .withJwkSetUri("https://www.googleapis.com/oauth2/v3/certs").build();
        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(
                JwtValidators.createDefaultWithIssuer("https://accounts.google.com"),
                googleTokenValidator));
        return decoder;
    }
}
