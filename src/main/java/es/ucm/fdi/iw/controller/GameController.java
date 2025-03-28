package es.ucm.fdi.iw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.ucm.fdi.iw.model.Heroe;
import es.ucm.fdi.iw.model.Jugador;
import es.ucm.fdi.iw.model.Mensaje;
import es.ucm.fdi.iw.model.Objeto;
import es.ucm.fdi.iw.model.Unidad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class GameController {

    @GetMapping("/game")
    public String showGamePage(Model model) {

        List<Objeto> playerObjects = List.of(
                new Objeto(null, "", 0, 0, 0, 0,"", 1),
                new Objeto(null, "", 0, 0, 0, 0,"", 1),
                new Objeto(null, "", 0, 0, 0, 0,"", 1),
                new Objeto(null, "", 0, 0, 0, 0,"", 1),
                new Objeto(null, "", 0, 0, 0, 0,"", 1),
                new Objeto(null, "", 0, 0, 0, 0,"", 1));
        model.addAttribute("playerObjects", playerObjects);

        List<Objeto> shopItems = List.of(
                new Objeto("/img/items/potion-red.png", "Poción Roja", 0, 0, 0, 0,"", 1),
                new Objeto("/img/items/flame-sword.png", "Espada de Hierro", 0, 0, 0, 0,"", 5));
        model.addAttribute("shopItems", shopItems);

        List<Heroe> shopUnits = List.of(
                new Heroe("Dragón", "/img/units/dragons/4. DGris/burner.png", 0, 0, 0, 0, null, 0, 2, 0),
                new Heroe("Esqueleto", "/img/units/humans/5. Mago/white-mage.png", 0, 0, 0, 0, null, 0, 4, 0),
                new Heroe("Mago", "/img/units/humans/5. Mago/white-mage.png", 0, 0, 0, 0, null, 0, 1, 0));
        model.addAttribute("shopUnits", shopUnits);

        List<Unidad> unitsP1 = new ArrayList<>(Arrays.asList(
                new Unidad("", null, 1, 0, 0, 0, "", 0, 0, 0, Arrays.asList(null, null)),
                new Unidad("", null, 1, 0, 0, 0, "", 0, 0, 0, Arrays.asList(null, null)),
                new Unidad("", null, 1, 0, 0, 0, "", 0, 0, 0, Arrays.asList(null, null)),
                new Unidad("", null, 1, 0, 0, 0, "", 0, 0, 0, Arrays.asList(null, null))));

        model.addAttribute("unitsP1", unitsP1);

        List<Unidad> unitsP2 = new ArrayList<>(Arrays.asList(
                new Unidad("", null, 1, 0, 0, 0, "", 0, 0, 0, Arrays.asList(null, null)),
                new Unidad("", null, 1, 0, 0, 0, "", 0, 0, 0, Arrays.asList(null, null)),
                new Unidad("", null, 1, 0, 0, 0, "", 0, 0, 0, Arrays.asList(null, null)),
                new Unidad("", null, 1, 0, 0, 0, "", 0, 0, 0, Arrays.asList(null, null))));
        model.addAttribute("unitsP2", unitsP2);

        Jugador j1 = new Jugador("Jugador 1");
        Jugador j2 = new Jugador("Jugador 2");

        List<Mensaje> mensajes = new ArrayList<>();
        mensajes.add(new Mensaje(j1, "¡Hola! ¿Cómo están todos?"));
        mensajes.add(new Mensaje(j2, "Todo bien, ¡listos para jugar!"));
        mensajes.add(new Mensaje(j1, "Perfecto, ¡empecemos!"));
        mensajes.add(new Mensaje(j2, "¡Vamos allá!"));
        mensajes.add(new Mensaje(j1, "¡Buena partida!"));

        // Agregar los mensajes al modelo
        model.addAttribute("mensajes", mensajes);

        return "game";
    }
}
