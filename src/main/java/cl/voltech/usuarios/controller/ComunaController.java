package cl.voltech.usuarios.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cl.voltech.usuarios.dto.ComunaResponse;

import cl.voltech.usuarios.service.ComunaService;

@RestController
@RequestMapping("/api/v1/comunas")
public class ComunaController {
    
    private final ComunaService comunaServ;

    public ComunaController(ComunaService comunaServ) {
        this.comunaServ = comunaServ;
    }

    @GetMapping
    public List<ComunaResponse> listarComunas() {
        return comunaServ.listarTodas();
    }
}
