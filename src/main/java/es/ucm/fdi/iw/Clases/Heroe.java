package es.ucm.fdi.iw.Clases;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor  
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
public class Heroe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHeroe;
    private String nombre;
    private String imagen;
    private int vida;
    private int armadura;
    private int daño;
    private int velocidad;
    private String descripcion;
    private int faccion;  // 0 = humanos, 1 = dragones, 2 = trolls, 3 = no muertos, 4 = criaturas legendarias
    private int precio;

    public Heroe(String nombre, String imagen, int vida, int armadura, int daño, int velocidad, String descripcion, int faccion, int precio) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.vida = vida;
        this.armadura = armadura;
        this.daño = daño;
        this.velocidad = velocidad;
        this.descripcion = descripcion;
        this.faccion = faccion;
        this.precio = precio;
    }

    public int getPrecio() {
        return precio;
    }
}
