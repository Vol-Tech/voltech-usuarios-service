package cl.voltech.usuarios.service;

import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.voltech.usuarios.dto.GoogleAuthResponse;
import cl.voltech.usuarios.dto.GoogleAuthResponse.UsuarioAutenticado;
import cl.voltech.usuarios.model.Usuario;
import cl.voltech.usuarios.repository.UsuarioRepository;

@Service
public class GoogleAuthService {

    private final JwtDecoder jwtDecoder;
    private final UsuarioRepository usuarioRepository;

    public GoogleAuthService(JwtDecoder jwtDecoder, UsuarioRepository usuarioRepository) {
        this.jwtDecoder = jwtDecoder;
        this.usuarioRepository = usuarioRepository;
    }

    public GoogleAuthResponse autenticar(String credential) {
        try {
            Jwt jwt = jwtDecoder.decode(credential);
            String email = jwt.getClaimAsString("email");
            String nombre = jwt.getClaimAsString("name");
            return usuarioRepository.findByEmailIgnoreCase(email)
                    .map(usuario -> new GoogleAuthResponse(credential,
                            new UsuarioAutenticado(usuario.getId(), usuario.getNombre(), usuario.getEmail(),
                                    normalizarRol(usuario))))
                    .orElseGet(() -> new GoogleAuthResponse(credential,
                            new UsuarioAutenticado(null, nombre == null ? "Usuario" : nombre, email, "CLIENTE")));
        } catch (JwtException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credencial de Google inválida", exception);
        }
    }

    public String obtenerRol(String email) {
        return usuarioRepository.findByEmailIgnoreCase(email)
                .map(this::normalizarRol)
                .orElse("CLIENTE");
    }

    private String normalizarRol(Usuario usuario) {
        return usuario.getRol() == null ? "CLIENTE" : usuario.getRol().toUpperCase(Locale.ROOT);
    }
}
