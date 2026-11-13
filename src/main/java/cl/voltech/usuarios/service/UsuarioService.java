package cl.voltech.usuarios.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import cl.voltech.usuarios.dto.UsuarioCreateRequest;
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

    public Usuario crearUsuario(UsuarioCreateRequest request) {
        Usuario nuevoUsuario = usuarioMapper.toModel(request);

        return usuarioRepo.save(nuevoUsuario);
    }

    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepo.findAll();
    }

    public Usuario buscarUsuarioId(Integer id) {
        return usuarioRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    public Usuario actualizarUsuario(Integer id, Usuario datosUsuario) {
        buscarUsuarioId(id);
        datosUsuario.setId(id);
        return usuarioRepo.save(datosUsuario);
    }

    public void eliminarUsuario(Integer id) {
        Usuario usuario = buscarUsuarioId(id);
        usuarioRepo.delete(usuario);
    }
}
