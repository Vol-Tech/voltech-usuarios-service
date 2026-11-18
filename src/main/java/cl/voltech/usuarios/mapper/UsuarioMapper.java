package cl.voltech.usuarios.mapper;

import cl.voltech.usuarios.dto.UsuarioCreateRequest;
import cl.voltech.usuarios.dto.UsuarioResponse;
import cl.voltech.usuarios.model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    private final PasswordEncoder passwordEncoder;

    public UsuarioMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario toModel(UsuarioCreateRequest request) {
        Usuario usuario = new Usuario();
        usuario.setRun(request.run());
        usuario.setNombre(request.nombre());
        usuario.setApellidos(request.apellidos());
        usuario.setEmail(request.email());
        usuario.setContraseña(passwordEncoder.encode(request.contraseña()));
        usuario.setCalle(request.calle());
        usuario.setDepto(request.depto());
        return usuario;
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
            usuario.getId(),
            usuario.getRun(),
            usuario.getNombre(),
            usuario.getApellidos(),
            usuario.getEmail(),
            usuario.getCalle(),
            usuario.getDepto(),
            usuario.getComuna().getId(),
            usuario.getComuna().getNombre()
        );
    }
}
