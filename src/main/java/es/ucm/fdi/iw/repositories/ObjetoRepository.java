package es.ucm.fdi.iw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.ucm.fdi.iw.model.ObjetoUsos;
import es.ucm.fdi.iw.model.Objeto;
import es.ucm.fdi.iw.model.User;
import java.util.List;



@Repository
public interface ObjetoRepository extends JpaRepository<ObjetoUsos, Long> {
    ObjetoUsos findByUserAndObjeto(User user,Objeto objeto);
    List<ObjetoUsos> findByUser(User user);
}