package es.ucm.fdi.iw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import es.ucm.fdi.iw.model.Partida;
import java.util.Optional;

@Repository
public interface PartidasRepository extends JpaRepository<Partida, Long> {
    Optional<Partida> findByIdPartida(Long id);


}