package cl.voltech.usuarios.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import cl.voltech.usuarios.model.Usuario;
import cl.voltech.usuarios.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;

    public UsuarioService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    public Usuario crearUsuario(Usuario usuario) {
        usuario.setId(null);
        return usuarioRepo.save(usuario);
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
