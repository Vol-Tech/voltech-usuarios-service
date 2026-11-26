package cl.voltech.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.voltech.usuarios.model.Usuario;
import java.util.Optional;
import java.time.LocalDateTime;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmailIgnoreCase(String email);

    long countByFechaCreacionGreaterThanEqualAndFechaCreacionLessThan(
        LocalDateTime inicio, LocalDateTime fin);

    default int totalUsuarios() {
        return (int) this.count();
    }
}
