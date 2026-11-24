package cl.voltech.usuarios.config;

import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

@Configuration
public class AppJwtConfig {

    @Bean
    public SecretKey appJwtKey(@Value("${app.jwt.secret}") String secretBase64) {
        byte[] bytes = Base64.getDecoder().decode(secretBase64);

        if (bytes.length < 32) {
            throw new IllegalArgumentException("APP_JWT_SECRET debe tener al menos 32 bytes");
        }

        return new SecretKeySpec(bytes, "HmacSHA256");
    }

    @Bean
    public JwtEncoder appJwtEncoder(SecretKey appJwtKey) {
        return NimbusJwtEncoder.withSecretKey(appJwtKey)
                .algorithm(MacAlgorithm.HS256)
                .build();
    }

    @Bean("appJwtDecoder")
    public JwtDecoder appJwtDecoder(SecretKey appJwtKey) {
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(appJwtKey)
            .macAlgorithm(MacAlgorithm.HS256)
            .build();

        decoder.setJwtValidator(
            JwtValidators.createDefaultWithIssuer("voltech-usuarios")
        );
        return decoder;
    }
}
