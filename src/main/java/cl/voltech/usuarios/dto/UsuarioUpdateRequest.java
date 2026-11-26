package cl.voltech.usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioUpdateRequest(
    @NotBlank String nombre,
    @NotBlank String apellidos,
    @NotBlank @Email String email,
    @NotBlank String calle,
    String depto,
    @NotNull Integer comunaId
) {}
