package es.ucm.fdi.iw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import es.ucm.fdi.iw.Clases.IWuser;
import java.util.List;
import java.util.Optional;

// JpaRepository<Entidad, Tipo de ID>
public interface userRepository extends JpaRepository<IWuser, Long> {
    Optional<IWuser> findByUsername(String username);
}