package es.ucm.fdi.iw.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.iw.model.Heroe;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Partida partida;
    private String nombre;
    private String imagen;
    /*
    private int indiceRanking;
    private int puntuacion;
    private int partidasGanadas;
    private int partidasPerdidas;
    private int faccionFavorita;  */
    private Long id_user;
    
    @OneToMany
    private List<Heroe> masJugados = new ArrayList<>();
    
    private boolean baneado;
    private String razonBaneo;
    private LocalDateTime fechaBaneo;

    public Jugador(){
    }

    public Jugador(String nombre) {
        this.nombre = nombre;
    }  

    public Jugador(String nombre, String imagen, int indiceRanking, int puntuacion, int partidasGanadas, int partidasPerdidas, int faccionFavorita, List<Heroe> masJugados, boolean baneado, String razon, Long id_user) {
        this.nombre = nombre;
        this.imagen = imagen;
        /*
        this.indiceRanking = indiceRanking;
        this.puntuacion = puntuacion;
        this.partidasGanadas = partidasGanadas;
        this.partidasPerdidas = partidasPerdidas;
        this.faccionFavorita = faccionFavorita;  */
        this.masJugados = masJugados;
        this.baneado = baneado;
        this.razonBaneo = razon;
        this.fechaBaneo = LocalDateTime.now();
        this.id_user=id_user;
    }
}
