package cl.voltech.usuarios.config;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
            @Qualifier("appJwtDecoder") JwtDecoder decoder) throws Exception {
        JwtAuthenticationConverter roles = new JwtAuthenticationConverter();
        roles.setJwtGrantedAuthoritiesConverter(jwt -> {
            String rol = jwt.getClaimAsString("rol");
            return rol == null || rol.isBlank()
                    ? List.of()
                    : List.of(new SimpleGrantedAuthority("ROLE_" + rol.toUpperCase(Locale.ROOT)));
        });

        http.csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/v1/auth/verificar-correo").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/comunas").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/google", "/api/v1/auth/login",
                                "/api/v1/usuarios").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/usuarios").hasRole("ADMINISTRADOR")
                        .requestMatchers("/api/v1/usuarios/**").hasRole("ADMINISTRADOR")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(decoder)
                        .jwtAuthenticationConverter(roles)));
        return http.build();
    }
}
