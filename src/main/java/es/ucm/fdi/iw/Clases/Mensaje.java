package es.ucm.fdi.iw.Clases;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Partida partida;
    
    @ManyToOne
    private Jugador autor;
    private String contenido;
    private LocalDateTime fechaHora;

    public Mensaje(Jugador autor, String contenido) {
        this.autor = autor;
        this.contenido = contenido;
        this.fechaHora = LocalDateTime.now(); // Fecha y hora actual
    }

    @Override
    public String toString() {
        return "[" + fechaHora + "] " + autor + ": " + contenido;
    }
}
