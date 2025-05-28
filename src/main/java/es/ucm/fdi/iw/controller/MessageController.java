package es.ucm.fdi.iw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.annotation.PostConstruct;
import es.ucm.fdi.iw.model.Message;
import es.ucm.fdi.iw.repositories.UserRepository;
import es.ucm.fdi.iw.repositories.MessageRepository;

import java.util.List;

/* Recibe petición HTTP para mostrar la galería y devuelve la vista "galeria" con toda la información de los héroes agrupados por facción */
@Controller
public class MessageController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;

    @PostConstruct
    public void init() {
        List<Message> todosLosMensajes = messageRepository.findAll(); // Carga todos los héroes al iniciar la aplicación
        System.out.println("Mensajes cargados al iniciar la aplicación: " + todosLosMensajes.size());
    }

}
