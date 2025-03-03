package es.ucm.fdi.iw.repository;

import es.ucm.fdi.iw.Clases.Heroe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HeroeRepository extends JpaRepository<Heroe, Long> {
    List<Heroe> findByFaccion(int faccion);
}
