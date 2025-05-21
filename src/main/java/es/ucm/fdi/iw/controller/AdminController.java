package es.ucm.fdi.iw.controller;

import java.io.ObjectInputFilter.Config;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.ucm.fdi.iw.model.ConfigPartida;
import es.ucm.fdi.iw.model.Heroe;
import es.ucm.fdi.iw.model.Message;
import es.ucm.fdi.iw.model.Objeto;
import es.ucm.fdi.iw.model.Partida;
import es.ucm.fdi.iw.model.User;
import es.ucm.fdi.iw.repositories.ConfigPartidaRepository;
import es.ucm.fdi.iw.repositories.HeroeRepository;
import es.ucm.fdi.iw.repositories.ItemRepository;
import es.ucm.fdi.iw.repositories.PartidasRepository;
import es.ucm.fdi.iw.repositories.UserRepository;
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
    private PartidasRepository partidaRepository;

    @Autowired
    private ConfigPartidaRepository configPartidaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

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
        ConfigPartida config = configPartidaRepository.findAll().stream().findFirst().orElse(new ConfigPartida());
        model.addAttribute("configPartida", config);
        return "gestPartidas";
    }

    @PostMapping("/gestPartidas/updateValues")
    @Transactional
    public String updateValuesPartida(
        @RequestParam("estrellasIni") int estrellasIni,
        @RequestParam("vidaIni") int vidaIni,
        @RequestParam("danyoVictoria") int danyoVictoria,
        @RequestParam("estrellasRonda") int estrellasRonda,
        @RequestParam("puntosPartida") int puntosPartida,
        @RequestParam("precioRefrescar") int precioRefrescar,
        Model model) {

            
        ConfigPartida config = configPartidaRepository.findAll().stream().findFirst().orElse(new ConfigPartida());
        config.setEstrellasIni(estrellasIni);
        config.setVidaIni(vidaIni);
        config.setDanyoVictoria(danyoVictoria);
        config.setEstrellasRonda(estrellasRonda);
        config.setPrecioRefrescar(precioRefrescar);
        config.setPuntosPorPartida(puntosPartida);
        configPartidaRepository.save(config);

        return "redirect:/admin/gestPartidas";
    }

    // @GetMapping("/gestHeroes")
    // public String showHeroes(Model model) throws JsonProcessingException {
    // Map<String, List<Heroe>> heroesPorFaccion =
    // heroesService.obtenerHeroesPorFaccion();
    // model.addAttribute("heroesPorFaccion", heroesPorFaccion);
    // return "gestHeroes";
    // }

    @GetMapping("/gestGaleria")
    public String mostrarGalería(@RequestParam(required = false) String faccion, Model model) {
        if (faccion != null) {
            List<Heroe> heroes = heroesService.obtenerHeroesDeFaccion(faccion);
            model.addAttribute("heroes", heroes);
        }
        List<Objeto> objetos = itemRepository.findAll();
        model.addAttribute("objetos", objetos);
        return "gestGaleria";
    }

    @GetMapping("/cardObjeto/{index}")
    public String getObjetoFragment(@PathVariable int index, Model model) {
    List<Objeto> objetos = itemRepository.findAll();
    if (index >= 0 && index < objetos.size()) {
        model.addAttribute("objeto", objetos.get(index));
    }
    return "fragments/cardAdminObj :: cartaAdminObj";
}

    @GetMapping("/gestUsuarios")
    public String showUsuarios(Model model) {
        List<User> reportados = userRepository.findByEstado(User.Estado.REPORTADO);
        List<User> usuarios = userRepository.findAll();
        model.addAttribute("reportados", reportados);

        model.addAttribute("usuarios", usuarios);
        return "gestUsuarios";
    }

    @GetMapping("/gestGaleria/{faccion}")
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

    @PostMapping("/gestGaleria/add")
    @Transactional
    public ResponseEntity<String> addHeroe(@RequestBody Heroe heroe) {
        try {
            heroeRepository.save(heroe);
            return ResponseEntity.ok("Héroe añadido correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al añadir el héroe: " + e.getMessage());
        }

    }

    @PostMapping("/gestGaleria/addObjeto")
    @Transactional
    public String addObjeto(@RequestParam String nombreObjeto,
        @RequestParam(required = true) Integer vidaObjeto,
        @RequestParam(required = true) Integer velocidadObjeto,
        @RequestParam(required = true) Integer dañoObjeto,
        @RequestParam(required = true) Integer precioObjeto,
        @RequestParam(required = true) Integer armaduraObjeto,
        @RequestParam String imagenObjeto,
        @RequestParam String descripcionObjeto, Model model){
        
        Objeto objeto =new Objeto(imagenObjeto, nombreObjeto, vidaObjeto, armaduraObjeto, dañoObjeto, velocidadObjeto, descripcionObjeto, precioObjeto);
        itemRepository.save(objeto);

        return "redirect:/admin/gestGaleria";
    }

    @PostMapping("/gestGaleria/delete/{idHeroe}")
    @Transactional
    public String deleteHeroe(@PathVariable("idHeroe") Long idHeroe, Model model) {
        try {
            heroeRepository.deleteById(idHeroe);
            return "gestGaleria";
        } catch (Exception e) {
            model.addAttribute("error", "Error al eliminar el héroe: " + e.getMessage());
            return "redirect:/admin/gestGaleria";
        }
    }

    @PostMapping("/gestGaleria/deleteObjeto/{idObjeto}")
    @Transactional
    public String deleteObjeto(@PathVariable Long idObjeto, Model model) {
        try{
            itemRepository.deleteById(idObjeto);
            return "redirect:/admin/gestGaleria";
        }catch(Exception e){
            model.addAttribute("error", "Error al eliminar el objeto: " + e.getMessage());
            return "redirect:/admin/gestGaleria";
        }
    }



    @PostMapping("/gestGaleria/update/{idHeroe}")
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

        return "redirect:/admin/gestGaleria";
    }

    @PostMapping("/gestGaleria/updateObjeto/{idObjeto}")
    @Transactional
    public String updateObjeto(
            @PathVariable("idObjeto") Long idObjeto,
            @RequestParam("nombre") String nombre,
            @RequestParam("imagen") String imagen,
            @RequestParam("vida") int vida,
            @RequestParam("armadura") int armadura,
            @RequestParam("daño") int daño,
            @RequestParam("velocidad") int velocidad,
            @RequestParam("precio") int precio,
            Model model) {

        Objeto objeto = itemRepository.findById(idObjeto)
                .orElseThrow(() -> new IllegalArgumentException("Héroe no encontrado"));

        // Si se encuentra, actualizamos cada campo con los datos recibidos
        objeto.setNombre(nombre);
        objeto.setImagen(imagen);
        objeto.setVida(vida);
        objeto.setArmadura(armadura);
        objeto.setDaño(daño);
        objeto.setVelocidad(velocidad);
        objeto.setPrecio(precio);

        itemRepository.save(objeto);

        return "redirect:/admin/gestGaleria";
    }

    @PostMapping("/banearUsuario/{idUsuario}")
    @Transactional
    public String banearUsuario(@PathVariable("idUsuario") Long idUsuario, Model model) {
        User usuario = userRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        usuario.setFechaBaneo(LocalDateTime.now());
        usuario.setEstado(User.Estado.BANEADO);
        userRepository.save(usuario);

        return "redirect:/admin/gestUsuarios";
    }

    @PostMapping("/desbanearUsuario/{idUsuario}")
    @Transactional
    public String desbanearUsuario(@PathVariable("idUsuario") Long idUsuario, Model model) {
        User usuario = userRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        usuario.setEstado(User.Estado.NORMAL);
        usuario.setFechaBaneo(null);
        usuario.setRazonBaneo(null);
        userRepository.save(usuario);

        return "redirect:/admin/gestUsuarios";
    }


    @GetMapping("/filtrarUsuarios")
    public String filtrarUsuarios(@RequestParam(required = false) String role, @RequestParam(required = false) Boolean baneado, Model model) {
        List<User> usuarios;

        if ("ADMIN".equals(role))   // Mostramos solo admins (ignoramos el checkbox baneado)
            usuarios = userRepository.findByRolesContaining("ADMIN");

        else if ("USER".equals(role)) {
            if (baneado != null) // Filtrar usuarios que sean no admin (o que no tengan el rol ADMIN) y con elestado baneado indicado
                usuarios = userRepository.findByEstadoAndRolesNotContaining(User.Estado.BANEADO, "ADMIN");
            else // Mostrar todos los usuarios que no sean admin
                usuarios = userRepository.findByRolesNotContaining("ADMIN");
            
        } else // Si no se selecciona nada, mostramos todos
            usuarios = userRepository.findAll();

        boolean mostrarBaneo = (baneado != null);

        model.addAttribute("mostrarBaneo", mostrarBaneo);
        model.addAttribute("usuarios", usuarios);
        return "gestUsuarios";
    }
}
