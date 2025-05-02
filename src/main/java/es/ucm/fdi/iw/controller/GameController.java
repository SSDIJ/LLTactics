package es.ucm.fdi.iw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.ucm.fdi.iw.model.Heroe;
import es.ucm.fdi.iw.model.Jugador;
import es.ucm.fdi.iw.model.Message;
import es.ucm.fdi.iw.model.Objeto;
import es.ucm.fdi.iw.model.Topic;
import es.ucm.fdi.iw.model.Unidad;
import es.ucm.fdi.iw.model.User.Role;
import jakarta.servlet.http.HttpSession;
import es.ucm.fdi.iw.model.User;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Controller
public class GameController {

    @Autowired
    private EntityManager entityManager;

    private static final Logger log = LogManager.getLogger(GameController.class);

    // Almacenamos las salas de juego activas
    private static final Map<String, GameRoom> activeGames = new ConcurrentHashMap<>();

    // Esto es un ejemplo de cómo puedes estructurar una sala de juego
    public static class GameRoom {
        private final String gameRoomId;
        private final String player1;
        private final String player2;

        public GameRoom(String gameRoomId, String player1, String player2) {
            this.gameRoomId = gameRoomId;
            this.player1 = player1;
            this.player2 = player2;
        }

        public String getGameRoomId() {
            return gameRoomId;
        }

        public String getPlayer1() {
            return player1;
        }

        public String getPlayer2() {
            return player2;
        }
    }

    // Método para añadir una sala de juego activa (por ejemplo, desde el matchmaking)
    public static void addActiveGame(String gameRoomId, String player1, String player2) {
        activeGames.put(gameRoomId, new GameRoom(gameRoomId, player1, player2));
    }
    
    @GetMapping("/game/{gameRoomId}")
    @Transactional
    public String showGamePage(@PathVariable String gameRoomId, Model model, HttpServletResponse response) {
        // Verificar si la sala de juego existe
        GameRoom gameRoom = activeGames.get(gameRoomId);

        if (gameRoom == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "errorPage";  // Devuelve una página de error si la sala no existe
        }

        // Cargar los datos de la sala de juego
        model.addAttribute("gameRoomId", gameRoom.getGameRoomId());
        model.addAttribute("player1", gameRoom.getPlayer1());
        model.addAttribute("player2", gameRoom.getPlayer2());

        // Aquí agregas los objetos y unidades de los jugadores para que los vea el frontend
        List<Objeto> playerObjects = List.of(
                new Objeto(null, "", 0, 0, 0, 0,"", 1),
                new Objeto(null, "", 0, 0, 0, 0,"", 1),
                new Objeto(null, "", 0, 0, 0, 0,"", 1),
                new Objeto(null, "", 0, 0, 0, 0,"", 1),
                new Objeto(null, "", 0, 0, 0, 0,"", 1),
                new Objeto(null, "", 0, 0, 0, 0,"", 1));
        model.addAttribute("playerObjects", playerObjects);

        List<Objeto> shopItems = List.of(
                new Objeto("/img/items/potion-red.png", "Poción Roja", 0, 0, 0, 0,"", 1),
                new Objeto("/img/items/flame-sword.png", "Espada de Hierro", 0, 0, 0, 0,"", 5));
        model.addAttribute("shopItems", shopItems);

        List<Heroe> shopUnits = List.of(
                new Heroe("Dragón", "/img/units/dragons/4. DGris/burner.png", 0, 0, 0, 0, null, 0, 2, 0),
                new Heroe("Esqueleto", "/img/units/humans/5. Mago/white-mage.png", 0, 0, 0, 0, null, 0, 4, 0),
                new Heroe("Mago", "/img/units/humans/5. Mago/white-mage.png", 0, 0, 0, 0, null, 0, 1, 0));
        model.addAttribute("shopUnits", shopUnits);

        List<Unidad> unitsP1 = new ArrayList<>(Arrays.asList(
                new Unidad("", null, 1, 0, 0, 0, "", 0, 0, 0, Arrays.asList(null, null)),
                new Unidad("", null, 1, 0, 0, 0, "", 0, 0, 0, Arrays.asList(null, null)),
                new Unidad("", null, 1, 0, 0, 0, "", 0, 0, 0, Arrays.asList(null, null)),
                new Unidad("", null, 1, 0, 0, 0, "", 0, 0, 0, Arrays.asList(null, null))));

        model.addAttribute("unitsP1", unitsP1);

        List<Unidad> unitsP2 = new ArrayList<>(Arrays.asList(
                new Unidad("", null, 1, 0, 0, 0, "", 0, 0, 0, Arrays.asList(null, null)),
                new Unidad("", null, 1, 0, 0, 0, "", 0, 0, 0, Arrays.asList(null, null)),
                new Unidad("", null, 1, 0, 0, 0, "", 0, 0, 0, Arrays.asList(null, null)),
                new Unidad("", null, 1, 0, 0, 0, "", 0, 0, 0, Arrays.asList(null, null))));
        model.addAttribute("unitsP2", unitsP2);

        List<Message> mensajes = new ArrayList<>();
        // Agregar los mensajes al modelo
        model.addAttribute("mensajes", mensajes);

        return "game";
    }
}

/*
@Controller
public class GameController {

    @Autowired
    private EntityManager entityManager;

    private static final Logger log = LogManager.getLogger(GameController.class);

    @ModelAttribute
    public void populateModel(HttpSession session, Model model) {
        for (String name : new String[] { "u", "url", "ws" })
            model.addAttribute(name, session.getAttribute(name));
    }

    // Método para añadir una sala de juego activa (por ejemplo, desde el matchmaking)
    public static void addActiveGame(String gameRoomId, String player1, String player2) {
        activeGames.put(gameRoomId, new GameRoom(gameRoomId, player1, player2));
    }

    @GetMapping("/game")
    @Transactional
    public String showGamePage(Model model) {

        // Verificar si el Topic "general" existe
        String topicKey = "general";
        Topic generalTopic;
        try {
            generalTopic = entityManager.createNamedQuery("Topic.byKey", Topic.class)
                                        .setParameter("key", topicKey)
                                        .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            // Si no existe, crearlo
            generalTopic = new Topic();
            generalTopic.setKey(topicKey);
            generalTopic.setName("General");
            entityManager.persist(generalTopic);
            log.info("Topic 'general' creado automáticamente.");
        }
        
        List<Objeto> playerObjects = List.of(
                new Objeto(null, "", 0, 0, 0, 0,"", 1),
                new Objeto(null, "", 0, 0, 0, 0,"", 1),
                new Objeto(null, "", 0, 0, 0, 0,"", 1),
                new Objeto(null, "", 0, 0, 0, 0,"", 1),
                new Objeto(null, "", 0, 0, 0, 0,"", 1),
                new Objeto(null, "", 0, 0, 0, 0,"", 1));
        model.addAttribute("playerObjects", playerObjects);

        List<Objeto> shopItems = List.of(
                new Objeto("/img/items/potion-red.png", "Poción Roja", 0, 0, 0, 0,"", 1),
                new Objeto("/img/items/flame-sword.png", "Espada de Hierro", 0, 0, 0, 0,"", 5));
        model.addAttribute("shopItems", shopItems);

        List<Heroe> shopUnits = List.of(
                new Heroe("Dragón", "/img/units/dragons/4. DGris/burner.png", 0, 0, 0, 0, null, 0, 2, 0),
                new Heroe("Esqueleto", "/img/units/humans/5. Mago/white-mage.png", 0, 0, 0, 0, null, 0, 4, 0),
                new Heroe("Mago", "/img/units/humans/5. Mago/white-mage.png", 0, 0, 0, 0, null, 0, 1, 0));
        model.addAttribute("shopUnits", shopUnits);

        List<Unidad> unitsP1 = new ArrayList<>(Arrays.asList(
                new Unidad("", null, 1, 0, 0, 0, "", 0, 0, 0, Arrays.asList(null, null)),
                new Unidad("", null, 1, 0, 0, 0, "", 0, 0, 0, Arrays.asList(null, null)),
                new Unidad("", null, 1, 0, 0, 0, "", 0, 0, 0, Arrays.asList(null, null)),
                new Unidad("", null, 1, 0, 0, 0, "", 0, 0, 0, Arrays.asList(null, null))));

        model.addAttribute("unitsP1", unitsP1);

        List<Unidad> unitsP2 = new ArrayList<>(Arrays.asList(
                new Unidad("", null, 1, 0, 0, 0, "", 0, 0, 0, Arrays.asList(null, null)),
                new Unidad("", null, 1, 0, 0, 0, "", 0, 0, 0, Arrays.asList(null, null)),
                new Unidad("", null, 1, 0, 0, 0, "", 0, 0, 0, Arrays.asList(null, null)),
                new Unidad("", null, 1, 0, 0, 0, "", 0, 0, 0, Arrays.asList(null, null))));
        model.addAttribute("unitsP2", unitsP2);

        Jugador j1 = new Jugador("Jugador 1");
        Jugador j2 = new Jugador("Jugador 2");

        List<Message> mensajes = new ArrayList<>();
        // Agregar los mensajes al modelo
        model.addAttribute("mensajes", mensajes);

        return "game";
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/topic/{name}")
    @ResponseBody
    @Transactional
    public Map<String, String> postMessage(@PathVariable String name, @RequestBody JsonNode o, Model model, HttpSession session, HttpServletResponse response) throws JsonProcessingException {
        String text = o.get("message").asText();
        User sender = entityManager.find(User.class, ((User) session.getAttribute("u")).getId());
        Topic target = entityManager.createNamedQuery("Topic.byKey", Topic.class).setParameter("key", name).getSingleResult();

        if(!sender.hasRole(Role.ADMIN) && !target.getMembers().contains(sender)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return Map.of("error", "user not in group");
        }

        // Guardado de mensaje en la base de datos
        Message m = new Message();
        m.setRecipient(null);
        m.setSender(sender);
        m.setTopic(target);
        m.setDateSent(LocalDateTime.now());
        m.setText(text);
        entityManager.persist(m); // save it to the database
        entityManager.flush(); // force the database to update    
    
        // Envio de mensaje a los usuarios en el grupo
        String json = new ObjectMapper().writeValueAsString(m.toTransfer());
        log.info("Sending a message to group {} with contents {}", target.getName(), json);
        messagingTemplate.convertAndSend("/topic/" + name, json);
        return Map.of("result", "message sent");
    }

    @GetMapping("/topic/{name}")
    @ResponseBody
    @Transactional
    public Map<String, String> getMessages(@PathVariable String name, HttpSession session, HttpServletResponse response) throws JsonProcessingException {
        
        User requester = entityManager.find(User.class, ((User) session.getAttribute("u")).getId());
        Topic target = entityManager.createNamedQuery("Topic.byKey", Topic.class).setParameter("key", name).getSingleResult();

        if(!requester.hasRole(Role.ADMIN) && !target.getMembers().contains(requester)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return Map.of("error", "user not in group");
        }

        return Map.of("messages", new ObjectMapper().writeValueAsString(target.getMessages().stream().map(Message::toTransfer).toArray()));
    }

}

*/