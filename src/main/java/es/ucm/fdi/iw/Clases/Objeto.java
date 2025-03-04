package es.ucm.fdi.iw.Clases;

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
    private String descripcion;
    private int precio;

    @ManyToOne
    @JoinColumn(name = "idUnidad")
    private Unidad unidad;

    public Objeto(String imagen, String nombre, String descripcion, int precio) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }
}
