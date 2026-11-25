package cl.voltech.usuarios.dto;

import java.time.LocalDateTime;

public record UsuarioResponse(
    Integer id,
    String run,
    String nombre,
    String apellidos,
    String email,
    String rol,
    String calle,
    String depto,
    Integer comunaId,
    String comunaNombre,
    LocalDateTime fechaCreacion
) {
    
}
