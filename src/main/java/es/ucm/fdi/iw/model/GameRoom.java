package es.ucm.fdi.iw.model;

import java.util.Arrays;
import java.util.List;

import java.util.ArrayList;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class GameRoom {

    public static class MessageEntry {
        private final String playerName;
        private final String message;
        private final ZonedDateTime timestamp;
        private final String formattedTime;

        public MessageEntry(String playerName, String message, ZonedDateTime timestamp) {
            this.playerName = playerName;
            this.message = message;
            this.timestamp = timestamp;
            this.formattedTime = timestamp.format(DateTimeFormatter.ofPattern("HH:mm"));
        }

        public String getPlayerName() {
            return playerName;
        }

        public String getMessage() {
            return message;
        }

        public String getFormattedTime() {
            return formattedTime;
        }

        public ZonedDateTime getTimestamp() {
            return timestamp;
        }
    }

    private final String gameRoomId;
    private final String player1;
    private final String player2;
    private int currentRound;
    private final List<MessageEntry> messageHistory;

    public GameRoom(String gameRoomId, String player1, String player2) {
        this.gameRoomId = gameRoomId;
        this.player1 = player1;
        this.player2 = player2;
        this.currentRound = 2;
        this.messageHistory = new ArrayList<>();
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

    public void addMessage(String playerName, String message, ZonedDateTime timestamp) {
        messageHistory.add(new MessageEntry(playerName, message, timestamp));
    }

    public List<MessageEntry> getMessageHistory() {
        return messageHistory;
    }
}
