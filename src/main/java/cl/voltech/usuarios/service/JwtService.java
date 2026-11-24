package cl.voltech.usuarios.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Locale;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String crearToken(String email, String rol) {
        Instant ahora = Instant.now();
        String rolNormalizado = rol == null
            ? "CLIENTE"
            : rol.toUpperCase(Locale.ROOT);

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("voltech-usuarios")
            .subject(email)
            .issuedAt(ahora)
            .expiresAt(ahora.plus(Duration.ofHours(2)))
            .claim("rol", rolNormalizado)
            .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims))
            .getTokenValue();
    }
}
