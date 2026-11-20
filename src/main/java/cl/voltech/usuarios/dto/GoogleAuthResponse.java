package cl.voltech.usuarios.dto;

public record GoogleAuthResponse(
    String token, UsuarioAutenticado usuario
) {
    public record UsuarioAutenticado(
        Integer id,
        String nombre,
        String email,
        String rol) {
    }
}
