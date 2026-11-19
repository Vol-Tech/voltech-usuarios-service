package cl.voltech.usuarios.repository;

import cl.voltech.usuarios.model.Comuna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComunaRepository extends JpaRepository<Comuna, Integer> {
}
