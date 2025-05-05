package es.ucm.fdi.iw.model;

import lombok.Data;
@Data
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
}
