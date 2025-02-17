package es.ucm.fdi.iw.Clases;

import java.util.ArrayList;
import java.util.List;
import es.ucm.fdi.iw.Clases.Heroe;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Jugador {
    private String nombre;
    private String imagen;
    private int indiceRanking;
    private int puntuacion;
    private int partidasGanadas;
    private int partidasPerdidas;
    private int faccionFavorita;
    private List<Heroe> masJugados;
    private boolean baneado;

    public Jugador(String nombre, String imagen, int indiceRanking, int puntuacion, int partidasGanadas, int partidasPerdidas, int faccionFavorita, List<Heroe> masJugados) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.indiceRanking = indiceRanking;
        this.puntuacion = puntuacion;
        this.partidasGanadas = partidasGanadas;
        this.partidasPerdidas = partidasPerdidas;
        this.faccionFavorita = faccionFavorita;
        this.masJugados = masJugados;
        this.baneado = false;
    }
}
