package es.ucm.fdi.iw.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Data;

@Data
public class GamePlayer {

    public static final int MAX_ITEMS = 6;
    public static final int MAX_UNITS = 4;

    private String name;
    private int health;
    private int stars;
    private Set<GameItem> inventory;
    private List<GameUnit> units;
    private GameShop shop;

    public GamePlayer() {
        this.inventory =  new HashSet<>();
        this.units = new ArrayList<>();
        this.shop = new GameShop();
    }

    public GamePlayer(String name) {
        this.name = name;
        health = GameRoom.INITIAL_LIFE;
        stars = GameRoom.INITIAL_STARS;
        inventory = new HashSet<>();
        this.shop = new GameShop();
        resetUnits();
    }

    public GameUnit getNullUnit() {
        return GameUnit.getNullUnit();
    }

    public void reduceHealth(int amount) {
        health -= amount;
        if (health < 0) health = 0;
    }

    public GameUnit getDefaultUnit() {
        return GameUnit.getDefaultUnit();
    }

    public void resetUnits() {
        units = new ArrayList<>();
        for (int i = 0; i < MAX_UNITS; i++) {
            units.add(getNullUnit());
        }
    }

    public boolean buyUnit(GameUnit unit) {
        if (stars >= unit.getPrice()) {
            int index = -1;
            for (int i = 0; i < units.size(); i++) {
                if (units.get(i).getImage() == null || units.get(i).getImage().isEmpty()) {
                    index = i;
                    break;
                }
            }

            if (index != -1) {
                units.set(index, unit);
                stars -= unit.getPrice();
                System.out.println(name + " compró una unidad: " + unit.getName());
                return true;
            } else {
                System.out.println(name + " no puede tener más unidades.");
                return false;
            }
        }

        System.out.println(name + " no tiene suficientes monedas.");
        return false;
    }

    public  boolean sellUnit(GameUnit soldUnit) {
        System.out.println("Vendiendo unidad: " + soldUnit);
        for (int i = 0; i < units.size(); i++) {
            GameUnit u = units.get(i);
            if (u.getUnitID() == soldUnit.getUnitID()) {
                stars += u.getPrice();
                for (GameItem item : u.getItems()) {
                    if (item != null) {
                        inventory.add(item);
                    }
                }
                units.set(i, getNullUnit());
                System.out.println("Se ha vendido la unidad " + soldUnit.getName());
                return true;
            }
        }
        return false;
    }

    public boolean buyItem(GameItem item) {

        // FALTA COMPROBAR QUE ESTÁ EN LA TIENDA
        if (stars >= item.getPrice() && inventory.size() < MAX_ITEMS) {
            stars -= item.getPrice();
            inventory.add(item);
            System.out.println(name + " compró un objeto: " + item.getName());
            return true;
        }
        return false;
    }

    public boolean sellItem(GameItem soldItem) {
        for (GameItem item : new HashSet<>(inventory)) {
            if (item.getId() == soldItem.getId()) {
                inventory.remove(item);
                stars += item.getPrice();
                System.out.println(name + " vendió un objeto: " + item.getName());
                return true;
            }
        }
        return false;
    }

    public void removeFromInventory(GameItem removedItem) {
        inventory.removeIf(item -> item.getId() == removedItem.getId());
    }

    public boolean assignItem(int unitUnitId, GameItem item) {

        for (GameItem it : getInventory()) {
            if (item.getId() == it.getId()) {
                for(GameUnit u : units) {
                    if (u.getUnitID() == unitUnitId) {
                        u.addItem(item);
                        removeFromInventory(item);
                        return true;                    
                    }
                }
            }
        }
        return false;
    }

    public void replaceUnit(GameUnit oldUnit, GameUnit newUnit) {
        for (int i = 0; i < units.size(); i++) {
            if (units.get(i) == oldUnit) {
                units.set(i, newUnit);
                return;
            }
        }
    }

    public void refreshShop(List<Heroe> heroes, List<Objeto> items, boolean cost) {

        if ((stars >= GameRoom.SHOP_REFRESH_PRICE) || !cost) {
            this.shop.refresh(heroes, items);
            if (cost) stars -= GameRoom.SHOP_REFRESH_PRICE;
        }

    }

    public void resetHealth() {
        for (GameUnit u : units) {
            u.resetHealth();
        }
    }

    public void addStars(int starsNewRound) {
        stars += starsNewRound;
    }
    
}

