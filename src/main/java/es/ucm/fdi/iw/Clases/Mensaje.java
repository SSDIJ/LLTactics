package es.ucm.fdi.iw.Clases;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mensaje {
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
