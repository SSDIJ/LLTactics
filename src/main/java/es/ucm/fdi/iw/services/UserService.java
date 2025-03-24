package es.ucm.fdi.iw.services;

import es.ucm.fdi.iw.repositories.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.ucm.fdi.iw.model.User;
import es.ucm.fdi.iw.model.User.Role;

@Service
public class UserService {

    @Autowired
    private userRepository userRepository;

    public void saveUser(User userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword()); //  Debe encriptarse antes de guardar
        user.setEnabled(userDTO.isEnabled());
        user.setRoles(userDTO.getRoles());

        
        user.setIndiceRanking(userDTO.getIndiceRanking());  
        user.setPuntuacion(userDTO.getPuntuacion());
        user.setPartidasGanadas(userDTO.getPartidasGanadas());
        user.setPartidasPerdidas(userDTO.getPartidasPerdidas());
        user.setFaccionFavorita(userDTO.getFaccionFavorita());
        
        
        userRepository.save(user);
    }
}