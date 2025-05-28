package es.ucm.fdi.iw.model;
import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor  
@DiscriminatorValue("unidad")
public class Unidad extends Heroe{
    
    private int vidaAct;
    private int dañoAct;
    private int velocidadAct;
    private int armaduraAct;

    @OneToMany(mappedBy = "unidad")
    private List<Objeto> objetos;

    public Unidad(String nombre, String imagen, int vida, int armadura, int daño, int velocidad, String descripcion, int faccion, int precio, double probabilidad, List<Objeto> objetos, double probabilidad_critico) {
        super(nombre, imagen, vida, armadura, daño, velocidad, descripcion, faccion, precio, probabilidad, probabilidad_critico);
        this.dañoAct = daño;
        this.vidaAct = vida;
        this.velocidadAct = velocidad;
        this.armaduraAct = armadura;
        this.objetos = objetos;
    }
}

