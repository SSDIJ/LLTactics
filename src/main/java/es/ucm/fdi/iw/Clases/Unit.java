package es.ucm.fdi.iw.Clases;
import java.util.List;

public class Unit {
    private String name;
    private Integer healthPercentage;
    private Integer healthValue;
    private String imagePath;
    private List<Item> objects;

    public Unit(String name, Integer healthPercentage, Integer healthValue, String imagePath, List<Item> objects) {
        this.name = name;
        this.healthPercentage = healthPercentage;
        this.healthValue = healthValue;
        this.imagePath = imagePath;
        this.objects = objects;
    }

    // Getters y Setters
    public String getName() { return name; }
    public Integer getHealthPercentage() { return healthPercentage; }
    public Integer getHealthValue() { return healthValue; }
    public String getImagePath() { return imagePath; }
    public List<Item> getObjects() { return objects; }
}

