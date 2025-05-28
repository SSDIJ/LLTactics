package es.ucm.fdi.iw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import es.ucm.fdi.iw.model.Partida;
import es.ucm.fdi.iw.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
import es.ucm.fdi.iw.repositories.PartidasRepository;
import jakarta.transaction.Transactional;
import es.ucm.fdi.iw.model.User;

@Controller
public class PartidasController {

    @Autowired
	private EntityManager entityManager;

    @Autowired
    private PartidasRepository partidasRepository;

    @ModelAttribute
    public void populateModel(HttpSession session, Model model) {
        for (String name : new String[] { "u", "url", "ws" })
            model.addAttribute(name, session.getAttribute(name));
    }

    @PostMapping("/guardaPartida")
    @Transactional
    public String guardaPartida(
            @RequestParam long idJugador1,
            @RequestParam long idJugador2,
            @RequestParam String estado,
            @RequestParam String gameRoomId,
            @RequestParam boolean enCurso,
            @RequestParam int ganador,
            @RequestParam int perdedor,
            Model model) {

        // Obtén los jugadores desde la base de datos
        User jugador1 = entityManager.find(User.class, idJugador1);
        User jugador2 = entityManager.find(User.class, idJugador2);

        // Verifica que ambos jugadores existan
        if (jugador1 == null || jugador2 == null) {
            model.addAttribute("error", "Uno o ambos jugadores no existen.");
            return "error"; // Redirige a una página de error
        }

        // Busca la partida en la base de datos por gameRoomId
        Partida partida = entityManager.createQuery(
                "SELECT p FROM Partida p WHERE p.gameRoomId = :gameRoomId", Partida.class)
                .setParameter("gameRoomId", gameRoomId)
                .getResultStream()
                .findFirst()
                .orElse(null);

        if (partida != null) {
            // Si la partida existe, actualiza sus datos
            partida.setEstado(estado);
            partida.setEnCurso(enCurso);
            partida.setGanador(ganador);
            partida.setPerdedor(perdedor);
            entityManager.merge(partida); // Actualiza la partida en la base de datos
            model.addAttribute("success", "Partida actualizada exitosamente.");
        } else {
            // Si la partida no existe, crea una nueva
            Partida nuevaPartida = new Partida(jugador1, jugador2, estado, gameRoomId);
            entityManager.persist(nuevaPartida); // Persiste la nueva partida en la base de datos
            model.addAttribute("success", "Nueva partida creada exitosamente.");
        }
        // Redirige a la página principal
        return "redirect:/admin/gestPartidas";
    }
    
    
}
