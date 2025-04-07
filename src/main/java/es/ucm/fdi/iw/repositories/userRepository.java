package es.ucm.fdi.iw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.ucm.fdi.iw.model.User;

import java.util.List;
import java.util.Optional;

//El repositorio se conecta con la tabla de User porque es su primer argumento.
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User>  findById(long id);

    Optional<User> findByUsername(String username);
    
    User findByUsernameContainingIgnoreCase(String username);

    List<User> findByRoles(String roles);

    List<User> findByRolesAndBaneado(String role, Boolean baneado);

    List<User> findByBaneado(Boolean baneado);



    // Encuentra todos los jugadores ordenados por partidas ganadas
    //List<User> findAllByOrderByPartidasGanadasDesc();

}