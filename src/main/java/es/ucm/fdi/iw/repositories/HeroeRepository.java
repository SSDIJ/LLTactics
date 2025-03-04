package es.ucm.fdi.iw.repositories;

import es.ucm.fdi.iw.Clases.Heroe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface HeroeRepository extends JpaRepository<Heroe, Long> {
    List<Heroe> findByFaccion(int faccion);
}
