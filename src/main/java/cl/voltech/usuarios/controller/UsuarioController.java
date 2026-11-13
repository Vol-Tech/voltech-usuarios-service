package cl.voltech.usuarios.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.voltech.usuarios.dto.UsuarioCreateRequest;
import cl.voltech.usuarios.dto.UsuarioResponse;
import cl.voltech.usuarios.mapper.UsuarioMapper;
import cl.voltech.usuarios.model.Usuario;
import cl.voltech.usuarios.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioServ;
    private final UsuarioMapper usuarioMapper;

    public UsuarioController(UsuarioService usuarioServ, UsuarioMapper usuarioMapper) {
        this.usuarioServ = usuarioServ;
        this.usuarioMapper = usuarioMapper;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> agregarUsuario(@Valid @RequestBody UsuarioCreateRequest request) {
        Usuario nuevoUsuario = usuarioServ.crearUsuario(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toResponse(nuevoUsuario));
    }
}
