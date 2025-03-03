package es.ucm.fdi.iw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.ucm.fdi.iw.Clases.Heroe;
import es.ucm.fdi.iw.Clases.Item;
import es.ucm.fdi.iw.Clases.Jugador;
import es.ucm.fdi.iw.Clases.Mensaje;
import es.ucm.fdi.iw.Clases.Unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class GameController {

    @GetMapping("/game")
    public String showGamePage(Model model) {

        List<Item> playerObjects = List.of(
            new Item("/img/items/book1.png", "Libro Mágico", "Un libro encantado con hechizos antiguos.", 1),
            new Item("/img/items/flame-sword.png", "Espada Llameante", "Una espada forjada en fuego eterno.", 1),
            new Item(null, "", "", 1),
            new Item(null, "", "", 1),
            new Item(null, "", "", 1),
            new Item(null, "", "", 1),
            new Item(null, "", "", 1)
        );
        model.addAttribute("playerObjects", playerObjects);

        List<Item> shopItems = List.of(
            new Item("/img/items/potion-red.png", "Poción Roja", "", 1),
            new Item("/img/items/flame-sword.png", "Espada de Hierro", "", 5)
        );
        model.addAttribute("shopItems", shopItems);

        List<Heroe> shopUnits = List.of(
            new Heroe("Dragón", "/img/units/dragons/4. DGris/burner.png", 0, 0, 0, 0, null, 0, 2),
            new Heroe("Esqueleto", "/img/units/humans/5. Mago/white-mage.png", 0, 0, 0, 0, null, 0, 4),
            new Heroe("Mago", "/img/units/humans/5. Mago/white-mage.png", 0, 0, 0, 0, null, 0, 1)
        );
        model.addAttribute("shopUnits", shopUnits);

        List<Unit> unitsP1 = new ArrayList<>(Arrays.asList(
            new Unit("Mago", 55, 75, "/img/units/humans/5. Mago/white-mage.png", Arrays.asList(null, null)),
            null,
            new Unit("Troll", 55, 75, "/img/units/trolls/3. TOfftanque/warrior.png", Arrays.asList(null, null)),
            new Unit("General", 55, 75, "/img/units/humans/2. General/general-breeze1.png", Arrays.asList(new Item("/img/items/staff-druid.png", "", "", 1), null)),
            new Unit("Paladín", 55, 75, "/img/units/humans/3. Caballero/knight.png", Arrays.asList(null, null))
        ));
        model.addAttribute("unitsP1", unitsP1);

        List<Unit> unitsP2 = new ArrayList<>(Arrays.asList(
            new Unit("Espíritu", 55, 75, "/img/units/legendary/fireghost/fireghost.png", Arrays.asList(new Item("/img/items/ball-blue.png","", "", 1), null)),
            new Unit("General", 55, 75, "/img/units/humans/2. General/general-breeze1.png", Arrays.asList(null, null)),
            null,
            null,
            new Unit("Arquero", 55, 75, "/img/units/humans/4. Arquero/longbowman-bow.png", Arrays.asList(null, null))
        ));
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
