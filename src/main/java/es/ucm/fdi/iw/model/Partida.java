package es.ucm.fdi.iw.model;

import java.util.ArrayList;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Data
public class Partida {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private Long idPartida;

    @ManyToOne
    @JoinColumn(name = "jid1", referencedColumnName = "id", nullable = false)
    private User jugador1;

    @ManyToOne
    @JoinColumn(name = "jid2", referencedColumnName = "id", nullable = false)
    private User jugador2;

    private ArrayList<Message> mensajes = new ArrayList<>();

    private int duracionMin;

    public Partida() {
        // Constructor vacío necesario para JPA
    }

    public Partida(User jugador1, User jugador2, int duracionMin) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.duracionMin = duracionMin;
    }
}