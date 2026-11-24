package cl.voltech.usuarios.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import cl.voltech.usuarios.dto.LoginRequest;
import cl.voltech.usuarios.dto.LoginResponse;
import cl.voltech.usuarios.mapper.UsuarioMapper;
import cl.voltech.usuarios.model.Usuario;
import cl.voltech.usuarios.repository.UsuarioRepository;

@Service
public class PasswordLoginService {

    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;
    private final JwtService jwtService;

    public PasswordLoginService(UsuarioRepository usuarioRepo, PasswordEncoder passwordEncoder, UsuarioMapper usuarioMapper, JwtService jwtService) {
        this.usuarioRepo = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
        this.usuarioMapper = usuarioMapper;
        this.jwtService = jwtService;
    }

    @Transactional(readOnly = true)
    public LoginResponse iniciarSesion(LoginRequest request) {
        Usuario usuario = usuarioRepo.findByEmailIgnoreCase(request.email().trim())
            .orElseThrow(this::credencialesInvalidas);

        if (!passwordEncoder.matches(
            request.contraseña(), usuario.getContraseña()
        )) {
            throw credencialesInvalidas();
        }

        String token = jwtService.crearToken(
            usuario.getEmail(), usuario.getRol()
        );

        return new LoginResponse(token, usuarioMapper.toResponse(usuario));
    }

    private ResponseStatusException credencialesInvalidas() {
        return new ResponseStatusException(
            HttpStatus.UNAUTHORIZED,
            "Correo o contraseña incorrectos"
        );
    }
}
