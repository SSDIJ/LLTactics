package es.ucm.fdi.iw.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import es.ucm.fdi.iw.model.Heroe;
import es.ucm.fdi.iw.model.Jugador;
import es.ucm.fdi.iw.model.Mensaje;
import es.ucm.fdi.iw.model.Partida;
import es.ucm.fdi.iw.model.User;
import es.ucm.fdi.iw.repositories.HeroeRepository;
import es.ucm.fdi.iw.services.HeroesService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

/**
 * Site administration.
 *
 * Access to this end-point is authenticated - see SecurityConfig
 */
@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private HeroeRepository heroeRepository;

    @Autowired
    private HeroesService heroesService; // Inyectamos el servicio de héroes

    @ModelAttribute
    public void populateModel(HttpSession session, Model model) {
        for (String name : new String[] { "u", "url", "ws" }) {
            model.addAttribute(name, session.getAttribute(name));
        }
        cargarDatos(model);
    }

    private void cargarDatos(Model model) {
        List<Jugador> baneados = new ArrayList<>();
        Jugador j1 = new Jugador("Arthur", "/img/players/arthur.png", 1, 1500, 20, 5, 0, List.of(
                new Heroe("Caballero", "/img/units/humans/3. Caballero/knight.png", 180, 60, 85, 50,
                        "Ágil y feroz en combate.", 0, 2, 0),
                new Heroe("Mago", "/img/units/humans/5. Mago/white-mage.png", 140, 20, 110, 60,
                        "Un maestro de la magia arcana.", 0, 2, 0)),
                true, "Ser demasiado bueno");

        Jugador j2 = new Jugador("Lancelot", "/img/players/lancelot.png", 2, 1400, 18, 7, 0, List.of(
                new Heroe("Dragón Morado", "/img/units/dragons/5. DMorado/blademaster.png", 180, 30, 95, 65,
                        "Especializado en la magia y las runas.", 1, 2, 0),
                new Heroe("Berserker", "/img/units/dragons/2. DBerserker/clasher-blade.png", 250, 50, 90, 45,
                        "El Berserker es una furia desatada en el campo de batalla.", 1, 3, 0)),
                true, "Se fue AFK 20 minutos");

        Jugador j3 = new Jugador("Morgana", "/img/players/morgana.png", 3, 1350, 17, 8, 1, List.of(
                new Heroe("Esqueleto mago", "/img/units/skeletons/5. SMago/ancient-lich.png", 160, 30, 110, 70,
                        "Utiliza poderosos hechizos para devastar a sus enemigos.", 3, 4, 0)),
                true, "Insultó a otro jugador");

        baneados.add(j1);
        baneados.add(j3);
        baneados.add(j2);
        model.addAttribute("jugadoresBaneados", baneados);

        List<Partida> enCurso = new ArrayList<>();
        List<Mensaje> chat = new ArrayList<>();
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(j1);
        jugadores.add(j2);

        Long id = (long) 1.0; // TODO: QUITAR, PUESTO SOLO PARA QUE FUNCIONE
        // Agregar mensajes al chat
        chat.add(new Mensaje(j1, "Hola, ¿qué tal?"));
        chat.add(new Mensaje(j2, "¡Hola Juan! Estoy bien, ¿y tú?"));
        chat.add(new Mensaje(j1, "Todo bien, gracias por preguntar."));
        // enCurso.add(new Partida(id, 30, jugadores, chat));
        model.addAttribute("enCurso", enCurso);

        List<Partida> terminadas = new ArrayList<>();

        model.addAttribute("terminadas", terminadas);

        List<Heroe> humanos = heroeRepository.findByFaccion(0);
        List<Heroe> dragones = heroeRepository.findByFaccion(1);
        List<Heroe> trolls = heroeRepository.findByFaccion(2);
        List<Heroe> noMuertos = heroeRepository.findByFaccion(3);
        List<Heroe> legendarios = heroeRepository.findByFaccion(4);

        System.out.println("Humanos: " + humanos); // Esto lo puedes revisar en el terminal

        model.addAttribute("humanos", humanos);
        model.addAttribute("trolls", trolls);
        model.addAttribute("dragones", dragones);
        model.addAttribute("noMuertos", noMuertos);
        model.addAttribute("legendarios", legendarios);
    }

    private static final Logger log = LogManager.getLogger(AdminController.class);

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/gestPartidas")
    public String showPartidas(Model model) {
        return "gestPartidas";
    }

    @GetMapping("/gestHeroes")
    public String showHeroes(Model model) throws JsonProcessingException {
        Map<String, List<Heroe>> heroesPorFaccion = heroesService.obtenerHeroesPorFaccion();
        model.addAttribute("heroesPorFaccion", heroesPorFaccion);
        return "gestHeroes";
    }

    @GetMapping("/gestUsuarios")
    public String showUsuarios(Model model) {
        return "gestUsuarios";
    }

    @GetMapping("/gestHeroes/{faccion}")
    public ResponseEntity<List<Heroe>> obtenerHeroesPorFaccion(@PathVariable String faccion) {
        List<Heroe> heroes = null;
        switch (faccion) {
            case "humanos":
                heroes = heroeRepository.findByFaccion(0);
                break;
            case "dragones":
                heroes = heroeRepository.findByFaccion(1);
                break;
            case "trolls":
                heroes = heroeRepository.findByFaccion(2);
                break;
            case "noMuertos":
                heroes = heroeRepository.findByFaccion(3);
                break;
            case "legendarios":
                heroes = heroeRepository.findByFaccion(4);
                break;

            default:
                break;
        }
        return ResponseEntity.ok(heroes);
    }

    @PostMapping("/gestHeroes/add")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> addHeroe(@RequestBody Heroe heroe) {
        try {
            heroeRepository.save(heroe);
            return ResponseEntity.ok("Héroe añadido correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al añadir el héroe: " + e.getMessage());
        }
    }

    @DeleteMapping("/gestHeroes/delete/{id}")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> deleteHeroe(@PathVariable Long id) {
        try {
            heroeRepository.deleteById(id);
            return ResponseEntity.ok("Héroe eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar el héroe: " + e.getMessage());
        }
    }
}
