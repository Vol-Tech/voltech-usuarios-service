package cl.voltech.usuarios.exception;

public class UsuarioNoEncontradoException extends RuntimeException {

    public UsuarioNoEncontradoException(Integer id) {
        super("Usuario no encontrado: " + id);
    }
}
