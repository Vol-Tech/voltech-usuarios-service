package cl.voltech.usuarios.service;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import cl.voltech.usuarios.dto.UsuarioCreateRequest;
import cl.voltech.usuarios.dto.UsuarioResponse;
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
        correoPermitidoService.exigirPermitido(request.email());
        Usuario nuevoUsuario = usuarioMapper.toModel(request);
        nuevoUsuario.setComuna(buscarComuna(request.comunaId()));

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

    public UsuarioResponse actualizarUsuario(Integer id, UsuarioCreateRequest request) {
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

    public void eliminarUsuario(Integer id) {
        Usuario usuario = buscarUsuarioId(id);
        usuarioRepo.delete(usuario);
    }

    private Comuna buscarComuna(Integer id) {
        return comunaRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "La comuna indicada no existe"));
    }
}
