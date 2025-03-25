package es.ucm.fdi.iw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.ucm.fdi.iw.model.Jugador;
import es.ucm.fdi.iw.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface userRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    // Encuentra jugadores por nombre (ignorando mayúsculas y minúsculas)
    User findByUsernameContainingIgnoreCase(String username);

    // Encuentra todos los jugadores ordenados por partidas ganadas
    //List<User> findAllByOrderByPartidasGanadasDesc();

}