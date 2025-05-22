package es.ucm.fdi.iw.model;

import java.time.LocalDateTime;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Data
public class Partida {
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

    @Column(nullable = true)
    private String gameRoomId; // ID de la sala de juego (tambien aparece en el JSON del estado, pero por comodidad lo añado aquí)
    
    @Column(nullable = true)
    private boolean enCurso; // Indica si la partida ha terminado o no
    @Column(nullable = true)
    private int ganador;   // 0 si no hay ganador, 1 si gana jugador1, 2 si gana jugador2
    @Column(nullable = true)
    private int perdedor;  // 0 si no hay perdedor, 1 si pierde jugador1, 2 si pierde jugador2

    @Column(nullable = true)
    private LocalDateTime inicio; 

    @Column(nullable = true)
    private LocalDateTime fin; 

  
    @Column(columnDefinition = "TEXT", nullable = true) // Permite almacenar JSON largos
    private String estado; // Contendrá el JSON del estado de la partida

    
    

    public Partida() {
        // Constructor vacío necesario para JPA
    }

    public Partida(User jugador1, User jugador2, String estado, String gameRoomId) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.estado = estado;
        this.gameRoomId = gameRoomId;
        this.enCurso = true; // Inicialmente la partida está en curso
        this.inicio=LocalDateTime.now();
    }
}