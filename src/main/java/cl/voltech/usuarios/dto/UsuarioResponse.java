package cl.voltech.usuarios.dto;

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
    String comunaNombre
) {
    
}
