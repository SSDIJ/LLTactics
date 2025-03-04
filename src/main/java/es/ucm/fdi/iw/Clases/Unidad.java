package es.ucm.fdi.iw.Clases;
import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor  
@DiscriminatorValue("unidad")
public class Unidad extends Heroe{
    private Long idUnidad;
    private Double vidaAct;
    private Double dañoAct;
    private Double velocidadAct;
    private Double armaduraAct;

    @OneToMany(mappedBy = "unidad")
    private List<Objeto> objetos;

    public Unidad(String nombre, String imagen, int vida, int armadura, int daño, int velocidad, String descripcion, int faccion, int precio, List<Objeto> objetos) {
        super(nombre, imagen, vida, armadura, daño, velocidad, descripcion, faccion, precio);
        this.dañoAct = Double.valueOf(daño);
        this.vidaAct = Double.valueOf(vida);
        this.velocidadAct = Double.valueOf(velocidad);
        this.objetos = objetos;
    }
}

