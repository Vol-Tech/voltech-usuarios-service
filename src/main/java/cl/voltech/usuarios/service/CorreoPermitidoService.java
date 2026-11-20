package cl.voltech.usuarios.service;

import java.util.Locale;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CorreoPermitidoService {

    private static final Set<String> DOMINIOS = Set.of("duoc.cl", "profesor.duoc.cl", "gmail.com");

    public boolean esPermitido(String email) {
        if (email == null || !email.equals(email.strip()) || email.chars().anyMatch(Character::isWhitespace)) {
            return false;
        }
        int arroba = email.indexOf('@');
        return arroba > 0 && arroba == email.lastIndexOf('@')
                && DOMINIOS.contains(email.substring(arroba + 1).toLowerCase(Locale.ROOT));
    }

    public void exigirPermitido(String email) {
        if (!esPermitido(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El correo debe pertenecer a @duoc.cl, @profesor.duoc.cl o @gmail.com");
        }
    }
}
