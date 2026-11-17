package cl.voltech.usuarios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UsuarioExceptionHandler {

    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ProblemDetail usuarioNoEncontrado(UsuarioNoEncontradoException exception) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setDetail(exception.getMessage());
        return problem;
    }
}
