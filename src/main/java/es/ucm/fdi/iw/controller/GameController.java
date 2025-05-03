package es.ucm.fdi.iw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.ucm.fdi.iw.model.GameItem;
import es.ucm.fdi.iw.model.GameMessage;
import es.ucm.fdi.iw.model.GamePlayer;
import es.ucm.fdi.iw.model.GameRoom;
import es.ucm.fdi.iw.model.GameRoom.Phase;
import es.ucm.fdi.iw.model.GameUnit;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Controller
public class GameController {

    @Autowired
    private EntityManager entityManager;

    private static final Logger log = LogManager.getLogger(GameController.class);

    // Almacenamos las salas de juego activas
    private static final Map<String, GameRoom> activeGames = new ConcurrentHashMap<>();

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

    // Método para añadir una sala de juego activa (por ejemplo, desde el matchmaking)
    public void addActiveGame(String gameRoomId, String player1, String player2) {

        activeGames.put(gameRoomId, new GameRoom(gameRoomId, player1, player2));

        // Espera 5 segundos antes de empezar la partida
        scheduler.schedule(() -> {
            startBuyPhase(gameRoomId);
        }, 5, TimeUnit.SECONDS);
    }
    
    @GetMapping("/game/{gameRoomId}")
    @Transactional
    public String showGamePage(@PathVariable String gameRoomId, Model model, HttpServletResponse response, Principal principal) {
        // Verificar si la sala de juego existe
        GameRoom gameRoom = activeGames.get(gameRoomId);

        if (gameRoom == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "errorPage";  // Devuelve una página de error si la sala no existe
        }

        String currentUsername = principal.getName();
        // Cargar los datos de la sala de juego
        model.addAttribute("gameRoomId", gameRoom.getGameRoomId());

        Map<String, GamePlayer> players = gameRoom.getPlayers();
        GamePlayer player1 = players.get(currentUsername);

        GamePlayer player2 = players.entrySet().stream()
            .filter(entry -> !entry.getKey().equals(currentUsername))
            .map(Map.Entry::getValue)
            .findFirst()
            .orElse(null);
        
        model.addAttribute("player1", player1);
        model.addAttribute("player2", player2);

        List<GameItem> playerObjects = new ArrayList<>();
        for (GameItem item : player1.getInventory()) {
            playerObjects.add(item);
        }
        while (playerObjects.size() < GamePlayer.MAX_ITEMS) {
            playerObjects.add(new GameItem(0, 0, "", "", null, "", 0, null, 0, 0));
        }   
        model.addAttribute("playerObjects", playerObjects);

        List<GameItem> opponentObjects = new ArrayList<>();
        for (GameItem item : player2.getInventory()) {
            opponentObjects.add(item);
        }
        while (opponentObjects.size() < GamePlayer.MAX_ITEMS) {
            opponentObjects.add(new GameItem(0, 0, "", "", null, "", 0, null, 0, 0));
        }   
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


        List<GameUnit> unitsP1 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            unitsP1.add(GameUnit.getNullUnit());
        }
        model.addAttribute("unitsP1", unitsP1);

        List<GameUnit> unitsP2 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            unitsP2.add(GameUnit.getNullUnit());
        }
        model.addAttribute("unitsP2", unitsP2);



        List<GameMessage> mensajes = gameRoom.getMessageHistory();
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

        String playerName = principal.getName();
        
        /*
        // Verificar si el jugador está en la partida
        if (!gameRoom.getPlayers().contains(playerName)) {
            log.error("El jugador {} no pertenece a la partida {}", playerName, gameRoomId);
            return;
        }
        */

        processAction(gameRoom, action, playerName);

        // Difundir la acción a los demás jugadores
        sendActionToPlayers(gameRoom, action, playerName);
    }

    private void processAction(GameRoom gameRoom, PlayerAction action, String senderPlayer) {
        ActionType type = action.getActionType();
        Map<String, Object> details = (Map<String, Object>) action.getActionDetails();

        ObjectMapper mapper = new ObjectMapper();

        switch (type) {
            case BUY_UNIT:
                Map<String, Object> unitMap1 = (Map<String, Object>) details.get("unit");
                unitMap1.remove("MAX_ITEMS");
                GameUnit unitBought = mapper.convertValue(unitMap1, GameUnit.class);
                gameRoom.playerBuyUnit(senderPlayer, unitBought);
                break;
            case SELL_UNIT:
                Map<String, Object> unitMap2 = (Map<String, Object>) details.get("unit");
                unitMap2.remove("MAX_ITEMS");
                GameUnit unitSold = mapper.convertValue(unitMap2, GameUnit.class);
                gameRoom.playerSellUnit(senderPlayer, unitSold);
                break;
            case BUY_ITEM:
                GameItem itemBought = mapper.convertValue(details.get("item"), GameItem.class);
                gameRoom.playerBuyItem(senderPlayer, itemBought);
                break;
            case SELL_ITEM:
                GameItem itemSold = mapper.convertValue(details.get("item"), GameItem.class);
                gameRoom.playerSellItem(senderPlayer, itemSold);
                break;
            case ASSIGN_ITEM_TO_UNIT:
                int unitIndex = (int) details.get("unitIndex");
                GameItem itemAssigned = mapper.convertValue(details.get("item"), GameItem.class);
                gameRoom.playerAssignItemToUnit(senderPlayer, unitIndex, itemAssigned);
                break;
            case REFRESH_SHOP:
                // Lógica para refrescar la tienda
                break;
            case SEND_MESSAGE:
                String message = (String) details.get("message");
                String timestamp = (String) details.get("timestamp");
                ZonedDateTime zdt = ZonedDateTime.parse(timestamp);
                gameRoom.addMessage(senderPlayer, message, zdt);
                break;
        }        

    }

    private void sendActionToPlayers(GameRoom gameRoom, PlayerAction action, String senderPlayer) {

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> basePayload = new HashMap<>();
        GamePlayer senderDataUnits = gameRoom.getPlayers().get(senderPlayer);
        GamePlayer senderDataItems = gameRoom.getPlayers().get(senderPlayer);
        boolean sendAll = true;
    
        switch (action.getActionType()) {
            case BUY_UNIT:
                basePayload.put("playerUnits", senderDataUnits.getUnits());
                basePayload.put("updateUnits", true);
                basePayload.put("updateItems", false);
                break;
            case BUY_ITEM:
            case SELL_ITEM:
                basePayload.put("updateUnits", false);
                basePayload.put("updateItems", true);
                basePayload.put("playerItems", new ArrayList<>(senderDataItems.getInventory()));
                break;
            case SELL_UNIT:
            case ASSIGN_ITEM_TO_UNIT:
                basePayload.put("playerUnits", senderDataUnits.getUnits());
                basePayload.put("playerItems", new ArrayList<>(senderDataItems.getInventory()));
                basePayload.put("updateUnits", true);
                basePayload.put("updateItems", true);
                break;
            case GENERAL:
                Map<String, GamePlayer> players = gameRoom.getPlayers();

                GamePlayer player1 = gameRoom.getPlayers().get(senderPlayer);
        
                GamePlayer player2 = players.entrySet().stream()
                    .filter(entry -> !entry.getKey().equals(senderPlayer))
                    .map(Map.Entry::getValue)
                    .findFirst()
                    .orElse(null);
                basePayload.put("player1Units", player1.getUnits());
                basePayload.put("player1Items", new ArrayList<>(player1.getInventory()));
                basePayload.put("player1Health", player1.getHealth());
                basePayload.put("player1Stars", player1.getStars()) ;
                basePayload.put("player2Units", player2.getUnits());
                basePayload.put("player2Items", new ArrayList<>(player2.getInventory()));
                basePayload.put("player2Health", player2.getHealth());
                basePayload.put("player2Stars", player2.getStars()) ;
                basePayload.put("updateAll", true);
                sendAll = false;
                break;
            case REFRESH_SHOP:
                sendAll = false;
                break;
            case SEND_MESSAGE:
                basePayload.put("newMessage", true);

                Map<String, Object> details = (Map<String, Object>) action.getActionDetails();
                String message = (String) details.get("message");
                String timestamp = (String) details.get("timestamp");

                // Crear un mapa para el mensaje
                Map<String, Object> messageMap = new HashMap<>();
                messageMap.put("text", message);
                messageMap.put("timestamp", timestamp);
                messageMap.put("playerName", senderPlayer);

                // Añadir el mensaje al payload
                basePayload.put("message", messageMap);

                break;
    
        }
    
        if (!sendAll) {
            Map<String, Object> payload = new HashMap<>(basePayload);
            payload.put("isSender", true);
            try {
                String jsonPayload = mapper.writeValueAsString(payload);
                messagingTemplate.convertAndSendToUser(
                    senderPlayer,
                    "/queue/game/" + gameRoom.getGameRoomId() + "/actions",
                    jsonPayload
                );
            } catch (JsonProcessingException e) {
                log.error("Error al convertir la acción a JSON: {}", action, e);
            }
        } else {
            for (String player : gameRoom.getPlayers().keySet()) {
                Map<String, Object> payload = new HashMap<>(basePayload);
                payload.put("isSender", player.equals(senderPlayer));
                try {
                    String jsonPayload = mapper.writeValueAsString(payload);
                    messagingTemplate.convertAndSendToUser(
                        player,
                        "/queue/game/" + gameRoom.getGameRoomId() + "/actions",
                        jsonPayload
                    );
                } catch (JsonProcessingException e) {
                    log.error("Error al convertir la acción a JSON: {}", action, e);
                }
            }
        }
    }

    @MessageMapping("/game/ready/{gameRoomId}")
    public void handleBattleReady(@DestinationVariable String gameRoomId, Principal principal) {
        GameRoom gameRoom = activeGames.get(gameRoomId);
        String playerName = principal.getName();
    
        synchronized (gameRoom) {
            gameRoom.setPlayerReady(playerName);
    
            if (gameRoom.bothPlayersReady()) {
                gameRoom.resetReadiness();
    
                // Validar si ya se está en fase de transición
                if (!gameRoom.isInTransition()) {
                    gameRoom.setInTransition(true); // bandera para evitar duplicación
    
                    scheduler.schedule(() -> {
                        startBuyPhase(gameRoomId);
                        gameRoom.setInTransition(false); // liberar transición
                    }, 5, TimeUnit.SECONDS);
                }
            }
        }
    }
    

    private void startBuyPhase(String gameRoomId) {

        GameRoom gameRoom = activeGames.get(gameRoomId);
        if (gameRoom == null) return;

        log.info("Comienza fase de compra para sala {}", gameRoomId);
        gameRoom.setCurrentPhase(Phase.BUY);

        // Notificar a los jugadores que comienza la fase de compra
        Map<String, Object> payload = Map.of(
            "phase", "buy",
            "round", gameRoom.getCurrentRound(),
            "time", GameRoom.SHOP_TIME
        );
        for (String player : gameRoom.getPlayers().keySet()) {
            messagingTemplate.convertAndSendToUser(
                player,
                "/queue/game/" + gameRoomId + "/actions",
                payload
            );
        }

        // Iniciar temporizador de 30 segundos
        scheduler.schedule(() -> {
            log.info("Fase de compra terminó automáticamente para sala {}", gameRoomId);
            startBattlePhase(gameRoomId);
        }, GameRoom.SHOP_TIME + 1, TimeUnit.SECONDS);
    }

    private void startBattlePhase(String gameRoomId) {
        GameRoom gameRoom = activeGames.get(gameRoomId);
        if (gameRoom == null) return;
    
        log.info("Comienza fase de batalla para sala {}", gameRoomId);
    
        // Avanzamos la ronda
        gameRoom.nextRound();
    
        // Notificar a los jugadores que deben esperar y luego confirmar
        Map<String, Object> payload = Map.of(
            "phase", "battle",
            "round", gameRoom.getCurrentRound()
        );
        for (String player : gameRoom.getPlayers().keySet()) {
            messagingTemplate.convertAndSendToUser(
                player,
                "/queue/game/" + gameRoomId + "/actions",
                payload
            );
        }

        gameRoom.fight();
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /*
    private void resolveBattle(GameRoom gameRoom) {
        String loser = gameRoom.calculateBattleLoser(); // Método que debes definir según tu lógica
        if (loser != null) {
            GamePlayer losingPlayer = gameRoom.getPlayers().get(loser);
            losingPlayer.reduceHealth(5);
    
            if (losingPlayer.getHealth() <= 0) {
                endGame(gameRoom, loser);
            }
        }
    }

    private void endGame(GameRoom gameRoom, String loser) {
        String winner = gameRoom.getPlayers().keySet().stream()
            .filter(name -> !name.equals(loser))
            .findFirst()
            .orElse("Desconocido");
    
        Map<String, Object> payload = Map.of(
            "gameOver", true,
            "winner", winner
        );
    
        for (String player : gameRoom.getPlayers().keySet()) {
            messagingTemplate.convertAndSendToUser(
                player,
                "/queue/game/" + gameRoom.getGameRoomId() + "/actions",
                payload
            );
        }
    
        activeGames.remove(gameRoom.getGameRoomId());
    }
    */

}