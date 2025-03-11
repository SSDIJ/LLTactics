package es.ucm.fdi.iw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor  
public class Objeto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idObjeto;
    private String imagen;
    private String nombre;
    private int vida;
    private int armadura;
    private int daño;
    private int velocidad;
    private String descripcion;
    private int precio;

    @ManyToOne
    @JoinColumn(name = "idUnidad")
    private Unidad unidad;

    public Objeto(String imagen, String nombre, int vida, int armadura, int daño, int velocidad, String descripcion, int precio) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.vida = vida;
        this.armadura = armadura;
        this.daño = daño;
        this.velocidad = velocidad;
        this.descripcion = descripcion;
        this.precio = precio;
    }
}
