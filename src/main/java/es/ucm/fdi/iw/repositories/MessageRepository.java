package es.ucm.fdi.iw.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.ucm.fdi.iw.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
   @Override
   default Optional<Message> findById(Long id) {
       // TODO Auto-generated method stub
       throw new UnsupportedOperationException("Unimplemented method 'findById'");
   }
   
}

