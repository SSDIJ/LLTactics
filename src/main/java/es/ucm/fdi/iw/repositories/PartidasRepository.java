package es.ucm.fdi.iw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import es.ucm.fdi.iw.model.Partida;
import es.ucm.fdi.iw.model.User;

import java.util.Optional;
import java.util.List;


@Repository
public interface PartidasRepository extends JpaRepository<Partida, Long> {
    Optional<Partida> findByIdPartida(Long id);
    Optional<Partida> findByGameRoomId(String gameRoomId);
    List<Partida> findByJugador1(User jugador1);
    List<Partida> findByJugador2(User jugador2);
}
