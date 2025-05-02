package es.ucm.fdi.iw.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Queue;
import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Controller
public class MatchmakingController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private Queue<String> waitingPlayers = new LinkedList<>(); // Cola de jugadores esperando

    @MessageMapping("/matchmaking/join")
    public void joinMatchmaking(Principal principal) {
        String username = principal.getName();
        System.out.println("Jugador conectado: " + username);
        waitingPlayers.add(username);

        if (waitingPlayers.size() >= 2) {
            String player1 = waitingPlayers.poll();
            String player2 = waitingPlayers.poll();

            String gameRoomId = UserController.generateRandomBase64Token(9);
            System.out.println("Emparejando: " + player1 + " y " + player2);
            System.out.println("Sala creada con ID: " + gameRoomId);

            // Crear objeto JSON para enviar
            Map<String, String> response = new HashMap<>();
            response.put("roomId", gameRoomId);

            GameController.addActiveGame(gameRoomId, player1, player2);

            messagingTemplate.convertAndSendToUser(player1, "/queue/match", response);
            messagingTemplate.convertAndSendToUser(player2, "/queue/match", response);
        } else {
            System.out.println("Esperando más jugadores...");
        }
    }

}
