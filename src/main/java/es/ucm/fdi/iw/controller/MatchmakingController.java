package es.ucm.fdi.iw.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.ucm.fdi.iw.model.GameRoom;
import es.ucm.fdi.iw.model.Partida;
import es.ucm.fdi.iw.model.User;
import es.ucm.fdi.iw.repositories.PartidasRepository;
import es.ucm.fdi.iw.repositories.UserRepository;
import jakarta.transaction.Transactional;


@Controller
public class MatchmakingController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private GameController gameController;

    @PostMapping("/game/matchmaking")
    @Transactional
    @ResponseBody
    public void matchmaking(Principal principal, @RequestParam(required = false) String accept) {
        String playerName = principal.getName();

        if (accept == null) {
            // Paso 1: Este jugador quiere jugar, anuncia su presencia
            Map<String, String> payload = new HashMap<>();
            payload.put("mm-type", "waiting");
            payload.put("mm-playerId", playerName); // Usamos nombre como identificador único
            messagingTemplate.convertAndSend("/topic/game/matchmaking", payload);
        } else {
            // Paso 2: Este jugador acepta duelo con 'accept'
            String player1 = accept;
            String player2 = playerName;

            if (player1.equals(player2)) return;

            String gameRoomId = UserController.generateRandomBase64Token(9);
            gameController.addActiveGame(gameRoomId, player1, player2);

            // Paso 3: Notificamos a ambos jugadores
            Map<String, String> payload = new HashMap<>();
            payload.put("mm-type", "start");
            payload.put("mm-roomId", gameRoomId);
            payload.put("mm-player1", player1);
            payload.put("mm-player2", player2);

            messagingTemplate.convertAndSend("/topic/game/matchmaking", payload);
        }
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PartidasRepository partidaRepository;

    @PostMapping("/game/createPrivateRoom")
    @Transactional
    @ResponseBody
    public ResponseEntity<String> createPrivateRoom(
            Principal principal,
            @RequestBody Map<String, String> body) throws JsonProcessingException {

        String password = body.get("password");
        String ownerName = principal.getName();
        User creador = userRepository.findByUsername(ownerName).orElse(null);

        if (creador == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
        }

        // Generar un gameRoomId único
        String gameRoomId = UserController.generateRandomBase64Token(9);

        Partida partida = new Partida();
        partida.setCreador(creador);
        partida.setJugador1(creador); // jugador2 se añadirá cuando alguien se una
        partida.setJugador2(creador); // temporal, para cumplir la restricción not null
        partida.setGameRoomId(gameRoomId);
        partida.setEnCurso(true);
        partida.setGanador(0);
        partida.setPerdedor(0);
        partida.setInicio(LocalDateTime.now());
        partida.setEsCustom(true);
        partida.setContraseña(password);
        partida.setEstado("{}"); // o estado inicial por defecto

        partidaRepository.save(partida);

        Map<String, String> response = new HashMap<>();
        response.put("roomId", gameRoomId);

        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(response);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(jsonResponse);
    }

    @PostMapping("/game/joinPrivateRoom")
    @ResponseBody
    @Transactional
    public ResponseEntity<?> joinPrivateRoom(
            Principal principal,
            @RequestBody Map<String, String> body) {

        String roomId = body.get("roomId");
        String password = body.get("password");

        if (roomId == null || password == null) {
            return ResponseEntity.badRequest().body("Faltan parámetros");
        }

        Partida partida = partidaRepository.findByGameRoomId(roomId).orElse(null);

        if (partida == null || !Boolean.TRUE.equals(partida.getEsCustom())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sala no encontrada");
        }

        if (!password.equals(partida.getContraseña())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
        }

        User jugador = userRepository.findByUsername(principal.getName()).orElse(null);
        if (jugador == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no válido");
        }

        // Asignar jugador2 si aún no lo está
        if (partida.getJugador2() == null || partida.getJugador2().equals(partida.getCreador())) {
            partida.setJugador2(jugador);
            partidaRepository.save(partida);
        }
        gameController.initializePrivateGameRoom(partida);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(() -> {
            messagingTemplate.convertAndSend("/topic/game/privateRoom/" + roomId, Map.of(
                "type", "join",
                "username", jugador.getUsername(),
                "roomId", roomId
            ));
        }, 2, TimeUnit.SECONDS);

        return ResponseEntity.ok(Map.of(
            "message", "Unido correctamente a la sala",
            "roomId", roomId
        ));
    }
}
