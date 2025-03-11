package es.ucm.fdi.iw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import es.ucm.fdi.iw.model.User;

import java.util.Optional;

// JpaRepository<Entidad, Tipo de ID>
public interface userRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}