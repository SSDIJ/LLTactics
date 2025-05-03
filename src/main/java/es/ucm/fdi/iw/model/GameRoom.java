package es.ucm.fdi.iw.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.time.ZonedDateTime;

public class GameRoom {

    public static final int SHOP_TIME = 20;
    public static final int INITIAL_STARS = 100;
    public static final int INITIAL_LIFE = 100;
    public static final int DAMAGE_WIN = 5;

    private final String gameRoomId;
    private final Map<String, GamePlayer> players = new HashMap<>();
    private int currentRound;
    private final List<GameMessage> messageHistory;

    public GameRoom(String gameRoomId, String player1Name, String player2Name) {
        this.gameRoomId = gameRoomId;
        this.players.put(player1Name, new GamePlayer(player1Name));
        this.players.put(player2Name, new GamePlayer(player2Name));
        this.currentRound = 1;
        this.messageHistory = new ArrayList<>();
    }

    public enum Phase { WAITING, BUY, BATTLE }
    private Phase currentPhase = Phase.WAITING;
    private boolean inTransition = false;

    public synchronized boolean isInTransition() {
        return inTransition;
    }
    public synchronized void setInTransition(boolean val) {
        inTransition = val;
    }

    public synchronized Phase getCurrentPhase() {
        return currentPhase;
    }
    public synchronized void setCurrentPhase(Phase phase) {
        this.currentPhase = phase;
    }


    public boolean isBuyingPhase() {
        return currentRound % 2 == 1;
    }

    public void nextRound() {
        currentRound++;
    }

    public String getGameRoomId() {
        return gameRoomId;
    }

    public int getCurrentRound() {
        return this.currentRound;
    }

    private final Map<String, Boolean> battleReady = new ConcurrentHashMap<>();

    public void setPlayerReady(String player) {
        battleReady.put(player, true);
    }

    public boolean bothPlayersReady() {
        return battleReady.values().stream().allMatch(Boolean::booleanValue);
    }

    public void resetReadiness() {
        battleReady.clear();
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

    public boolean playerAssignItemToUnit(String playerName, int unitIndex, GameItem item) {
        return this.players.get(playerName).assignItem(unitIndex, item);
    }

    public Map<String, GamePlayer> getPlayers() {
        return players;
    }

    public void fight() {
        List<GamePlayer> playerList = new ArrayList<>(players.values());
        Collections.shuffle(playerList);

        GamePlayer player1 = playerList.get(0);
        GamePlayer player2 = playerList.get(1);

        GameUnit unit1 = getLastValidUnit(player1);
        GameUnit unit2 = getFirstValidUnit(player2);
    
        if (unit1 == null) {
            player1.buyUnit(player1.getDefaultUnit());
            unit1 = getLastValidUnit(player1);
        }
        if (unit2 == null) {
            player2.buyUnit(player2.getDefaultUnit());
            unit2 = getFirstValidUnit(player2);
        }
    
        while (unit1 != null && unit2 != null) {
            // Fight between unit1 and unit2
            while (unit1.getHealth() > 0 && unit2.getHealth() > 0) {
                if (unit1.getSpeed() >= unit2.getSpeed()) {
                    attack(unit1, unit2);
                    if (unit2.getHealth() > 0) {
                        attack(unit2, unit1);
                    }
                } else {
                    attack(unit2, unit1);
                    if (unit1.getHealth() > 0) {
                        attack(unit1, unit2);
                    }
                }
            }
    
            if (unit1.getHealth() <= 0) {
                player1.replaceUnit(unit1, player1.getNullUnit());
            }
            if (unit2.getHealth() <= 0) {
                player2.replaceUnit(unit2, player2.getNullUnit());
            }
    
            unit1 = getLastValidUnit(player1);
            unit2 = getFirstValidUnit(player2);
        }
    
        // Determine the winner
        boolean player1Defeated = player1.getUnits().stream().allMatch(u -> u.getHealth() <= 0 || u.getUnitID() == -1);
        boolean player2Defeated = player2.getUnits().stream().allMatch(u -> u.getHealth() <= 0 || u.getUnitID() == -1);
    
        if (player1Defeated) {
            player1.reduceHealth(DAMAGE_WIN);
        } else if (player2Defeated) {
            player2.reduceHealth(DAMAGE_WIN);
        }
    }
    
    private GameUnit getLastValidUnit(GamePlayer player) {
        List<GameUnit> units = player.getUnits();
        for (int i = units.size() - 1; i >= 0; i--) {
            GameUnit unit = units.get(i);
            if (unit != null && unit.getUnitID() != -1 && unit.getHealth() > 0) {
                return unit;
            }
        }
        return null;
    }
    
    private GameUnit getFirstValidUnit(GamePlayer player) {
        for (GameUnit unit : player.getUnits()) {
            if (unit != null && unit.getUnitID() != -1 && unit.getHealth() > 0) {
                return unit;
            }
        }
        return null;
    }
    
    private void attack(GameUnit attacker, GameUnit defender) {
        int rawDamage = attacker.getDamage();
        int armor = defender.getArmor();
        int damage = Math.max(rawDamage - armor, 1);
        defender.setHealth(defender.getHealth() - damage);
    }

}
