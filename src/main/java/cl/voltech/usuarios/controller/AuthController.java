package cl.voltech.usuarios.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.voltech.usuarios.dto.GoogleAuthResponse;
import cl.voltech.usuarios.dto.GoogleCredentialRequest;
import cl.voltech.usuarios.service.CorreoPermitidoService;
import cl.voltech.usuarios.service.GoogleAuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final CorreoPermitidoService correoPermitidoService;
    private final GoogleAuthService googleAuthService;

    public AuthController(CorreoPermitidoService correoPermitidoService, GoogleAuthService googleAuthService) {
        this.correoPermitidoService = correoPermitidoService;
        this.googleAuthService = googleAuthService;
    }

    @GetMapping("/verificar-correo")
    public ResponseEntity<Map<String, Boolean>> verificarCorreo(@RequestParam String email) {
        return ResponseEntity.ok(Map.of("permitido", correoPermitidoService.esPermitido(email)));
    }

    @PostMapping("/google")
    public ResponseEntity<GoogleAuthResponse> autenticarGoogle(@Valid @RequestBody GoogleCredentialRequest request) {
        return ResponseEntity.ok(googleAuthService.autenticar(request.credential()));
    }
}
