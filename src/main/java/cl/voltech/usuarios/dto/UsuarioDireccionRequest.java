package cl.voltech.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioDireccionRequest(
    @NotBlank String calle,
    String depto,
    @NotNull Integer comunaId
) {}
