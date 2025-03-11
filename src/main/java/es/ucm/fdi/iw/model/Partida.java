package es.ucm.fdi.iw.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Partida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPartida;
    private String nombre;
    @OneToMany(mappedBy = "partida")
    private ArrayList<Mensaje> mensajes;
    private int duarcionMin;

    public Partida(String nombre) {
        this.nombre = nombre;
    }
}
