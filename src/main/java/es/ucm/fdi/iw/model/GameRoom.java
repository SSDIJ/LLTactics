package es.ucm.fdi.iw.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

import java.util.Set;

import java.util.ArrayList;
import java.time.ZonedDateTime;

@Data
public class GameRoom {

    public static int INITIAL_STARS;
    public static int INITIAL_LIFE;
    public static int DAMAGE_WIN;
    public static int STARS_NEW_ROUND;
    public static int SHOP_REFRESH_PRICE;

    public enum Phase { WAITING, BUY, BATTLE }

    private final String gameRoomId;
    private final Map<String, GamePlayer> players = new HashMap<>();
    private int currentRound;
    private final List<GameMessage> messageHistory;
    private String player1Name;
    private String player2Name;
    private String lastRoundLoser;
    private final Map<String, Boolean> battleReady = new ConcurrentHashMap<>();
    private String preferredPlayer;
    private String winner;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Phase currentPhase;

    private boolean inTransition = false;

    // Constructor por defecto
    public GameRoom() {
        this.gameRoomId = "";
        // Inicializar estructuras complejas para evitar null
        this.players.put(player1Name, new GamePlayer(player1Name));
        this.players.put(player2Name, new GamePlayer(player2Name));
        this.messageHistory = new ArrayList<>();
    }

    public GameRoom(String gameRoomId, String player1Name, String player2Name, ConfigPartida config) {
        this.gameRoomId = gameRoomId;

        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.players.put(player1Name, new GamePlayer(player1Name));
        this.players.put(player2Name, new GamePlayer(player2Name));
        this.currentRound = 0;
        this.messageHistory = new ArrayList<>();
        this.resetReadiness();
        preferredPlayer = new Random().nextBoolean() ? player1Name : player2Name;
        winner = null;
        this.currentPhase = Phase.WAITING;
        setConfig(config);
    }

    public static void setConfig(ConfigPartida config) {
        INITIAL_STARS = config.getEstrellasIni();
        INITIAL_LIFE = config.getVidaIni();
        DAMAGE_WIN = config.getDanyoVictoria();
        STARS_NEW_ROUND = config.getEstrellasRonda();
        SHOP_REFRESH_PRICE = config.getPrecioRefrescar(); 
    }
    
    public boolean isInTransition() {
    return inTransition;
    }

    public void setInTransition(boolean inTransition) {
        this.inTransition = inTransition;
    }

    public boolean isBuyingPhase() {
        return currentRound % 2 == 1;
    }

    public void setBuyPhase() {
        this.currentPhase = Phase.BUY;
    }

    public void setBattlePhase() {
        this.currentPhase = Phase.BATTLE;
    }

    public void nextRound() {
        currentRound++;
    }

    public Boolean canDoAction(PlayerAction action) {
        return true; // POR IMPLEMENTAR
    }

    public void setPlayerReady(String player) {
        battleReady.put(player, true);
    }

    public boolean bothPlayersReady() {
        System.out.println("\n\n\nEstado readiness: " + battleReady);
        return battleReady.values().stream().allMatch(Boolean::booleanValue);
    }

    public void resetReadiness() {
        battleReady.clear();
        battleReady.put(player1Name, false);
        battleReady.put(player2Name, false);
    }

    public void addMessage(String playerName, String message, ZonedDateTime timestamp) {
        messageHistory.add(new GameMessage(playerName, message, timestamp));
    }

    public List<GameMessage> getMessageHistory() {
        return messageHistory;
    }

    public boolean playerBuyUnit(String playerName, GameUnit unit) {
        return this.players.get(playerName).buyUnit(unit);
    }

    public boolean playerSellUnit(String playerName, GameUnit unit) {
        return this.players.get(playerName).sellUnit(unit);
    }

    public boolean playerBuyItem(String playerName, GameItem item) {
        return this.players.get(playerName).buyItem(item);
    }

    public boolean playerSellItem(String playerName, GameItem item) {
        return this.players.get(playerName).sellItem(item);
    }

    public boolean playerAssignItemToUnit(String playerName, int unitUnitId, GameItem item) {
        return this.players.get(playerName).assignItem(unitUnitId, item);
    }

    public Map<String, GamePlayer> getPlayers() {
        return players;
    }

    public void reduceLoserHealth() {
        this.players.get(lastRoundLoser).reduceHealth(DAMAGE_WIN);
    }

    public void refreshPlayerShop(String player, List<Heroe> heroes, List<Objeto> items, boolean cost) {
        this.players.get(player).refreshShop(heroes, items, cost);
    }

    public void fight() {

        GamePlayer player1 = players.get(player1Name);
        GamePlayer player2 = players.get(player2Name);

        GameUnit unit1 = null;
        GameUnit unit2 = null;

        // Bucle principal de combate
        while (true) {
            unit1 = getFirstValidUnit(player1);
            unit2 = getFirstValidUnit(player2);

            if (unit1 == null || unit2 == null) break;

            // Pelea entre unidades mientras vivas
           while (unit1.getHealth() > 0 && unit2.getHealth() > 0) {
                if (unit1.getSpeed() > unit2.getSpeed()) {
                    attack(unit1, unit2);
                    if (unit2.getHealth() > 0) attack(unit2, unit1);
                } else if (unit2.getSpeed() > unit1.getSpeed()) {
                    attack(unit2, unit1);
                    if (unit1.getHealth() > 0) attack(unit1, unit2);
                } else {
                    // Velocidad igual → se decide por preferencia
                    if (player1.getName() == preferredPlayer) {
                        attack(unit1, unit2);
                        if (unit2.getHealth() > 0) attack(unit2, unit1);
                    } else {
                        attack(unit2, unit1);
                        if (unit1.getHealth() > 0) attack(unit1, unit2);
                    }
                }
            }

            // Si una unidad muere, reemplaza por nula
            if (unit1.getHealth() <= 0) {
                replaceUnitWithNull(player1, unit1);
            }
            if (unit2.getHealth() <= 0) {
                replaceUnitWithNull(player2, unit2);
            }
        }

        if (allUnitsDead(player1)) {
            player1.setHealth(player1.getHealth() - GameRoom.DAMAGE_WIN);
        } else if (allUnitsDead(player2)) {
            player2.setHealth(player2.getHealth() - GameRoom.DAMAGE_WIN);
        }

        if (player1.getHealth() <= 0) {
            winner = player2.getName();
        }
        else if (player2.getHealth() <= 0) {
            winner = player1.getName();
        }

        player1.resetHealth();
        player2.resetHealth();
        
    }

    public GameUnit getLastValidUnit(GamePlayer player) {
        for (int i = player.getUnits().size() - 1; i >= 0; i--) {
            GameUnit unit = player.getUnits().get(i);
            if (unit != null && unit.getHealth() > 0) {
                return unit;
            }
        }
        return null;
    }

    public GameUnit getFirstValidUnit(GamePlayer player) {
        for (GameUnit unit : player.getUnits()) {
            if (unit != null && unit.getHealth() > 0) {
                return unit;
            }
        }
        return null;
    }

    private void replaceUnitWithNull(GamePlayer player, GameUnit deadUnit) {
        List<GameUnit> units = player.getUnits();
        for (int i = 0; i < units.size(); i++) {
            if (units.get(i) == deadUnit) {
                units.set(i, player.getNullUnit());
            }
        }
    }

    private boolean allUnitsDead(GamePlayer player) {
        for (GameUnit u : player.getUnits()) {
            if (u != null && u.getUnitID() > 0 && u.getHealth() > 0) return false;
        }
        return true;
    }

    private void attack(GameUnit attacker, GameUnit defender) {
        int damage = Math.max(attacker.getDamage() - defender.getArmor(), 1);
        defender.setHealth(defender.getHealth() - damage);
    }

    public String getWinner() {
        return winner;
    }

    public void newRoundStars() {
        for (GamePlayer p : players.values()) {
            p.addStars(GameRoom.STARS_NEW_ROUND);
        }
    }

}
