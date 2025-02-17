package es.ucm.fdi.iw.Clases;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Heroe {
    private String nombre;
    private String imagen;
    private int vida;
    private int armadura;
    private int daño;
    private int velocidad;
    private String descripcion;
    private int faccion;  // 0 = humanos, 1 = dragones, 2 = trolls, 3 = no muertos, 4 = criaturas legendarias

    public Heroe(String nombre, String imagen, int vida, int armadura, int daño, int velocidad, String descripcion, int faccion) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.vida = vida;
        this.armadura = armadura;
        this.daño = daño;
        this.velocidad = velocidad;
        this.descripcion = descripcion;
        this.faccion = faccion;
    }
}
