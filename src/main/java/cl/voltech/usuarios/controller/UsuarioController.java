package cl.voltech.usuarios.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.voltech.usuarios.repository.UsuarioRepository;
import cl.voltech.usuarios.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioServ;

    public UsuarioController(UsuarioService usuarioServ) {
        this.usuarioServ = usuarioServ;
    }

    // @PostMapping
    // public ResponseEntity
}
