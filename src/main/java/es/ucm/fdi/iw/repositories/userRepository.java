package es.ucm.fdi.iw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.ucm.fdi.iw.model.Jugador;
import es.ucm.fdi.iw.model.User;

import java.util.List;
import java.util.Optional;

//El repositorio se conecta con la tabla de User porque es su primer argumento.
@Repository
public interface userRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    
    User findByUsernameContainingIgnoreCase(String username);

    

}