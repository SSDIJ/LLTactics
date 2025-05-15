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

    public static GameItem fromObjeto(Objeto objeto) {
    return new GameItem(
        objeto.getArmadura(),                        // armor
        objeto.getDaño(),                            // damage
        objeto.getDescripcion(),                     // description
        objeto.getIdObjeto().intValue(),             // id
        objeto.getImagen(),                          // imageUrl
        objeto.getNombre(),                          // name
        objeto.getPrecio(),                          // price
        -1,                                          // unitId
        objeto.getVelocidad(),                       // velocity
        objeto.getVida()                             // life
    );
}
}
