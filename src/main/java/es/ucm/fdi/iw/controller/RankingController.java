package es.ucm.fdi.iw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import es.ucm.fdi.iw.Clases.Jugador;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RankingController {
    
    @GetMapping("/ranking")
    public String mostrarRanking(Model model) {
        List<Jugador> jugadores = new ArrayList<>();

    //Jugadores añadidos por el momento (cambiar a BD cuando lo explique el profe)
        jugadores.add(new Jugador("Arthur", "/img/players/arthur.png", 1, 1500, 20, 5, 0));
        jugadores.add(new Jugador("Lancelot", "/img/players/lancelot.png", 2, 1400, 18, 7, 0));
        jugadores.add(new Jugador("Morgana", "/img/players/morgana.png", 3, 1350, 17, 8, 1));
        jugadores.add(new Jugador("Merlin", "/img/players/merlin.png", 4, 1300, 15, 10, 1));
        jugadores.add(new Jugador("Galahad", "/img/players/galahad.png", 5, 1250, 14, 11, 2));

      //La lista se envia en funcion a las partidas ganadas con las perdidas
      jugadores.sort((j1, j2) -> {
        int diferenciaJ1 = j1.getPartidasGanadas() - j1.getPartidasPerdidas();
        int diferenciaJ2 = j2.getPartidasGanadas() - j2.getPartidasPerdidas();
        return Integer.compare(diferenciaJ2, diferenciaJ1); // Orden descendente
    });
    //Se envia la lista
        model.addAttribute("jugadores", jugadores);

        return "ranking";
    }
}
