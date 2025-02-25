package es.ucm.fdi.iw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import org.springframework.web.bind.annotation.GetMapping;

import es.ucm.fdi.iw.Clases.Heroe;

import java.util.ArrayList;
import java.util.List;



@Controller
public class GaleriaController {
    
    @Autowired
    private EntityManager entityManager;

    @Transactional
    @GetMapping("/galeria")
    public String mostrarGaleria(Model model) {
        List<Heroe> humanos = new ArrayList<>();
        List<Heroe> dragones = new ArrayList<>();
        List<Heroe> trolls = new ArrayList<>();
        List<Heroe> noMuertos = new ArrayList<>();
        List<Heroe> legendarios = new ArrayList<>();

        /* CODIGO ANTIGUO PARA RELLENAR LAS BASES DE DATOS, COMENTADO PARA EVITAR ERRORES PORQUE YA ESTÁ AÑADIDO 
        Heroe heroe  = new Heroe("Tanque", "/img/units/humans/1. Tanque/heavyinfantry.png", 25, 50, 75, 100, "Es la primera línea de defensa.",0);
        entityManager.persist(heroe);
        Heroe heroe1 = new Heroe("General de Guerra", "/img/units/humans/2. General/general.png", 200, 80, 70, 40, "Un estratega nato en el campo de batalla.",0);
        entityManager.persist(heroe1);
        Heroe heroe2 = new Heroe("Caballero", "/img/units/humans/3. Caballero/knight.png", 180, 60, 85, 50, "Ágil y feroz en combate.",0);
        entityManager.persist(heroe2);  
        Heroe heroe3 = new Heroe("Arquero", "/img/units/humans/4. Arquero/longbowman-bow-attack-1.png", 120, 30, 95, 70, "Con una puntería letal.",0); 
        entityManager.persist(heroe3);
        Heroe heroe4 = new Heroe("Mago", "/img/units/humans/5. Mago/white-mage.png", 140, 20, 110, 60, "Un maestro de la magia arcana.",0);
        entityManager.persist(heroe4);
        Heroe heroe5 = new Heroe("Dragón Tanque", "/img/units/dragons/1. DTanque/enforcer-blade.png", 350, 120, 40, 20, "El Dragón Tanque es una fortaleza viviente con una armadura impenetrable, diseñado para absorber grandes cantidades de daño.",1);
        entityManager.persist(heroe5);
        Heroe heroe6 = new Heroe("Berserker", "/img/units/dragons/2. DBerserker/clasher-blade.png", 250, 50, 90, 45, "El Berserker es una furia desatada en el campo de batalla, con ataques devastadores a costa de una defensa reducida.",1);
        entityManager.persist(heroe6);
        Heroe heroe7 = new Heroe("Guardián", "/img/units/dragons/3. DAlabarda/warden.png", 280, 90, 75, 35, "Con su alabarda mágica, el Guardián equilibra ataque y defensa, protegiendo a sus aliados con su imponente presencia.",1); 
        entityManager.persist(heroe7);
        Heroe heroe8 = new Heroe("Incinerador", "/img/units/dragons/4. DGris/burner.png", 200, 40, 110, 50, "El Incinerador es un dragón de fuego que abrasa todo a su paso con llamas intensas y ataques explosivos.",1);
        entityManager.persist(heroe8);
        Heroe heroe9 = new Heroe("Dragón Morado", "/img/units/dragons/5. DMorado/blademaster.png", 180, 30, 95, 65, "Especializado en la magia y las runas, el Dragón Morado es un apoyo para su equipo y una amenaza para el contrario.",1);
        entityManager.persist(heroe9);
        Heroe heroe10 = new Heroe("Troll Gigante", "/img/units/trolls/1. TTanque/great-troll.png", 300, 60, 50, 30, "El Troll Gigante es una bestia imponente con una fuerza descomunal.",2);
        entityManager.persist(heroe10);
        Heroe heroe11 = new Heroe("Troll Asesino", "/img/units/trolls/2. TAssasin/whelp.png", 150, 30, 100, 70, "El Troll Asesino es rápido y letal, especializado en ataques furtivos.",2);
        entityManager.persist(heroe11);
        Heroe heroe12 = new Heroe("Troll Guerrero", "/img/units/trolls/3. TOfftanque/warrior.png", 200, 50, 80, 40, "El Troll Guerrero es un combatiente feroz con habilidades equilibradas.",2);
        entityManager.persist(heroe12);
        Heroe heroe13 = new Heroe("Troll Certero", "/img/units/trolls/4. TRango/lobber.png", 180, 40, 90, 60, "El Troll Certero es un experto en ataques a distancia con gran precisión.",2);
        entityManager.persist(heroe13);
        Heroe heroe14 = new Heroe("Troll Chamán", "/img/units/trolls/5. TMago/shaman.png", 160, 20, 70, 80, "El Troll Chamán utiliza poderes místicos para apoyar a sus aliados.",2);
        entityManager.persist(heroe14);
        Heroe heroe15 = new Heroe("Esqueleto Corpulento", "/img/units/skeletons/1. STanque/draug.png", 250, 70, 60, 30, "El Esqueleto Corpulento es un guerrero resistente que puede soportar mucho daño.",3);
        entityManager.persist(heroe15);
        Heroe heroe16 = new Heroe("Esqueleto General", "/img/units/skeletons/2. SGeneral/deathknight.png", 220, 60, 80, 40, "El Esqueleto General lidera a sus tropas con una presencia intimidante.",3);
        entityManager.persist(heroe16);
        Heroe heroe17 = new Heroe("Esqueleto a caballo", "/img/units/skeletons/3. SCaballero/boneknight-attack.png", 200, 50, 90, 50, "El Esqueleto a caballo es rápido y mortal en combate.",3);
        entityManager.persist(heroe17);
        Heroe heroe18 = new Heroe("Esqueleto Arquero", "/img/units/skeletons/4. SArquero/banebow-bow.png", 180, 40, 100, 60, "El Esqueleto Arquero es letal a larga distancia con su arco.",3);
        entityManager.persist(heroe18);
        Heroe heroe19 = new Heroe("Esqueleto mago", "/img/units/skeletons/5. SMago/ancient-lich.png", 160, 30, 110, 70, "El Esqueleto mago utiliza poderosos hechizos para devastar a sus enemigos.",3);
        entityManager.persist(heroe19);
        Heroe heroe20 = new Heroe("Criatura Legendaria", "/img/units/legendary/1. Legendaria/legendary-creature.png", 400, 120, 200, 40, "Una criatura de leyenda con habilidades extraordinarias.",4);
        entityManager.persist(heroe20);

        entityManager.flush(); // <- implicito al final de la transaccion

        */

        // Humanos
        humanos.add(new Heroe("Tanque", "/img/units/humans/1. Tanque/heavyinfantry.png", 25, 50, 75, 100, "Es la primera línea de defensa.",0));
        humanos.add(new Heroe("General de Guerra", "/img/units/humans/2. General/general.png", 200, 80, 70, 40, "Un estratega nato en el campo de batalla.",0));
        humanos.add(new Heroe("Caballero", "/img/units/humans/3. Caballero/knight.png", 180, 60, 85, 50, "Ágil y feroz en combate.",0));
        humanos.add(new Heroe("Arquero", "/img/units/humans/4. Arquero/longbowman-bow-attack-1.png", 120, 30, 95, 70, "Con una puntería letal.",0));
        humanos.add(new Heroe("Mago", "/img/units/humans/5. Mago/white-mage.png", 140, 20, 110, 60, "Un maestro de la magia arcana.",0));

        // Dragones
        dragones.add(new Heroe("Dragón Tanque", "/img/units/dragons/1. DTanque/enforcer-blade.png", 350, 120, 40, 20, "El Dragón Tanque es una fortaleza viviente con una armadura impenetrable, diseñado para absorber grandes cantidades de daño.",1));
        dragones.add(new Heroe("Berserker", "/img/units/dragons/2. DBerserker/clasher-blade.png", 250, 50, 90, 45, "El Berserker es una furia desatada en el campo de batalla, con ataques devastadores a costa de una defensa reducida.",1));
        dragones.add(new Heroe("Guardián", "/img/units/dragons/3. DAlabarda/warden.png", 280, 90, 75, 35, "Con su alabarda mágica, el Guardián equilibra ataque y defensa, protegiendo a sus aliados con su imponente presencia.",1));
        dragones.add(new Heroe("Incinerador", "/img/units/dragons/4. DGris/burner.png", 200, 40, 110, 50, "El Incinerador es un dragón de fuego que abrasa todo a su paso con llamas intensas y ataques explosivos.",1));
        dragones.add(new Heroe("Dragón Morado", "/img/units/dragons/5. DMorado/blademaster.png", 180, 30, 95, 65, "Especializado en la magia y las runas, el Dragón Morado es un apoyo para su equipo y una amenaza para el contrario.",1));

        // Trolls
        trolls.add(new Heroe("Troll Gigante", "/img/units/trolls/1. TTanque/great-troll.png", 300, 60, 50, 30, "El Troll Gigante es una bestia imponente con una fuerza descomunal.",2));
        trolls.add(new Heroe("Troll Asesino", "/img/units/trolls/2. TAssasin/whelp.png", 150, 30, 100, 70, "El Troll Asesino es rápido y letal, especializado en ataques furtivos.",2));
        trolls.add(new Heroe("Troll Guerrero", "/img/units/trolls/3. TOfftanque/warrior.png", 200, 50, 80, 40, "El Troll Guerrero es un combatiente feroz con habilidades equilibradas.",2));
        trolls.add(new Heroe("Troll Certero", "/img/units/trolls/4. TRango/lobber.png", 180, 40, 90, 60, "El Troll Certero es un experto en ataques a distancia con gran precisión.",2));
        trolls.add(new Heroe("Troll Chamán", "/img/units/trolls/5. TMago/shaman.png", 160, 20, 70, 80, "El Troll Chamán utiliza poderes místicos para apoyar a sus aliados.",2));

        // No muertos
        noMuertos.add(new Heroe("Esqueleto Corpulento", "/img/units/skeletons/1. STanque/draug.png", 250, 70, 60, 30, "El Esqueleto Corpulento es un guerrero resistente que puede soportar mucho daño.",3));
        noMuertos.add(new Heroe("Esqueleto General", "/img/units/skeletons/2. SGeneral/deathknight.png", 220, 60, 80, 40, "El Esqueleto General lidera a sus tropas con una presencia intimidante.",3));
        noMuertos.add(new Heroe("Esqueleto a caballo", "/img/units/skeletons/3. SCaballero/boneknight-attack.png", 200, 50, 90, 50, "El Esqueleto a caballo es rápido y mortal en combate.",3));
        noMuertos.add(new Heroe("Esqueleto Arquero", "/img/units/skeletons/4. SArquero/banebow-bow.png", 180, 40, 100, 60, "El Esqueleto Arquero es letal a larga distancia con su arco.",3));
        noMuertos.add(new Heroe("Esqueleto mago", "/img/units/skeletons/5. SMago/ancient-lich.png", 160, 30, 110, 70, "El Esqueleto mago utiliza poderosos hechizos para devastar a sus enemigos.",3));

        // Criaturas legendarias
        legendarios.add(new Heroe("Criatura Legendaria", "/img/units/legendary/1. Legendaria/legendary-creature.png", 400, 120, 200, 40, "Una criatura de leyenda con habilidades extraordinarias.",4));

        model.addAttribute("humanos", humanos);
        model.addAttribute("trolls", trolls);
        model.addAttribute("dragones", dragones);
        model.addAttribute("noMuertos", noMuertos);
        model.addAttribute("legendarios", legendarios);
        return "galeria";
    }
}