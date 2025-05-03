package es.ucm.fdi.iw.model;

import java.util.Arrays;

public class GameUnit {
    private static int nextId = 1;
    private static final int MAX_ITEMS = 2;

    private int armor;
    private int damage;
    private String description;
    private String faction;
    private int id;
    private String image;
    private String name;
    private int price;
    private int speed;
    private int health;
    private int maxHealth;
    private int unitID;
    private GameItem[] items;

    public GameUnit() {}

    public GameUnit(int armor, int damage, String description, String faction, int id,
                    String image, String name, int price, int speed, int health) {
        this.armor = armor;
        this.damage = damage;
        this.description = description;
        this.faction = faction;
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.speed = speed;
        this.health = health;
        this.maxHealth = health;
        this.unitID = (name != null && !name.isEmpty()) ? nextId++ : -1;
        this.items = new GameItem[MAX_ITEMS];
        Arrays.fill(this.items, null);
    }

    public static GameUnit fromUnit(GameUnit otherUnit) {
        GameUnit newUnit = new GameUnit(
            otherUnit.armor,
            otherUnit.damage,
            otherUnit.description,
            otherUnit.faction,
            otherUnit.id,
            otherUnit.image,
            otherUnit.name,
            otherUnit.price,
            otherUnit.speed,
            otherUnit.health
        );
        newUnit.unitID = otherUnit.unitID;
        newUnit.items = Arrays.copyOf(otherUnit.items, otherUnit.items.length);
        return newUnit;
    }

    public double getHealthPercentage() {
        return (health / (double) maxHealth) * 100;
    }

    public boolean hasFullInventory() {
        for (GameItem item : items) {
            if (item == null) return false;
        }
        return true;
    }

    public boolean addItem(GameItem item) {
        if (!hasFullInventory()) {
            for (int i = 0; i < MAX_ITEMS; i++) {
                if (items[i] == null) {
                    items[i] = item;
                    upgradeStats(item);
                    System.out.println("Item has been added");
                    return true;
                }
            }
        }
        return false;
    }

    public void upgradeStats(GameItem item) {
        this.health += item.getLife();
        this.maxHealth += item.getLife();
        this.damage += item.getDamage();
        this.speed += item.getVelocity();
        this.armor += item.getArmor();
    }

    public void degradeStats(GameItem item) {
        this.health -= item.getLife();
        this.maxHealth -= item.getLife();
        this.damage -= item.getDamage();
        this.speed -= item.getVelocity();
        this.armor -= item.getArmor();
    }

    public static GameUnit getNullUnit() {
        GameUnit nullUnit = new GameUnit();
        nullUnit.setArmor(0);
        nullUnit.setDamage(0);
        nullUnit.setDescription(null);
        nullUnit.setFaction(null);
        nullUnit.setId(0);
        nullUnit.setImage(null);
        nullUnit.setName(null);
        nullUnit.setPrice(0);
        nullUnit.setSpeed(0);
        nullUnit.setHealth(0);
        nullUnit.setMaxHealth(0);
        nullUnit.setUnitID(0);
        nullUnit.setItems(new GameItem[nullUnit.getMAX_ITEMS()]);
        return nullUnit;
    }

    public static GameUnit getDefaultUnit() {
        return new GameUnit(
            30, // armor
            20, // damage
            "Defiende sus tierras.", // description
            null, // faction (puedes cambiarlo si deseas establecer una facción)
            0, // id
            "/img/units/humans/0. Campesino/peasant.png", // image
            "Campesino", // name
            0, // price
            10, // speed
            150 // health (también se usará como maxHealth)
        );
    }
    


    // Getters and Setters

    public int getArmor() { return armor; }
    public void setArmor(int armor) { this.armor = armor; }

    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFaction() { return faction; }
    public void setFaction(String faction) { this.faction = faction; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }

    public int getMaxHealth() { return maxHealth; }
    public void setMaxHealth(int maxHealth) { this.maxHealth = maxHealth; }

    public int getUnitID() { return unitID; }
    public void setUnitID(int unitID) { this.unitID = unitID; }

    public GameItem[] getItems() { return items; }
    public void setItems(GameItem[] items) { this.items = items; }

    public int getMAX_ITEMS() { return MAX_ITEMS; }
}
