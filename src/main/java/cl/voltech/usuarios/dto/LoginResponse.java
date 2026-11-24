package cl.voltech.usuarios.dto;

public record LoginResponse(
    String token,
    UsuarioResponse usuario
) {}
