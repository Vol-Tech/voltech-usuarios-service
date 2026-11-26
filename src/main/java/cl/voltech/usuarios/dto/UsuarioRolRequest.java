package cl.voltech.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UsuarioRolRequest(
    @NotBlank
    @Pattern(regexp = "CLIENTE|VENDEDOR|ADMINISTRADOR", flags = Pattern.Flag.CASE_INSENSITIVE,
        message = "El rol debe ser CLIENTE, VENDEDOR o ADMINISTRADOR")
    String rol
) {}
