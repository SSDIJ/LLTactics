package es.ucm.fdi.iw.model;

import java.util.Arrays;
import java.util.List;

public class GameRoom {
    private final String gameRoomId;
    private final String player1;
    private final String player2;
    private int currentRound;

    public GameRoom(String gameRoomId, String player1, String player2) {
        this.gameRoomId = gameRoomId;
        this.player1 = player1;
        this.player2 = player2;
        this.currentRound = 2;
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

    public int getCurrentRound() {
        return this.currentRound;
    }

    public List<String> getPlayers() {
        return Arrays.asList(player1, player2);
    }
}