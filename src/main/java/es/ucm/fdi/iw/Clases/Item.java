package es.ucm.fdi.iw.Clases;

public class Item {
    private String imageUrl;
    private String name;
    private String description;
    private int price;

    public Item(String imageUrl, String name, String description, int price) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.description = description;
        this.price = price;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setName(int price) {
        this.price = price;
    }
}
