package es.ucm.fdi.iw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.ucm.fdi.iw.model.Heroe;
import es.ucm.fdi.iw.model.Jugador;
import es.ucm.fdi.iw.repositories.playerRepository;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RankingController {

    @Autowired
    private playerRepository jugadorRepository;
    
    @GetMapping("/ranking")
    public String mostrarRanking(Model model) {
      
        System.out.println("Mondongo");
        // Obtener jugadores de la base de datos
        List<Jugador> jugadores = jugadorRepository.findAllByOrderByPartidasGanadasDesc();
        System.out.println("Se han podido pillar a los jugadores");
        if (jugadores == null || jugadores.isEmpty()) {
            model.addAttribute("jugadores", List.of()); // Evitar null en la vista
            return "ranking";
        }

   System.out.println("He enviado los jugadores\n");
        model.addAttribute("jugadores", jugadores);
        return "ranking"; // Retorna la vista "ranking.html"
    }
}
