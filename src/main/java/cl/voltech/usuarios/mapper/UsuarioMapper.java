package cl.voltech.usuarios.mapper;


import cl.voltech.usuarios.dto.UsuarioCreateRequest;
import cl.voltech.usuarios.dto.UsuarioResponse;
import cl.voltech.usuarios.model.Usuario;

public interface UsuarioMapper {

    public static Usuario toModel(UsuarioCreateRequest request) {
        return new Usuario(
            request.run(),
            request.nombre(),
            request.apellidos(),
            request.email(),
            request.contraseña(),
            request.calle(),
            request.depto()
        );
    }
}
