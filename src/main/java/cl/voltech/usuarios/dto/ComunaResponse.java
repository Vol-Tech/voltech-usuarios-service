package cl.voltech.usuarios.dto;

public record ComunaResponse(
    Integer id,
    String nombre,
    Integer regionId,
    String regionNombre
) {

}
