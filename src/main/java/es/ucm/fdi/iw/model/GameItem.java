package es.ucm.fdi.iw.model;

public class GameItem {

    private int armor;
    private int damage;
    private String description;
    private String id;
    private String imageUrl;
    private String name;
    private double price;
    private String unit;
    private int velocity;
    private int life;

    public GameItem() {
        // Constructor vacío necesario para deserialización
    }

    public GameItem(int armor, int damage, String description, String id, String imageUrl, String name, double price, String unit, int velocity, int life) {
        this.armor = armor;
        this.damage = damage;
        this.description = description;
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.velocity = velocity;
        this.life = life;
    }

    // Getters y setters

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
