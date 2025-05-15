package es.ucm.fdi.iw.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Data;

import java.util.Set;

import java.util.ArrayList;
import java.time.ZonedDateTime;

@Data
public class GameRoom {

    public static final int SHOP_TIME = 10;
    public static final int INITIAL_STARS = 100;
    public static final int INITIAL_LIFE = 2;
    public static final int DAMAGE_WIN = 1;

    private final String gameRoomId;
    private final Map<String, GamePlayer> players = new HashMap<>();
    private int currentRound;
    private final List<GameMessage> messageHistory;
    private String player1Name;
    private String player2Name;
    private String lastRoundLoser;
    private final Map<String, Boolean> battleReady = new ConcurrentHashMap<>();

    // Constructor por defecto
    public GameRoom() {
        this.gameRoomId = "";
        // Inicializar estructuras complejas para evitar null
        this.players.put(player1Name, new GamePlayer(player1Name));
        this.players.put(player2Name, new GamePlayer(player2Name));
        this.messageHistory = new ArrayList<>();
        this.playerResults = new HashMap<>();
    }

    public GameRoom(String gameRoomId, String player1Name, String player2Name) {
        this.gameRoomId = gameRoomId;

        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.players.put(player1Name, new GamePlayer(player1Name));
        this.players.put(player2Name, new GamePlayer(player2Name));
        this.currentRound = 0;
        this.messageHistory = new ArrayList<>();
        this.resetReadiness();
    }

    public enum Phase { WAITING, BUY, BATTLE }
    private Phase currentPhase = Phase.WAITING;

    public boolean isBuyingPhase() {
        return currentRound % 2 == 1;
    }

    public void nextRound() {
        currentRound++;
        
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

    private Map<String, GameBattleResult> playerResults = new ConcurrentHashMap<>();

    public void setPlayerResult(String playerName, GameBattleResult result) {
        playerResults.put(playerName, result);
    }

    public GameBattleResult getPlayerResult(String playerName) {
        return playerResults.get(playerName);
    }

    public void reduceLoserHealth() {
        this.players.get(lastRoundLoser).reduceHealth(DAMAGE_WIN);
    }

    public String getWinner() {
        Iterator<GamePlayer> iterator = players.values().iterator();
        GamePlayer player1 = iterator.next();
        GamePlayer player2 = iterator.next();

        System.out.println("\n\n");
        System.out.println(player1.getName());
        System.out.println(player1.getHealth());
        System.out.println(player2.getName());
        System.out.println(player2.getHealth());
        System.out.println("\n\n");
    
        if (player1.getHealth() <= 0) {
            return player2.getName();
        } else if (player2.getHealth() <= 0) {
            return player1.getName();
        }
    
        return null; // Ningún jugador ha perdido aún
    }
    
    public boolean resultsMatch(GameBattleResult r1, GameBattleResult r2) {

        if (!Objects.equals(r1.getWinner(), r2.getWinner())) return false;
    
        Set<String> playerNames = r1.getUnits().keySet();
        if (!playerNames.equals(r2.getUnits().keySet())) return false;
        
        /* 
        for (String player : playerNames) {
            List<GameUnit> units1 = r1.getUnits().get(player);
            List<GameUnit> units2 = r2.getUnits().get(player);
    
            if (!unitsMatch(units1, units2)) return false;
        }
        */

        lastRoundLoser = r1.getWinner().equals(this.player1Name) ? this.player2Name : this.player1Name;

        return true;
    }

    private boolean unitsMatch(List<GameUnit> units1, List<GameUnit> units2) {
        if (units1.size() != units2.size()) return false;
    
        for (int i = 0; i < units1.size(); i++) {
            GameUnit u1 = units1.get(i);
            GameUnit u2 = units2.get(i);
    
            if (!unitEquals(u1, u2)) return false;
        }
    
        return true;
    }
    
    private boolean unitEquals(GameUnit u1, GameUnit u2) {
        return u1.getHealth() == u2.getHealth()
            && u1.getMaxHealth() == u2.getMaxHealth()
            && u1.getArmor() == u2.getArmor()
            && u1.getDamage() == u2.getDamage()
            && u1.getSpeed() == u2.getSpeed()
            && u1.getId() == u2.getId();
    }

    public void fight() {
        System.out.println("GameRoom: FIGHTING"); // TODO: ELIMINAR

        GamePlayer player1 = players.get(player1Name);
        GamePlayer player2 = players.get(player2Name);

        GameUnit unit1 = null;
        GameUnit unit2 = null;

        // Bucle principal de combate
        while (true) {
            unit1 = getLastValidUnit(player1); // TODO: revisar
            unit2 = getFirstValidUnit(player2); // TODO: revisar

            if (unit1 == null || unit2 == null) break;

            // Pelea entre unidades mientras vivas
            while (unit1.getHealth() > 0 && unit2.getHealth() > 0) {
                if (unit1.getSpeed() >= unit2.getSpeed()) {
                    attack(unit1, unit2);
                    if (unit2.getHealth() > 0) attack(unit2, unit1);
                } else {
                    attack(unit2, unit1);
                    if (unit1.getHealth() > 0) attack(unit1, unit2);
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

        // Determina el ganador y actualiza la vida
        String winner = null;
        if (allUnitsDead(player1)) {
            player1.setHealth(player1.getHealth() - 5);
            winner = player2.getName();
        } else if (allUnitsDead(player2)) {
            player2.setHealth(player2.getHealth() - 5);
            winner = player1.getName();
        }

        // Prepara el resultado y actualiza el estado de la sala
        Map<String, List<GameUnit>> units = Map.of(
            player1.getName(), player1.getUnits(),
            player2.getName(), player2.getUnits()
        );
        GameBattleResult result = new GameBattleResult(winner, units);
        setPlayerResult(player1.getName(), result);
        setPlayerResult(player2.getName(), result);
        lastRoundLoser = (winner != null && winner.equals(player1.getName())) ? player2.getName() : player1.getName();
        
        System.out.println("GameRoom: GANADOR " + winner); // TODO: ELIMINAR
        if(getLastValidUnit(player1) != null)
            System.out.println("GameRoom: " + player1.getName() + " unit: " + getLastValidUnit(player1)); // TODO: ELIMINAR
        if(getLastValidUnit(player2) != null)
            System.out.println("GameRoom: " + player2.getName() + " unit: " + getLastValidUnit(player2)); // TODO: ELIMINAR
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

}
