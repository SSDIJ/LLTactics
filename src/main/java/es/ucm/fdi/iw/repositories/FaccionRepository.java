package es.ucm.fdi.iw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.ucm.fdi.iw.model.FaccionUsos;
import es.ucm.fdi.iw.model.User;
import java.util.List;



@Repository
public interface FaccionRepository extends JpaRepository<FaccionUsos, Long> {
    FaccionUsos findByUserAndFaccion(User user,int faccion);
    List<FaccionUsos> findByUser(User user);
}