package cl.voltech.usuarios.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.voltech.usuarios.dto.UsuarioAdminCreateRequest;
import cl.voltech.usuarios.dto.UsuarioCreateRequest;
import cl.voltech.usuarios.dto.UsuarioDireccionRequest;
import cl.voltech.usuarios.dto.UsuarioEstadisticasResponse;
import cl.voltech.usuarios.dto.UsuarioResponse;
import cl.voltech.usuarios.dto.UsuarioRolRequest;
import cl.voltech.usuarios.dto.UsuarioUpdateRequest;
import cl.voltech.usuarios.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioServ;

    public UsuarioController(UsuarioService usuarioServ) {
        this.usuarioServ = usuarioServ;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> agregarUsuario(@Valid @RequestBody UsuarioCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioServ.crearUsuario(request));
    }

    @PostMapping("/administracion")
    public ResponseEntity<UsuarioResponse> agregarUsuarioAdministracion(
            @Valid @RequestBody UsuarioAdminCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioServ.crearUsuarioAdministracion(request));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {
        return ResponseEntity.ok(usuarioServ.listarTodosUsuarios());
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<UsuarioEstadisticasResponse> obtenerEstadisticas() {
        return ResponseEntity.ok(usuarioServ.obtenerEstadisticas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtenerUsuario(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioServ.obtenerUsuarioId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizarUsuario(@PathVariable Integer id,
            @Valid @RequestBody UsuarioUpdateRequest request) {
        return ResponseEntity.ok(usuarioServ.actualizarUsuario(id, request));
    }

    @PutMapping("/{id}/rol")
    public ResponseEntity<UsuarioResponse> actualizarRol(@PathVariable Integer id,
            @Valid @RequestBody UsuarioRolRequest request) {
        return ResponseEntity.ok(usuarioServ.actualizarRol(id, request.rol()));
    }

    @PutMapping("/{id}/direccion")
    public ResponseEntity<UsuarioResponse> actualizarDireccion(@PathVariable Integer id,
            @Valid @RequestBody UsuarioDireccionRequest request, Authentication auth) {
        return ResponseEntity.ok(usuarioServ.actualizarDireccion(id, request, auth));
    }
}
