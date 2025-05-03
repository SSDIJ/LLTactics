package es.ucm.fdi.iw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.ucm.fdi.iw.model.GameRoom;
import es.ucm.fdi.iw.model.Heroe;
import es.ucm.fdi.iw.model.Jugador;
import es.ucm.fdi.iw.model.Message;
import es.ucm.fdi.iw.model.Objeto;
import es.ucm.fdi.iw.model.PlayerAction;
import es.ucm.fdi.iw.model.Topic;
import es.ucm.fdi.iw.model.Unidad;
import es.ucm.fdi.iw.model.User.Role;
import jakarta.servlet.http.HttpSession;
import es.ucm.fdi.iw.model.User;
import es.ucm.fdi.iw.model.PlayerAction.ActionType;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
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
import java.time.ZonedDateTime;
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

        // Aquí agregas los objetos y unidades de los jugadores para que los vea el frontend
        List<Objeto> opponentObjects = List.of(
                new Objeto(null, "", 0, 0, 0, 0,"", 1),
                new Objeto(null, "", 0, 0, 0, 0,"", 1),
                new Objeto(null, "", 0, 0, 0, 0,"", 1),
                new Objeto(null, "", 0, 0, 0, 0,"", 1),
                new Objeto(null, "", 0, 0, 0, 0,"", 1),
                new Objeto(null, "", 0, 0, 0, 0,"", 1));
        model.addAttribute("opponentObjects", opponentObjects);

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


        List<GameRoom.MessageEntry> mensajes = gameRoom.getMessageHistory();
        model.addAttribute("mensajes", mensajes);

        return "game";
    }

    // Método para recibir la acción de un jugador
    @MessageMapping("/game/action/{gameRoomId}")
    public void handlePlayerAction(@DestinationVariable String gameRoomId, @Payload PlayerAction action, Principal principal) {
        // Verificar si la sala de juego existe
        GameRoom gameRoom = activeGames.get(gameRoomId);

        if (gameRoom == null) {
            log.error("Intento de acción en una sala no existente: {}", gameRoomId);
            return;
        }

        // Verificar si el jugador está en la partida
        String playerName = principal.getName();
        if (!gameRoom.getPlayers().contains(playerName)) {
            log.error("El jugador {} no pertenece a la partida {}", playerName, gameRoomId);
            return;
        }

        // Validar la acción
        if (!isValidAction(action)) {
            log.error("Acción inválida recibida de {} en la partida {}: {}", playerName, gameRoomId, action);
            return;
        }

        processAction(gameRoom, action, playerName);

        // Difundir la acción a los demás jugadores
        sendActionToPlayers(gameRoom, action, playerName);
    }

    // Método para validar la acción
    private boolean isValidAction(PlayerAction action) {
        // Aquí puedes poner la lógica de validación según el tipo de acción
        // Por ejemplo: comprobar si la acción es un movimiento válido, si el jugador tiene suficiente energía, etc.
        return true;  // Ejemplo: siempre válida, deberías implementar tu propia validación
    }

    private void processAction(GameRoom gameRoom, PlayerAction action, String senderPlayer) {
        ActionType type = action.getActionType();

        switch (type) {
            case BUY_UNIT:
                // Lógica para comprar una unidad
                break;
            case SELL_UNIT:
                // Lógica para vender una unidad
                break;
            case BUY_ITEM:
                // Lógica para comprar un ítem
                break;
            case SELL_ITEM:
                // Lógica para vender un ítem
                break;
            case ASSIGN_ITEM_TO_UNIT:
                // Lógica para asignar un ítem a una unidad
                break;
            case REFRESH_SHOP:
                // Lógica para refrescar la tienda
                break;
            case SEND_MESSAGE:
                Map<String, Object> details = (Map<String, Object> ) action.getActionDetails();
                String message = (String) details.get("message");
                String timestamp = (String) details.get("timestamp");
                ZonedDateTime zdt = ZonedDateTime.parse(timestamp);
                gameRoom.addMessage(senderPlayer, message, zdt);
                break;
        }        
    }

    // Método para enviar la acción a los jugadores
    private void sendActionToPlayers(GameRoom gameRoom, PlayerAction action, String senderPlayer) {
        String jsonAction;
        try {
            jsonAction = new ObjectMapper().writeValueAsString(action);
        } catch (JsonProcessingException e) {
            log.error("Error al convertir la acción a JSON: {}", action, e);
            return;
        }

        // Enviar la acción a los jugadores
        gameRoom.getPlayers().stream()
        .filter(player -> !player.equals(senderPlayer)) // Filtra el jugador que envió la acción
        .forEach(player -> 
            messagingTemplate.convertAndSendToUser(player, "/queue/game/" + gameRoom.getGameRoomId() + "/actions", jsonAction)
        );
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
}