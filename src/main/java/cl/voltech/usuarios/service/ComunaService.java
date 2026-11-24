package cl.voltech.usuarios.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.voltech.usuarios.dto.ComunaResponse;
import cl.voltech.usuarios.mapper.ComunaMapper;
import cl.voltech.usuarios.repository.ComunaRepository;

@Service
public class ComunaService {

    private final ComunaRepository comunaRepo;
    private final ComunaMapper comunaMap;

    public ComunaService(ComunaRepository comunaRepo, ComunaMapper comunaMap) {
        this.comunaRepo = comunaRepo;
        this.comunaMap = comunaMap;
    }

    public List<ComunaResponse> listarTodas() {
        return comunaRepo.findAllByOrderByNombreAsc()
                .stream()
                .map(comunaMap::toResponse)
                .toList();
    }
}
