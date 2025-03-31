package es.ucm.fdi.iw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import es.ucm.fdi.iw.model.Partida;
import es.ucm.fdi.iw.repositories.userRepository;
import jakarta.persistence.EntityManager;
import es.ucm.fdi.iw.repositories.partidasRepository;
import jakarta.transaction.Transactional;
import es.ucm.fdi.iw.model.User;

@Controller
public class PartidasController {

    @Autowired
	private EntityManager entityManager;

    @Autowired
    private partidasRepository partidasRepository;

    @PostMapping("/creaPartida")
    @Transactional
    public String creaPartida(
            @RequestParam long idJugador1,
            @RequestParam long idJugador2,
            @RequestParam int duracionMin,
            Model model) {

        // Obtén los jugadores desde la base de datos
        User jugador1 = entityManager.find(User.class, idJugador1);
        User jugador2 = entityManager.find(User.class, idJugador2);

        // Verifica que ambos jugadores existan
        if (jugador1 == null || jugador2 == null) {
            model.addAttribute("error", "Uno o ambos jugadores no existen.");
            return "error"; // Redirige a una página de error
        }

        // Crea una nueva partida
        Partida nuevaPartida = new Partida(jugador1, jugador2, duracionMin);

        // Persiste la partida en la base de datos
        entityManager.persist(nuevaPartida);

        // Agrega un mensaje de éxito al modelo
        model.addAttribute("success", "Partida creada exitosamente.");

        // Redirige a la página principal
        return "redirect:/";
    }
    
}
