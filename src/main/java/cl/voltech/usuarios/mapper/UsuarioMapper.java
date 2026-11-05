package cl.voltech.usuarios.mapper;

import org.springframework.stereotype.Component;

import cl.voltech.usuarios.dto.UsuarioResponse;
import cl.voltech.usuarios.model.Usuario;

@Component
public class UsuarioMapper {

    public UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getRun(),
                usuario.getNombre(),
                usuario.getApellidos(),
                usuario.getEmail(),
                usuario.getContraseña(),
                usuario.getCalle(),
                usuario.getDepto(),
                usuario.getRol(),
                usuario.getFechaCreacion());
    }
}
