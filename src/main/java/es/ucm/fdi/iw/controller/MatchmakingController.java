package es.ucm.fdi.iw.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.security.Principal;

@Controller
public class MatchmakingController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private GameController gameController;

    private Queue<String> waitingPlayers = new LinkedList<>(); // Cola de jugadores esperando
    private Set<String> playersInQueue = new HashSet<>();      // Seguimiento de jugadores en cola

    @MessageMapping("/matchmaking/join")
    public void joinMatchmaking(Principal principal) {
        String username = principal.getName();
        System.out.println("Jugador conectado: " + username);

        synchronized (this) {
            if (playersInQueue.contains(username)) {
                System.out.println("Jugador ya en cola: " + username);
                return;
            }

            waitingPlayers.add(username);
            playersInQueue.add(username);
        }

        if (waitingPlayers.size() >= 2) {
            String player1, player2;

            synchronized (this) {
                player1 = waitingPlayers.poll();
                player2 = waitingPlayers.poll();
                playersInQueue.remove(player1);
                playersInQueue.remove(player2);
            }

            String gameRoomId = UserController.generateRandomBase64Token(9);
            System.out.println("Emparejando: " + player1 + " y " + player2);
            System.out.println("Sala creada con ID: " + gameRoomId);

            Map<String, String> response = new HashMap<>();
            response.put("roomId", gameRoomId);

            gameController.addActiveGame(gameRoomId, player1, player2);

            messagingTemplate.convertAndSendToUser(player1, "/queue/match", response);
            messagingTemplate.convertAndSendToUser(player2, "/queue/match", response);
        } else {
            System.out.println("Esperando más jugadores...");
        }
    }
}
