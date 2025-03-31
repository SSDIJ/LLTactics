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
import es.ucm.fdi.iw.model.Message;
import es.ucm.fdi.iw.model.Partida;
import es.ucm.fdi.iw.model.User;
import es.ucm.fdi.iw.repositories.HeroeRepository;
import es.ucm.fdi.iw.repositories.partidasRepository;
import es.ucm.fdi.iw.repositories.userRepository;
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
    private partidasRepository partidaRepository;

    @Autowired
    private userRepository userRepository;

    @Autowired
    private HeroesService heroesService; // Inyectamos el servicio de héroes

    @ModelAttribute
    public void populateModel(HttpSession session, Model model) {
        for (String name : new String[] { "u", "url", "ws" })
            model.addAttribute(name, session.getAttribute(name));
    }

    private static final Logger log = LogManager.getLogger(AdminController.class);

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/gestPartidas")
    public String showPartidas(Model model) {
        List<Partida> partidas = partidaRepository.findAll();
        model.addAttribute("partidas", partidas);
        return "gestPartidas";
    }

    // @GetMapping("/gestHeroes")
    // public String showHeroes(Model model) throws JsonProcessingException {
    // Map<String, List<Heroe>> heroesPorFaccion =
    // heroesService.obtenerHeroesPorFaccion();
    // model.addAttribute("heroesPorFaccion", heroesPorFaccion);
    // return "gestHeroes";
    // }

    @GetMapping("/gestHeroes")
    public String mostrarHeroes(@RequestParam(required = false) String faccion, Model model) {
        if (faccion != null) {
            List<Heroe> heroes = heroesService.obtenerHeroesDeFaccion(faccion);
            model.addAttribute("heroes", heroes);
        }
        return "gestHeroes";
    }

    @GetMapping("/gestUsuarios")
    public String showUsuarios(Model model) {
        List<User> usuarios = userRepository.findAll();
        model.addAttribute("usuarios", usuarios);
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

    @PostMapping("/gestHeroes/delete/{idHeroe}")
    @Transactional
    public String deleteHeroe(@PathVariable("idHeroe") Long idHeroe, Model model) {
        try {
            heroeRepository.deleteById(idHeroe);
            return "gestHeroes";
        } catch (Exception e) {
            model.addAttribute("error", "Error al eliminar el héroe: " + e.getMessage());
            return "gestHeroes";
        }
    }

    @PostMapping("/gestHeroes/update/{idHeroe}")
    @Transactional
    public String updateHeroe(
            @PathVariable("idHeroe") Long idHeroe,
            @RequestParam("nombre") String nombre,
            @RequestParam("imagen") String imagen,
            @RequestParam("vida") int vida,
            @RequestParam("armadura") int armadura,
            @RequestParam("daño") int daño,
            @RequestParam("velocidad") int velocidad,
            @RequestParam("probabilidad") double probabilidad,
            @RequestParam("precio") int precio,
            Model model) {

        Heroe heroe = heroeRepository.findById(idHeroe)
                .orElseThrow(() -> new IllegalArgumentException("Héroe no encontrado"));

        // Si se encuentra, actualizamos cada campo con los datos recibidos
        heroe.setNombre(nombre);
        heroe.setImagen(imagen);
        heroe.setVida(vida);
        heroe.setArmadura(armadura);
        heroe.setDaño(daño);
        heroe.setVelocidad(velocidad);
        heroe.setProbabilidad(probabilidad);
        heroe.setPrecio(precio);

        heroeRepository.save(heroe);

        return "gestHeroes";
    }

}
