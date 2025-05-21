package es.ucm.fdi.iw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import es.ucm.fdi.iw.model.ConfigPartida;
import es.ucm.fdi.iw.model.GameItem;
import es.ucm.fdi.iw.model.GameMessage;
import es.ucm.fdi.iw.model.GamePlayer;
import es.ucm.fdi.iw.model.GameRoom;
import es.ucm.fdi.iw.model.GameShop;
import es.ucm.fdi.iw.model.GameRoom.Phase;
import es.ucm.fdi.iw.model.GameUnit;
import es.ucm.fdi.iw.model.Heroe;
import es.ucm.fdi.iw.model.Message;
import es.ucm.fdi.iw.model.Objeto;
import es.ucm.fdi.iw.model.Partida;
//import es.ucm.fdi.iw.model.PartidaActiva;
import es.ucm.fdi.iw.model.PlayerAction;
import es.ucm.fdi.iw.model.Topic;
import es.ucm.fdi.iw.model.Unidad;
import es.ucm.fdi.iw.model.User.Role;
import es.ucm.fdi.iw.repositories.ConfigPartidaRepository;
import es.ucm.fdi.iw.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import es.ucm.fdi.iw.model.User;
import es.ucm.fdi.iw.model.PlayerAction.ActionType;
import jakarta.annotation.PostConstruct;
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
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

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

import es.ucm.fdi.iw.controller.UserController;

@Controller
public class GameController {

    @Autowired
    private EntityManager entityManager;

    private static final Logger log = LogManager.getLogger(GameController.class);

    @Autowired
    private UserController userController;

    @Autowired 
    private UnitController unitController;

    @Autowired
    private ItemController itemController;

    @Autowired
    private ConfigPartidaRepository configPartidaRepository;

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

    @ModelAttribute
    public void populateModel(HttpSession session, Model model) {
        for (String name : new String[] { "u", "url", "ws", "gameId" }) {
            model.addAttribute(name, session.getAttribute(name));
        }
    }

    @PostConstruct
    public void init() {
        ConfigPartida config = configPartidaRepository.findAll().stream().findFirst().orElse(null);
        GameRoom.setConfig(config);
    }

    public void addActiveGame(String gameRoomId, String player1, String player2) {
        System.out.println("INTENTANDO GUARDAR PARTIDA EN BD");

        ConfigPartida config = configPartidaRepository.findAll().stream().findFirst().orElse(null);
        // Crear una nueva instancia de GameRoom
        GameRoom gameRoom = new GameRoom(gameRoomId, player1, player2, config);
        updatePlayerShop(gameRoom, gameRoom.getPlayer1Name(), false);
        updatePlayerShop(gameRoom, gameRoom.getPlayer2Name(), false);

        // Serializar el estado inicial de la partida a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String estadoPartidaJson = "";
        
        //Parsea el estado de la partida a JSON
        try {
            estadoPartidaJson = objectMapper.writeValueAsString(gameRoom);
        } catch (JsonProcessingException e) {
            log.error("Error al serializar el estado de la partida: {}", e.getMessage());
            return;
        } 

        // Buscar los usuarios en la base de datos
        User jugador1 = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", player1)
                .getSingleResult();

        User jugador2 = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", player2)
                .getSingleResult();

        // Crear una nueva instancia de Partida
        Partida partida = new Partida(jugador1, jugador2, estadoPartidaJson, gameRoomId);

        // Guardar la partida en la base de datos
        entityManager.persist(partida);
        entityManager.flush();
    }

    @GetMapping("/game/{gameRoomId}")
    @Transactional
    public String showGamePage(@PathVariable String gameRoomId, HttpSession session, Model model,
            HttpServletResponse response, Principal principal) {
        // Verificar si la sala de juego existe
        GameRoom gameRoom = getGameRoomFromDatabase(gameRoomId);

        if (gameRoom == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "errorPage"; // Devuelve una página de error si la sala no existe
        }

        String currentUsername = principal.getName();

        // Cargar los datos de la sala de juego
        model.addAttribute("gameRoomId", gameRoom.getGameRoomId());
        session.setAttribute("gameId", gameRoom.getGameRoomId());

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
            playerObjects.add(new GameItem());
        }
        model.addAttribute("playerObjects", playerObjects);

        List<GameItem> opponentObjects = new ArrayList<>();
        for (GameItem item : player2.getInventory()) {
            opponentObjects.add(item);
        }
        while (opponentObjects.size() < GamePlayer.MAX_ITEMS) {
            opponentObjects.add(new GameItem());
        }
        model.addAttribute("opponentObjects", opponentObjects);

        List<Objeto> shopItems = player1.getShop().getItems();
        model.addAttribute("shopItems", shopItems);

        List<Heroe> shopUnits = player1.getShop().getUnits();
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

        // Actualizar el estado de la partida en la base de datos
        updateGameRoomInDatabase(gameRoomId, gameRoom);

        return "game";
    }

    // Método para recibir la acción de un jugador
    @PostMapping("/game/action/{gameRoomId}")
    @Transactional
    @ResponseBody
    public void handlePlayerAction(@PathVariable String gameRoomId, @RequestBody PlayerAction action,
            Principal principal) {
        // Verificar si la sala de juego existe
        GameRoom gameRoom = getGameRoomFromDatabase(gameRoomId);

        if (gameRoom == null) {
            log.error("Intento de acción en una sala no existente: {}", gameRoomId);
            return;
        }

        String playerName = principal.getName();

        // Verificar si el jugador está en la partida
        if (!gameRoom.getPlayers().containsKey(playerName)) {
            log.error("El jugador {} no pertenece a la partida {}", playerName,
            gameRoomId);
            return;
        }

        // Determinamos quién puede hacer qué cuándo
        if (!gameRoom.canDoAction(action)) return;
         
        try {
            processAction(gameRoom, action, playerName);
        } catch (JsonProcessingException e) {
            log.warn("Error al procesar la acción del jugador {}: {}", playerName, action, e);
        }
        // Actualizar el estado de la partida en la base de datos
        updateGameRoomInDatabase(gameRoomId, gameRoom);
        // Difundir la acción a los demás jugadores
        sendActionToPlayers(gameRoom, action, playerName);
    }

    private void processAction(GameRoom gameRoom, PlayerAction action, String senderPlayer)
            throws JsonProcessingException {
        ActionType type = action.getActionType();
        String details = action.getActionDetails();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        switch (type) {
            case BUY_UNIT:
                GameUnit unitBought = mapper.readValue(details, GameUnit.class);
                gameRoom.playerBuyUnit(senderPlayer, unitBought);
                //ACTUALIZAR AQUI TANTO EL USO DE FACCIONES COMO EL USO DE TROPAS
                userController.updateUserByUsername(senderPlayer, unitBought);
                break;
            case SELL_UNIT:
                GameUnit unitSold = mapper.readValue(details, GameUnit.class);
                gameRoom.playerSellUnit(senderPlayer, unitSold);
                break;
            case BUY_ITEM:
                GameItem itemBought = mapper.readValue(details, GameItem.class);
                gameRoom.playerBuyItem(senderPlayer, itemBought);
                break;
            case SELL_ITEM:
                GameItem itemSold = mapper.readValue(details, GameItem.class);
                gameRoom.playerSellItem(senderPlayer, itemSold);
                break;
            case ASSIGN_ITEM_TO_UNIT:
                GameItem itemAssigned = mapper.readValue(details, GameItem.class);
                gameRoom.playerAssignItemToUnit(senderPlayer, itemAssigned.getUnitUnitId(), itemAssigned);
                break;
            case REFRESH_SHOP:
                updatePlayerShop(gameRoom, senderPlayer, true);
                break;
            case SEND_MESSAGE:
                gameRoom.addMessage(senderPlayer, details, ZonedDateTime.now());
                break;
            default:
                log.warn("Acción desconocida: {}", action);
                break;
        }
    }

    private void updatePlayerShop(GameRoom gameRoom, String player, boolean cost) {
        List<Heroe> heroes = unitController.getRandomUnits(3);
        List<Objeto> items = itemController.getRandomItems(2);
        gameRoom.refreshPlayerShop(player, heroes, items, cost);
    }

    private void sendActionToPlayers(GameRoom gameRoom, PlayerAction action) {
        sendActionToPlayers(gameRoom, action, "server");
    }

    private void sendActionToPlayers(GameRoom gameRoom, PlayerAction action, String senderPlayer) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Map<String, Object> payload = new HashMap<>();
        GamePlayer sender = gameRoom.getPlayers().get(senderPlayer);
        payload.put("actor", senderPlayer);

        for (Map.Entry<String, GamePlayer> entry : gameRoom.getPlayers().entrySet()) {
            String playerName = entry.getKey();
            GamePlayer player = entry.getValue();
            payload.put("health_" + playerName, player.getHealth());
            payload.put("stars_" + playerName, player.getStars());
        }

        switch (action.getActionType()) {
            case BUY_UNIT:
                payload.put("units_" + senderPlayer, sender.getUnits());
                payload.put("updateUnits", true);
                payload.put("updateItems", false);
                break;

            case BUY_ITEM:
            case SELL_ITEM:
                payload.put("items_" + senderPlayer, new ArrayList<>(sender.getInventory()));
                payload.put("updateUnits", false);
                payload.put("updateItems", true);
                break;

            case SELL_UNIT:
            case ASSIGN_ITEM_TO_UNIT:
                payload.put("units_" + senderPlayer, sender.getUnits());
                payload.put("items_" + senderPlayer, new ArrayList<>(sender.getInventory()));
                payload.put("updateUnits", true);
                payload.put("updateItems", true);
                break;

            case GENERAL:

                Map<String, Boolean> battleReady = gameRoom.getBattleReady();
                payload.put("updateAll", true);
                payload.put("currentRound", gameRoom.getCurrentRound());
                payload.put("currentPhase", gameRoom.getCurrentPhase());

                System.out.println("\n\n\n");
                System.out.println(gameRoom.getCurrentPhase());
                System.out.println("\n\n\n");
                payload.put("preferredPlayer", gameRoom.getPreferredPlayer());
                for (Map.Entry<String, GamePlayer> entry : gameRoom.getPlayers().entrySet()) {
                    String playerName = entry.getKey();
                    GamePlayer player = entry.getValue();
                    payload.put("units_" + playerName, player.getUnits());
                    payload.put("items_" + playerName, new ArrayList<>(player.getInventory()));
                    payload.put("health_" + playerName, player.getHealth());
                    payload.put("stars_" + playerName, player.getStars());
                    payload.put("name_" + playerName, player.getName());
                    payload.put("shop_" + playerName, player.getShop());
                    Boolean isReady = battleReady.getOrDefault(playerName, false);
                    payload.put("ready_" + playerName, isReady);
                }
                break;

            case WINNER:
                String winnerDetails = action.getActionDetails();
                payload.put("isWinner", true);
                payload.put("winner", winnerDetails);
                
                break;

            case REFRESH_SHOP:
                payload.put("updateShop", true);
                payload.put("shop_" + senderPlayer, sender.getShop());
                break;

            case SEND_MESSAGE:
                payload.put("newMessage", true);
                Map<String, Object> messageMap = new HashMap<>();
                messageMap.put("text", action.getActionDetails());
                messageMap.put("timestamp", ZonedDateTime.now());
                messageMap.put("playerName", senderPlayer);
                payload.put("message", messageMap);
                break;
        }

        try {
            String jsonPayload = mapper.writeValueAsString(payload);
            messagingTemplate.convertAndSend(
                    "/topic/game/" + gameRoom.getGameRoomId(),
                    jsonPayload);
        } catch (JsonProcessingException e) {
            log.error("Error al convertir la acción a JSON: {}", action, e);
        }
    }

    @PostMapping("/game/ready/{gameRoomId}")
    @Transactional
    @ResponseBody
    public void handleRoundReady(@PathVariable String gameRoomId,
            Principal principal) {

        System.out.println("\n\nDETECTADO LISTO\n\n");
        GameRoom gameRoom = getGameRoomFromDatabase(gameRoomId);
        String playerName = principal.getName();

        gameRoom.setPlayerReady(playerName);

        boolean allReady = gameRoom.bothPlayersReady();

        if (allReady) gameRoom.resetReadiness();

        String winner = gameRoom.getWinner();
        if (winner != null) {

            PlayerAction winnerAction = new PlayerAction(
                    PlayerAction.ActionType.WINNER,
                    "server",
                    winner);

            sendActionToPlayers(gameRoom, winnerAction);
            String defeated = gameRoom.getPlayers().keySet().stream()
                    .filter(p -> !p.equals(winner))
                    .findFirst()
                    .orElse(null);

            if (defeated != null) {
                userController.updateWinner(winner, defeated);
            }
            return;
        }

        updateGameRoomInDatabase(gameRoomId, gameRoom);

        if (allReady) {
            // Control de transición para evitar glitches
            if (gameRoom.isInTransition()) {
                return; // Ya se está cambiando de fase, no hacer nada
            }
            gameRoom.setInTransition(true);

            if (gameRoom.isBuyingPhase()) {
                prepareDefaultUnits(gameRoom);
                sendActionToPlayers(gameRoom, new PlayerAction(ActionType.GENERAL, "server", ""));
                startBattlePhase(gameRoomId);
            } else {
                sendActionToPlayers(gameRoom, new PlayerAction(ActionType.GENERAL, "server", ""));
                startBuyPhase(gameRoomId);
            }
        }
    }

    private void prepareDefaultUnits(GameRoom gameRoom) {
        GamePlayer player1 = gameRoom.getPlayers().get(gameRoom.getPlayer1Name());
        GamePlayer player2 = gameRoom.getPlayers().get(gameRoom.getPlayer2Name());

        GameUnit unit1 = gameRoom.getLastValidUnit(player1); // let unit1 = player1.units.slice().reverse().find(u => u.unitID && u.unitID !== null);  
        GameUnit unit2 = gameRoom.getFirstValidUnit(player2); // let unit2 = player2.units.find(u => u.unitID && u.unitID !== null);

        // Si no hay unidad válida, compra unidad por defecto
        if (unit1 == null) {
            player1.buyUnit(player1.getDefaultUnit());
            unit1 = gameRoom.getLastValidUnit(player1);
        }
        if (unit2 == null) {
            player2.buyUnit(player2.getDefaultUnit());
            unit2 = gameRoom.getFirstValidUnit(player2);
        }
    }
    
    private void startBuyPhase(String gameRoomId) {

        GameRoom gameRoom = getGameRoomFromDatabase(gameRoomId);

        if (gameRoom == null)
            return;

        gameRoom.nextRound();
        gameRoom.setBuyPhase();

        System.out.println("\n\n\n");
        System.out.println(gameRoom.getCurrentPhase());
        System.out.println("\n\n\n");

        boolean cost = false;

        updatePlayerShop(gameRoom, gameRoom.getPlayer1Name(), cost);
        updatePlayerShop(gameRoom, gameRoom.getPlayer2Name(), cost);

        if (gameRoom.getCurrentRound() != 1)
            gameRoom.newRoundStars();

        // Actualizar el estado de la partida en la base de datos

        sendActionToPlayers(gameRoom, new PlayerAction(PlayerAction.ActionType.GENERAL, "server", ""));
        
        log.info("Comienza fase de compra para sala {}", gameRoomId);

        // Notificar a los jugadores que comienza la fase de compra
        Map<String, Object> payload = Map.of(
                "phase", "buy",
                "round", gameRoom.getCurrentRound());
        
        messagingTemplate.convertAndSend(
            "/topic/game/" + gameRoomId,
            payload);

        gameRoom.setInTransition(false);
        updateGameRoomInDatabase(gameRoomId, gameRoom);
    }

    private void startBattlePhase(String gameRoomId) {
        GameRoom gameRoom = getGameRoomFromDatabase(gameRoomId);
        if (gameRoom == null)
            return;

        log.info("Comienza fase de batalla para sala {}", gameRoomId);

        // Avanzamos la ronda
        gameRoom.nextRound();
        gameRoom.setBattlePhase();

        // Notificar a los jugadores que deben esperar y luego confirmar
        Map<String, Object> payload = Map.of(
                "phase", "battle",
                "round", gameRoom.getCurrentRound());
        messagingTemplate.convertAndSend(
            "/topic/game/" + gameRoomId,
            payload);

        
        gameRoom.fight();
        
        gameRoom.setInTransition(false);
        // Actualizar el estado de la partida en la base de datos
        updateGameRoomInDatabase(gameRoomId, gameRoom);
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    //FUNCION QUE DEVUELVE LA PARTIDA DESDE LA BD
    public GameRoom getGameRoomFromDatabase(String gameRoomId) {
        try {
            // Buscar la partida activa en la base de datos
            Partida partida = entityManager.createQuery(
                "SELECT p FROM Partida p WHERE p.gameRoomId = :gameRoomId", Partida.class)
                .setParameter("gameRoomId", gameRoomId) // No convertir a Long, ya que gameRoomId es un String
                .getSingleResult();

            // Obtener el estado de la partida en formato JSON
            String estadoPartidaJson = partida.getEstado();

            // Deserializar el JSON a un objeto GameRoom
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // Ignorar propiedades desconocidas
            GameRoom gameRoom = objectMapper.readValue(estadoPartidaJson, GameRoom.class);

            return gameRoom;
        } catch (Exception e) {
            log.error("Error al obtener o deserializar el estado de la partida: {}", e.getMessage());
            return null;
        }
    }

    //FUNCION QUE ACTUALIZA LA PARTIDA EN LA BD
    public void updateGameRoomInDatabase(String gameRoomId, GameRoom gameRoom) {
        try {
            // Serializar el objeto GameRoom a JSON
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String estadoPartidaJson = objectMapper.writeValueAsString(gameRoom);

            // Buscar la partida activa en la base de datos
            Partida partida = entityManager.createQuery(
                    "SELECT p FROM Partida p WHERE p.gameRoomId = :gameRoomId", Partida.class)
                    .setParameter("gameRoomId", gameRoomId)
                    .getSingleResult();

            // Actualizar el estado de la partida
            partida.setEstado(estadoPartidaJson);
            entityManager.merge(partida); // Actualizar la entidad en la base de datos

            log.info("Estado de la partida actualizado en la base de datos para gameRoomId: {}", gameRoomId);
        } catch (JsonProcessingException e) {
            log.error("Error al actualizar el estado de la partida en la base de datos: {}", e.getMessage(), e);
        }
    }
}