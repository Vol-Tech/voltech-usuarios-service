package cl.voltech.usuarios.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UsuarioAdminCreateRequest(
    @Valid @NotNull UsuarioCreateRequest usuario,
    @NotBlank
    @Pattern(regexp = "CLIENTE|VENDEDOR|ADMINISTRADOR", flags = Pattern.Flag.CASE_INSENSITIVE,
        message = "El rol debe ser CLIENTE, VENDEDOR o ADMINISTRADOR")
    String rol
) {}
