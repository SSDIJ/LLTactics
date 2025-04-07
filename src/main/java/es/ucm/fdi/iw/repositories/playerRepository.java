package es.ucm.fdi.iw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import es.ucm.fdi.iw.model.Jugador;

import java.util.List;

// JpaRepository<Entidad, Tipo de ID>
public interface PlayerRepository extends JpaRepository<Jugador, Long> {

    // Encuentra jugadores por nombre (ignorando mayúsculas y minúsculas)
    List<Jugador> findByNombreContainingIgnoreCase(String nombre);


    // Encuentra todos los jugadores ordenados por partidas ganadas
    //List<Jugador> findAllByOrderByPartidasGanadasDesc();
}
