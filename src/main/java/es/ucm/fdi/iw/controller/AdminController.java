package es.ucm.fdi.iw.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.ucm.fdi.iw.Clases.Heroe;
import es.ucm.fdi.iw.Clases.Jugador;
import es.ucm.fdi.iw.Clases.Mensaje;
import es.ucm.fdi.iw.Clases.Partida;
import es.ucm.fdi.iw.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 *  Site administration.
 *
 *  Access to this end-point is authenticated - see SecurityConfig
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    @ModelAttribute
    public void populateModel(HttpSession session, Model model) {        
        for (String name : new String[] {"u", "url", "ws"}) {
            model.addAttribute(name, session.getAttribute(name));
        }
    }

    private static final Logger log = LogManager.getLogger(AdminController.class);

@GetMapping("/")
    public String index(Model model) {
        List<Jugador> baneados = new ArrayList<>();
        Jugador j1 = new Jugador("Arthur", "/img/players/arthur.png", 1, 1500, 20, 5, 0, List.of(
                new Heroe("Caballero", "/img/units/humans/3. Caballero/knight.png", 180, 60, 85, 50,
                        "Ágil y feroz en combate.", 0),
                new Heroe("Mago", "/img/units/humans/5. Mago/white-mage.png", 140, 20, 110, 60,
                        "Un maestro de la magia arcana.", 0)), true, "Ser demasiado bueno");

        Jugador j2 = new Jugador("Lancelot", "/img/players/lancelot.png", 2, 1400, 18, 7, 0, List.of(
                new Heroe("Dragón Morado", "/img/units/dragons/5. DMorado/blademaster.png", 180, 30, 95, 65,
                        "Especializado en la magia y las runas.", 1),
                new Heroe("Berserker", "/img/units/dragons/2. DBerserker/clasher-blade.png", 250, 50, 90, 45,
                        "El Berserker es una furia desatada en el campo de batalla.", 1)),true, "Se fue AFK 20 minutos");

        Jugador j3 = new Jugador("Morgana", "/img/players/morgana.png", 3, 1350, 17, 8, 1, List.of(
                new Heroe("Esqueleto mago", "/img/units/skeletons/5. SMago/ancient-lich.png", 160, 30, 110, 70,
                        "Utiliza poderosos hechizos para devastar a sus enemigos.", 3)), true, "Insultó a otro jugador");

        baneados.add(j1);
        baneados.add(j3);
        baneados.add(j2);
        model.addAttribute("jugadoresBaneados", baneados);

        List<Partida> enCurso = new ArrayList<>();
        List<Mensaje> chat = new ArrayList<>();
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(j1);
        jugadores.add(j2);

        // Agregar mensajes al chat
        chat.add(new Mensaje(j1, "Hola, ¿qué tal?"));
        chat.add(new Mensaje(j2, "¡Hola Juan! Estoy bien, ¿y tú?"));
        chat.add(new Mensaje(j1, "Todo bien, gracias por preguntar."));
        enCurso.add(new Partida("1", 30, jugadores, chat));
        model.addAttribute("enCurso", enCurso);

        List<Partida> terminadas = new ArrayList<>();

        model.addAttribute("terminadas", terminadas);

        return "admin";
    }
}
