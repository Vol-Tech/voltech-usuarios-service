package cl.voltech.usuarios.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.server.ResponseStatusException;

import cl.voltech.usuarios.dto.UsuarioCreateRequest;
import cl.voltech.usuarios.dto.UsuarioDireccionRequest;
import cl.voltech.usuarios.mapper.UsuarioMapper;
import cl.voltech.usuarios.model.Comuna;
import cl.voltech.usuarios.model.Usuario;
import cl.voltech.usuarios.repository.ComunaRepository;
import cl.voltech.usuarios.repository.UsuarioRepository;

class UsuarioServiceTest {

    private UsuarioRepository usuarioRepo;
    private ComunaRepository comunaRepo;
    private UsuarioMapper usuarioMapper;
    private UsuarioService usuarioService;

    @BeforeEach
    void preparar() {
        usuarioRepo = mock(UsuarioRepository.class);
        comunaRepo = mock(ComunaRepository.class);
        usuarioMapper = mock(UsuarioMapper.class);
        usuarioService = new UsuarioService(usuarioRepo, comunaRepo, usuarioMapper,
            new CorreoPermitidoService());
    }

    @Test
    void registroPublicoSiempreCreaCliente() {
        UsuarioCreateRequest request = new UsuarioCreateRequest(
            "12.345.678-9", "Ana", "Pérez", "ana@gmail.com", "clave",
            "Calle 1", null, 1);
        Usuario usuario = new Usuario();
        when(usuarioMapper.toModel(request)).thenReturn(usuario);
        when(comunaRepo.findById(1)).thenReturn(Optional.of(new Comuna()));
        when(usuarioRepo.save(usuario)).thenReturn(usuario);

        usuarioService.crearUsuario(request);

        assertEquals("CLIENTE", usuario.getRol());
        verify(usuarioRepo).save(usuario);
    }

    @Test
    void clienteNoPuedeActualizarDireccionAjena() {
        Usuario usuario = new Usuario();
        usuario.setEmail("duena@gmail.com");
        when(usuarioRepo.findById(7)).thenReturn(Optional.of(usuario));
        Authentication auth = new UsernamePasswordAuthenticationToken(
            "otra@gmail.com", null, List.of(new SimpleGrantedAuthority("ROLE_CLIENTE")));

        ResponseStatusException error = assertThrows(ResponseStatusException.class,
            () -> usuarioService.actualizarDireccion(7,
                new UsuarioDireccionRequest("Nueva calle", null, 1), auth));

        assertEquals(403, error.getStatusCode().value());
        verify(usuarioRepo, never()).save(any());
    }

    @Test
    void clientePuedeActualizarSuDireccion() {
        Usuario usuario = new Usuario();
        usuario.setEmail("duena@gmail.com");
        Comuna comuna = new Comuna();
        when(usuarioRepo.findById(7)).thenReturn(Optional.of(usuario));
        when(comunaRepo.findById(1)).thenReturn(Optional.of(comuna));
        when(usuarioRepo.save(usuario)).thenReturn(usuario);
        Authentication auth = new UsernamePasswordAuthenticationToken(
            "duena@gmail.com", null, List.of(new SimpleGrantedAuthority("ROLE_CLIENTE")));

        usuarioService.actualizarDireccion(7,
            new UsuarioDireccionRequest("Nueva calle", "12B", 1), auth);

        assertEquals("Nueva calle", usuario.getCalle());
        assertEquals("12B", usuario.getDepto());
        assertEquals(comuna, usuario.getComuna());
        verify(usuarioRepo).save(usuario);
    }
}
