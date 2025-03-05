package es.ucm.fdi.iw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.ucm.fdi.iw.Clases.Heroe;
import es.ucm.fdi.iw.Clases.Objeto;
import es.ucm.fdi.iw.Clases.Jugador;
import es.ucm.fdi.iw.Clases.Mensaje;
import es.ucm.fdi.iw.Clases.Unidad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class GameController {

    @GetMapping("/game")
    public String showGamePage(Model model) {

        List<Objeto> playerObjects = List.of(
                new Objeto("/img/items/book1.png", "Libro Mágico", "Un libro encantado con hechizos antiguos.", 1),
                new Objeto("/img/items/flame-sword.png", "Espada Llameante", "Una espada forjada en fuego eterno.", 1),
                new Objeto(null, "", "", 1),
                new Objeto(null, "", "", 1),
                new Objeto(null, "", "", 1),
                new Objeto(null, "", "", 1),
                new Objeto(null, "", "", 1));
        model.addAttribute("playerObjects", playerObjects);

        List<Objeto> shopItems = List.of(
                new Objeto("/img/items/potion-red.png", "Poción Roja", "", 1),
                new Objeto("/img/items/flame-sword.png", "Espada de Hierro", "", 5));
        model.addAttribute("shopItems", shopItems);

        List<Heroe> shopUnits = List.of(
                new Heroe("Dragón", "/img/units/dragons/4. DGris/burner.png", 0, 0, 0, 0, null, 0, 2),
                new Heroe("Esqueleto", "/img/units/humans/5. Mago/white-mage.png", 0, 0, 0, 0, null, 0, 4),
                new Heroe("Mago", "/img/units/humans/5. Mago/white-mage.png", 0, 0, 0, 0, null, 0, 1));
        model.addAttribute("shopUnits", shopUnits);

        List<Unidad> unitsP1 = new ArrayList<>(Arrays.asList(
                new Unidad("Mago", "/img/units/humans/5. Mago/white-mage.png", 20, 110, 60, 140,
                        "Un maestro de la magia arcana.", 0, 5, Arrays.asList(null, null)),
                new Unidad("Troll Gigante", "/img/units/trolls/1. TTanque/great-troll.png", 60, 50, 30, 300,
                        "El Troll Gigante es una bestia imponente con una fuerza descomunal.", 2, 3,
                        Arrays.asList(null, null)),
                new Unidad("Esqueleto General", "/img/units/skeletons/2. SGeneral/deathknight.png", 60, 80, 40, 220,
                        "El Esqueleto General lidera a sus tropas con una presencia intimidante.", 3, 4,
                        Arrays.asList(new Objeto("/img/items/staff-druid.png", "", "", 1), null)),
                new Unidad("Paladín", "/img/units/humans/3. Caballero/knight.png", 60, 85, 50, 180,
                        "Agil y feroz en combate.", 0, 3, Arrays.asList(null, null))));

        model.addAttribute("unitsP1", unitsP1);

        List<Unidad> unitsP2 = new ArrayList<>(Arrays.asList(
                new Unidad("Troll Gigante", "/img/units/trolls/1. TTanque/great-troll.png", 60, 50, 30, 300,
                        "El Troll Gigante es una bestia imponente con una fuerza descomunal.", 2, 3,
                        Arrays.asList(null, null)),
                new Unidad("Esqueleto General", "/img/units/skeletons/2. SGeneral/deathknight.png", 60, 80, 40, 220,
                        "El Esqueleto General lidera a sus tropas con una presencia intimidante.", 3, 4,
                        Arrays.asList(new Objeto("/img/items/staff-druid.png", "", "", 1), null)),
                new Unidad("Paladín", "/img/units/humans/3. Caballero/knight.png", 60, 85, 50, 180,
                        "Agil y feroz en combate.", 0, 3, Arrays.asList(null, null))));
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
