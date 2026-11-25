package cl.voltech.usuarios.service;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import cl.voltech.usuarios.dto.UsuarioAdminCreateRequest;
import cl.voltech.usuarios.dto.UsuarioCreateRequest;
import cl.voltech.usuarios.dto.UsuarioDireccionRequest;
import cl.voltech.usuarios.dto.UsuarioEstadisticasResponse;
import cl.voltech.usuarios.dto.UsuarioResponse;
import cl.voltech.usuarios.dto.UsuarioUpdateRequest;
import cl.voltech.usuarios.exception.UsuarioNoEncontradoException;
import cl.voltech.usuarios.mapper.UsuarioMapper;
import cl.voltech.usuarios.model.Usuario;
import cl.voltech.usuarios.repository.UsuarioRepository;
import cl.voltech.usuarios.repository.ComunaRepository;
import cl.voltech.usuarios.model.Comuna;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final ComunaRepository comunaRepo;
    private final UsuarioMapper usuarioMapper;
    private final CorreoPermitidoService correoPermitidoService;

    public UsuarioService(UsuarioRepository usuarioRepo, ComunaRepository comunaRepo, UsuarioMapper usuarioMapper,
            CorreoPermitidoService correoPermitidoService) {
        this.usuarioRepo = usuarioRepo;
        this.comunaRepo = comunaRepo;
        this.usuarioMapper = usuarioMapper;
        this.correoPermitidoService = correoPermitidoService;
    }

    public UsuarioResponse crearUsuario(UsuarioCreateRequest request) {
        return crearUsuarioConRol(request, "CLIENTE");
    }

    public UsuarioResponse crearUsuarioAdministracion(UsuarioAdminCreateRequest request) {
        return crearUsuarioConRol(request.usuario(), request.rol());
    }

    private UsuarioResponse crearUsuarioConRol(UsuarioCreateRequest request, String rol) {
        correoPermitidoService.exigirPermitido(request.email());
        Usuario nuevoUsuario = usuarioMapper.toModel(request);
        nuevoUsuario.setComuna(buscarComuna(request.comunaId()));
        nuevoUsuario.setRol(rol.toUpperCase(Locale.ROOT));

        return usuarioMapper.toResponse(usuarioRepo.save(nuevoUsuario));
    }

    public List<UsuarioResponse> listarTodosUsuarios() {
        return usuarioRepo.findAll().stream()
            .map(usuarioMapper::toResponse)
            .toList();
    }

    public Usuario buscarUsuarioId(Integer id) {
        return usuarioRepo.findById(id)
            .orElseThrow(() -> new UsuarioNoEncontradoException(id));
    }

    public UsuarioResponse obtenerUsuarioId(Integer id) {
        return usuarioMapper.toResponse(buscarUsuarioId(id));
    }

    public UsuarioEstadisticasResponse obtenerEstadisticas() {
        LocalDateTime inicioMes = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime inicioMesSiguiente = inicioMes.plusMonths(1);
        return new UsuarioEstadisticasResponse(
            usuarioRepo.count(),
            usuarioRepo.countByFechaCreacionGreaterThanEqualAndFechaCreacionLessThan(inicioMes, inicioMesSiguiente)
        );
    }

    public UsuarioResponse actualizarUsuario(Integer id, UsuarioUpdateRequest request) {
        correoPermitidoService.exigirPermitido(request.email());
        Usuario usuario = buscarUsuarioId(id);

        usuario.setNombre(request.nombre());
        usuario.setApellidos(request.apellidos());
        usuario.setEmail(request.email());
        usuario.setCalle(request.calle());
        usuario.setDepto(request.depto());
        usuario.setComuna(buscarComuna(request.comunaId()));

        return usuarioMapper.toResponse(usuarioRepo.save(usuario));
    }

    public UsuarioResponse actualizarRol(Integer id, String rol) {
        Usuario usuario = buscarUsuarioId(id);
        usuario.setRol(rol.toUpperCase(Locale.ROOT));
        return usuarioMapper.toResponse(usuarioRepo.save(usuario));
    }

    public UsuarioResponse actualizarDireccion(Integer id, UsuarioDireccionRequest request, Authentication auth) {
        Usuario usuario = buscarUsuarioId(id);
        boolean esAdmin = auth.getAuthorities().stream()
            .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMINISTRADOR"));
        if (!esAdmin && !usuario.getEmail().equalsIgnoreCase(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Solo puedes actualizar tu dirección");
        }

        usuario.setCalle(request.calle());
        usuario.setDepto(request.depto());
        usuario.setComuna(buscarComuna(request.comunaId()));
        return usuarioMapper.toResponse(usuarioRepo.save(usuario));
    }

    public void eliminarUsuario(Integer id) {
        Usuario usuario = buscarUsuarioId(id);
        usuarioRepo.delete(usuario);
    }

    private Comuna buscarComuna(Integer id) {
        return comunaRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "La comuna indicada no existe"));
    }
}
