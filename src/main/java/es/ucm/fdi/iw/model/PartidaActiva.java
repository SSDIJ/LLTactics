package es.ucm.fdi.iw.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Column;
import lombok.Data;

@Entity
@Data
public class PartidaActiva {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private Long idPartida;    //ID interna de la BD

    @ManyToOne
    @JoinColumn(name = "jid1", referencedColumnName = "id", nullable = false)
    private User jugador1;

    @ManyToOne
    @JoinColumn(name = "jid2", referencedColumnName = "id", nullable = false)
    private User jugador2;

    private String gameRoomId; // ID de la sala de juego (tambien aparece en el JSON del estado, pero por comodidad lo añado aquí)

    @Column(columnDefinition = "TEXT") // Permite almacenar JSON largos
    private String estado; // Contendrá el JSON del estado de la partida

    

    public PartidaActiva() {
        // Constructor vacío necesario para JPA
    }

    public PartidaActiva(User jugador1, User jugador2, String estado, String gameRoomId) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.estado = estado;
        this.gameRoomId = gameRoomId;
    }
}