package es.ucm.fdi.iw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.ucm.fdi.iw.model.Objeto;

import java.util.List;



@Repository
public interface ItemRepository extends JpaRepository<Objeto, Long> {
   
}
