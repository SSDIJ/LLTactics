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
    

/* CODIGO ANTIGUO PARA MOSTRAR EL RANKING SIN BASE DE DATOS
    public String mostrarRanking(Model model) {
        
        List<Jugador> jugadores = new ArrayList<>();

        // Definimos héroes más jugados
        jugadores.add(new Jugador("Arthur", "/img/players/arthur.png", 1, 1500, 20, 5, 0, List.of(
            new Heroe("Caballero", "/img/units/humans/3. Caballero/knight.png", 180, 60, 85, 50, "Ágil y feroz en combate.", 0),
            new Heroe("Mago", "/img/units/humans/5. Mago/white-mage.png", 140, 20, 110, 60, "Un maestro de la magia arcana.", 0)
        ), false, ""));

        jugadores.add(new Jugador("Lancelot", "/img/players/lancelot.png", 2, 1400, 18, 7, 0, List.of(
            new Heroe("Dragón Morado", "/img/units/dragons/5. DMorado/blademaster.png", 180, 30, 95, 65, "Especializado en la magia y las runas.", 1),
            new Heroe("Berserker", "/img/units/dragons/2. DBerserker/clasher-blade.png", 250, 50, 90, 45, "El Berserker es una furia desatada en el campo de batalla.", 1)
        ), false, ""));

        jugadores.add(new Jugador("Morgana", "/img/players/morgana.png", 3, 1350, 17, 8, 1, List.of(
            new Heroe("Esqueleto mago", "/img/units/skeletons/5. SMago/ancient-lich.png", 160, 30, 110, 70, "Utiliza poderosos hechizos para devastar a sus enemigos.", 3)
        ), false, ""));

        jugadores.add(new Jugador("Merlin", "/img/players/merlin.png", 4, 1300, 15, 10, 1, List.of(
            new Heroe("Incinerador", "/img/units/dragons/4. DGris/burner.png", 200, 40, 110, 50, "Un dragón de fuego que abrasa todo a su paso.", 1)
        ), false, ""));

        jugadores.add(new Jugador("Galahad", "/img/players/galahad.png", 5, 1250, 14, 11, 2, List.of(
            new Heroe("General de Guerra", "/img/units/humans/2. General/general.png", 200, 80, 70, 40, "Un estratega nato en el campo de batalla.", 0)
        ), false, ""));

        // Ordenar la lista por diferencia entre partidas ganadas y perdidas (descendente)
        jugadores.sort((j1, j2) -> Integer.compare(
            j2.getPartidasGanadas() - j2.getPartidasPerdidas(),
            j1.getPartidasGanadas() - j1.getPartidasPerdidas()
        ));

        model.addAttribute("jugadores", jugadores);
        return "ranking";
    }
  */
    @GetMapping("/ranking")
    public String mostrarRanking(Model model) {
    // Obtener jugadores de la base de datos
    List<Jugador> jugadores = jugadorRepository.findAll();

    // Ordenar por diferencia entre partidas ganadas y perdidas (descendente)
    jugadores.sort((j1, j2) -> Integer.compare(
        j2.getPartidasGanadas() - j2.getPartidasPerdidas(),
        j1.getPartidasGanadas() - j1.getPartidasPerdidas()
    ));

    model.addAttribute("jugadores", jugadores);
    return "ranking"; // Retorna la vista "ranking.html"
}
}