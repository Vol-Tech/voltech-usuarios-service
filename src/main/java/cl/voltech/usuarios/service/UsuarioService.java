package cl.voltech.usuarios.service;

import org.springframework.stereotype.Service;

import java.util.List;

import cl.voltech.usuarios.dto.UsuarioCreateRequest;
import cl.voltech.usuarios.dto.UsuarioResponse;
import cl.voltech.usuarios.exception.UsuarioNoEncontradoException;
import cl.voltech.usuarios.mapper.UsuarioMapper;
import cl.voltech.usuarios.model.Usuario;
import cl.voltech.usuarios.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepo, UsuarioMapper usuarioMapper) {
        this.usuarioRepo = usuarioRepo;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioResponse crearUsuario(UsuarioCreateRequest request) {
        Usuario nuevoUsuario = usuarioMapper.toModel(request);

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
        Usuario usuario = buscarUsuarioId(id);

        usuario.setNombre(request.nombre());
        usuario.setApellidos(request.apellidos());
        usuario.setEmail(request.email());
        usuario.setCalle(request.calle());
        usuario.setDepto(request.depto());

        return usuarioMapper.toResponse(usuarioRepo.save(usuario));
    }

    public void eliminarUsuario(Integer id) {
        Usuario usuario = buscarUsuarioId(id);
        usuarioRepo.delete(usuario);
    }
}
