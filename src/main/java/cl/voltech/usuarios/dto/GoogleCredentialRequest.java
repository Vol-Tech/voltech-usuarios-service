package cl.voltech.usuarios.dto;

import jakarta.validation.constraints.NotBlank;

public record GoogleCredentialRequest(
    @NotBlank String credential
) {
}
