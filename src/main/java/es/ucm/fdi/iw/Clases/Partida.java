package es.ucm.fdi.iw.Clases;

import java.util.List;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Partida {
    private String id;
    private int duracionMin;

    @OneToMany
    @JoinColumn(name="partida_id")
    private List<Jugador> jugadores;

    private Jugador ganador;

    @OneToMany
    @JoinColumn(name="partida_id")
    private List<Mensaje> chat;
    
    private boolean terminada;

    public Partida(String id, int duracionMin, List<Jugador> jugadores, List<Mensaje> chat) {
        this.id = id;
        this.duracionMin = duracionMin;
        this.jugadores = jugadores;
        this.chat = chat;
        this.terminada = false;
    }
}
