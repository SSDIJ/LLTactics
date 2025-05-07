package es.ucm.fdi.iw.model;

import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameItem {
    private int armor;
    private int damage;
    private String description;
    private int id;
    private String imageUrl;
    private String name;
    private int price;
    private int unitUnitId;
    private int velocity;
    private int life;
}
