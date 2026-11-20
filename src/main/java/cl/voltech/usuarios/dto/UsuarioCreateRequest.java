package cl.voltech.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;

public record UsuarioCreateRequest(
    @NotBlank(message = "El RUN no puede estar vacío")
    String run,

    @NotBlank(message = "El NOMBRE no puede estar vacío")
    String nombre,

    @NotBlank(message = "Los APELLIDOS no pueden estar vacíos")
    String apellidos,

    @NotBlank(message = "El CORREO no puede estar vacío")
    @Email(message = "El correo no tiene un formato válido")
    String email,

    @NotBlank(message = "La CONTRASENA no puede estar vacía")
    String contraseña,

    String calle,
    String depto,

    @NotNull(message = "La comuna es obligatoria")
    Integer comunaId
) {

}
