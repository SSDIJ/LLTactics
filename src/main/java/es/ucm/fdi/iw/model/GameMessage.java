package es.ucm.fdi.iw.model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class GameMessage {
    private final String playerName;
    private final String message;
    private final ZonedDateTime timestamp;
    private final String formattedTime;

    public GameMessage(String playerName, String message, ZonedDateTime timestamp) {
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