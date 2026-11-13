package cl.voltech.usuarios.dto;

public record UsuarioResponse(
    Integer id,
    String run,
    String nombre,
    String apellidos,
    String email,
    String contraseña,
    String calle,
    String depto) {

}
