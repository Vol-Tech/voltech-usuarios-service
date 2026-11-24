package cl.voltech.usuarios.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class GoogleTokenValidator implements OAuth2TokenValidator<Jwt> {

    private final String clientId;
    private final CorreoPermitidoService correos;

    public GoogleTokenValidator(@Value("${google.client-id}") String clientId, CorreoPermitidoService correos) {
        this.clientId = clientId;
        this.correos = correos;
    }

    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        String email = jwt.getClaimAsString("email");
        String dominioGoogle = jwt.getClaimAsString("hd");
        boolean esInstitucional = email != null &&
                (email.toLowerCase(Locale.ROOT).endsWith("@duoc.cl")
                        || email.toLowerCase(Locale.ROOT).endsWith("@profesor.duoc.cl"));
        boolean cuentaDuoc = dominioGoogle != null &&
                (dominioGoogle.equalsIgnoreCase("duoc.cl") || dominioGoogle.equalsIgnoreCase("profesor.duoc.cl"));

        if (!jwt.getAudience().contains(clientId)
                || !Boolean.TRUE.equals(jwt.getClaim("email_verified"))
                || !correos.esPermitido(email)
                || (esInstitucional && !cuentaDuoc)) {
            return OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_token",
                    "Audiencia, correo o dominio de Google no permitido", null));
        }
        return OAuth2TokenValidatorResult.success();
    }
}
