package cl.voltech.usuarios.mapper;

import org.springframework.stereotype.Component;
import cl.voltech.usuarios.dto.ComunaResponse;
import cl.voltech.usuarios.model.Comuna;

@Component
public class ComunaMapper {
    public ComunaResponse toResponse(Comuna comuna) {
        return new ComunaResponse(comuna.getId(), comuna.getNombre());
    }
}
