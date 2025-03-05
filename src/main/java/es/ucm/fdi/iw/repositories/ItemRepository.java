package es.ucm.fdi.iw.repositories;

import es.ucm.fdi.iw.Clases.Objeto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;



@Repository
public interface ItemRepository extends JpaRepository<Objeto, Long> {
   
}
