package es.ucm.fdi.iw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import es.ucm.fdi.iw.Clases.Jugador;
import es.ucm.fdi.iw.Clases.Heroe;
import es.ucm.fdi.iw.Clases.Partida;
import java.util.ArrayList;
import java.util.List;

public class AdminViewController {
    List<Jugador> jugadores = new ArrayList<>();
    Partida prueba = new Partida("1", 30, jugadores );

    
}
