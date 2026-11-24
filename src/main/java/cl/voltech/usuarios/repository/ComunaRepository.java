package cl.voltech.usuarios.repository;

import cl.voltech.usuarios.model.Comuna;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComunaRepository extends JpaRepository<Comuna, Integer> {

    @EntityGraph(attributePaths = "region")
    List<Comuna> findAllByOrderByNombreAsc();
}
