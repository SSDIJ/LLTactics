package es.ucm.fdi.iw.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GameBattleResult {
    private String winner;
    private Map<String, List<GameUnit>> units; // key: player name, value: list of their units

    public GameBattleResult() {}

    public GameBattleResult(String winner, Map<String, List<GameUnit>> units) {
        this.winner = winner;
        this.units = units;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Map<String, List<GameUnit>> getUnits() {
        return units;
    }

    public void setUnits(Map<String, List<GameUnit>> units) {
        this.units = units;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameBattleResult)) return false;
        GameBattleResult that = (GameBattleResult) o;
        return Objects.equals(winner, that.winner) &&
               Objects.equals(units, that.units);
    }

    @Override
    public int hashCode() {
        return Objects.hash(winner, units);
    }

    @Override
    public String toString() {
        return "BattleResult{" +
                "winner='" + winner + '\'' +
                ", units=" + units +
                '}';
    }
}
