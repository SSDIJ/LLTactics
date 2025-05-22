package es.ucm.fdi.iw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.ucm.fdi.iw.model.HeroeUsos;
import es.ucm.fdi.iw.model.User;
import es.ucm.fdi.iw.model.Heroe;

import java.util.List;

@Repository
public interface HeroeUsosRepository extends JpaRepository<HeroeUsos, Long> {
    HeroeUsos findByUserAndHeroe(User user, Heroe heroe);
    List<HeroeUsos> findByUser(User user);

}