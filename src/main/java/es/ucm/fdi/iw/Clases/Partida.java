package es.ucm.fdi.iw.Clases;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Partida {
    private String id;
    private int duracionMin;
    List<Jugador> jugadores;

    public Partida(String id, int duracionMin, List<Jugador> jugadores){
        this.id = id;
        this.duracionMin = duracionMin;
        this.jugadores = jugadores;
    }
}
