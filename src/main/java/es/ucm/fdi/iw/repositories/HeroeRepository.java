package es.ucm.fdi.iw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.ucm.fdi.iw.model.Heroe;

import java.util.List;
import java.util.Map;

/* Permite la conexión con la base de datos para obtener lo relevante a los heroes */

@Repository
public interface HeroeRepository extends JpaRepository<Heroe, Long> {
    List<Heroe> findByFaccion(int faccion);

    void deleteById(long id);
}
