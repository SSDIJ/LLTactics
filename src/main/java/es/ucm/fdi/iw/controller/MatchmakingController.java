package es.ucm.fdi.iw.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.security.Principal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    
}
