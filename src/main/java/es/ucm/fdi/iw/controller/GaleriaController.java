package es.ucm.fdi.iw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import org.springframework.web.bind.annotation.GetMapping;

import es.ucm.fdi.iw.model.Heroe;
import es.ucm.fdi.iw.model.Jugador;
import es.ucm.fdi.iw.model.Mensaje;
import es.ucm.fdi.iw.model.Objeto;
import es.ucm.fdi.iw.model.Partida;
import es.ucm.fdi.iw.repositories.HeroeRepository;
import es.ucm.fdi.iw.repositories.ItemRepository;

import java.util.ArrayList;
import java.util.List;

/* Recibe petición HTTP para mostrar la galería y devuelve la vista "galeria" con toda la información de los héroes agrupados por facción */

@Controller
public class GaleriaController {
    
    @Autowired
    private HeroeRepository heroeRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @GetMapping("/galeria")
    public String mostrarGaleria(Model model) {

        /* CODIGO ANTIGUO PARA RELLENAR LAS BASES DE DATOS, COMENTADO PARA EVITAR ERRORES PORQUE YA ESTÁ AÑADIDO */ 
        /*
        Heroe heroe  = new Heroe("Tanque", "/img/units/humans/1. Tanque/heavyinfantry.png", 300, 75, 150, 75, "Firme como una muralla de acero, el Tanque es la primera línea de defensa, imparable ante los embates enemigos. Con 300 de vida y una resistencia formidable, se erige como el escudo de sus compañeros en el fragor de la batalla.", 0, 3, 0.4);
        entityManager.persist(heroe);
        Heroe heroe1 = new Heroe("General de Guerra", "/img/units/humans/2. General/general.png", 262, 45, 112, 56, "Sabio en las artes de la estrategia, el General no solo combate, sino que guía a sus tropas con astucia. Con una armadura ligera pero letal en combate, sus tácticas dan la ventaja en el campo de batalla.", 0, 4, 0.2);
        entityManager.persist(heroe1);
        Heroe heroe2 = new Heroe("Caballero", "/img/units/humans/3. Caballero/knight.png", 225, 68, 150, 45, "Ágil y feroz, el Caballero es un guerrero que destaca por su destreza y valor. Con gran habilidad en el combate cuerpo a cuerpo, su espada brilla como un rayo de esperanza para su gente.",0, 3, 0.4);
        entityManager.persist(heroe2);  
        Heroe heroe3 = new Heroe("Arquero", "/img/units/humans/4. Arquero/longbowman-bow-attack-1.png", 150, 37, 90, 68, "El ojo certero del ejército, el Arquero lanza flechas como estrellas fugaces. Con puntería mortal, su habilidad para atacar desde lejos es esencial para desgastar al enemigo antes de que se acerque.",0, 2, 0.4); 
        entityManager.persist(heroe3);
        Heroe heroe4 = new Heroe("Mago", "/img/units/humans/5. Mago/white-mage.png", 187, 30, 135, 68, "El conjurador de fuerzas arcanas, el Mago domina la magia como un tejedor de destinos. Su poder radica en su habilidad para controlar el fuego, el hielo y otras artes místicas, arrasando a sus enemigos desde la distancia.",0, 5, 0.35);
        entityManager.persist(heroe4);
        Heroe heroe5 = new Heroe("Dragón Tanque", "/img/units/dragons/1. DTanque/enforcer-blade.png", 285, 80, 145, 40, "Un muro viviente, el Dragón Tanque es una fortaleza de escamas y fuego. Con una armadura impenetrable y un corazón de acero, se erige como el centinela más temible del campo, capaz de resistir las embestidas más feroces.",1, 6, 0.4);
        entityManager.persist(heroe5);
        Heroe heroe6 = new Heroe("Berserker", "/img/units/dragons/2. DBerserker/clasher-blade.png", 190, 20, 140, 90, "La furia hecha carne, el Berserker es un dragón que se lanza al combate sin medida, arrasando con cada golpe. Aunque su defensa es frágil, sus ataques devastadores pueden destruir cualquier enemigo que se cruce en su camino.",1, 5, 0.4);
        entityManager.persist(heroe6);
        Heroe heroe7 = new Heroe("Guardián", "/img/units/dragons/3. DAlabarda/warden.png", 220, 75, 135, 45, "El protector de los suyos, el Guardián blande su alabarda mágica con maestría, equilibrando su agresión con una defensa formidable. Su presencia en la batalla es un escudo que protege a sus aliados de la muerte misma.",1, 5, 0.35); 
        entityManager.persist(heroe7);
        Heroe heroe8 = new Heroe("Incinerador", "/img/units/dragons/4. DGris/burner.png", 230, 15, 170, 50, "El fuego que arrasa todo a su paso, el Incinerador es un dragón de llamas intensas que reduce a cenizas a sus enemigos. Sus ataques explosivos convierten el campo de batalla en un infierno, quemando todo lo que toca.",1, 4, 0.35);
        entityManager.persist(heroe8);
        Heroe heroe9 = new Heroe("Dragón Morado", "/img/units/dragons/5. DMorado/blademaster.png", 315, 15, 170, 50, "El hechicero de las runas, el Dragón Morado combina magia arcana y poder destructivo en una única entidad. Su habilidad para manipular las runas lo convierte en un aliado invaluable y una amenaza peligrosa para cualquier adversario.",1, 4, 0.2);
        entityManager.persist(heroe9);
        Heroe heroe10 = new Heroe("Troll Gigante", "/img/units/trolls/1. TTanque/great-troll.png", 280, 85, 120, 70, "Una bestia imparable, el Troll Gigante es una masa de músculo y furia. Su fuerza descomunal lo convierte en un enemigo temido en las líneas de batalla, capaz de aplastar a cualquier rival con un solo golpe.",2, 3, 0.35);
        entityManager.persist(heroe10);
        Heroe heroe11 = new Heroe("Troll Asesino", "/img/units/trolls/2. TAssasin/whelp.png", 250, 50, 135, 60, "La sombra que corta, el Troll Asesino se desliza entre las sombras con agilidad mortal. Sus ataques furtivos y rápidos dejan a sus enemigos sin aliento, antes de que puedan siquiera reaccionar a su presencia.",2, 2, 0.35);
        entityManager.persist(heroe11);
        Heroe heroe12 = new Heroe("Troll Guerrero", "/img/units/trolls/3. TOfftanque/warrior.png", 275, 60, 175, 50, "El combatiente equilibrado, el Troll Guerrero es un maestro del combate con habilidades tanto ofensivas como defensivas. Su resistencia y destreza le permiten mantenerse firme, enfrentando a sus adversarios con una feroz determinación.",2, 3, 0.4);
        entityManager.persist(heroe12);
        Heroe heroe13 = new Heroe("Troll Certero", "/img/units/trolls/4. TRango/lobber.png", 170, 30, 110, 80, "La mano que nunca falla, el Troll Certero es un experto en el arte de los ataques a distancia. Con gran precisión, lanza sus proyectiles como flechas al corazón de sus enemigos, eliminando amenazas desde lejos con puntería infalible.",2, 2, 0.4);
        entityManager.persist(heroe13);
        Heroe heroe14 = new Heroe("Troll Chamán", "/img/units/trolls/5. TMago/shaman.png", 210, 25, 160, 50, "El invocador de fuerzas oscuras, el Troll Chamán usa su conexión mística con la naturaleza y los espíritus para invocar poderosos hechizos que apoyan a sus aliados y desestabilizan a sus enemigos. Su magia es tan impredecible como las tormentas.",2, 4, 0.2);
        entityManager.persist(heroe14);
        Heroe heroe15 = new Heroe("Esqueleto Corpulento", "/img/units/skeletons/1. STanque/draug.png", 295, 70, 140, 60, "El guerrero eterno, el Esqueleto Corpulento resiste las heridas del tiempo y el combate. Su enorme resistencia lo convierte en una fortaleza ambulante, capaz de soportar grandes daños y seguir luchando con furia implacable.",3, 3, 0.4);
        entityManager.persist(heroe15);
        Heroe heroe16 = new Heroe("Esqueleto General", "/img/units/skeletons/2. SGeneral/deathknight.png", 235, 55, 125, 70, "El líder de los muertos, el Esqueleto General emana una presencia temible que inspira terror y respeto. Su destreza táctica y su voluntad inquebrantable lo convierten en el comandante perfecto para las huestes de la no muerte.",3, 4, 0.35);
        entityManager.persist(heroe16);
        Heroe heroe17 = new Heroe("Esqueleto a caballo", "/img/units/skeletons/3. SCaballero/boneknight-attack.png", 260, 75, 165, 45, "La sombra rápida, el Esqueleto a caballo es un jinete espectral que asola el campo con su destreza y velocidad. Mortal en combate, se mueve con la gracia de una tormenta, causando estragos entre las filas enemigas.",3, 4, 0.2);
        entityManager.persist(heroe17);
        Heroe heroe18 = new Heroe("Esqueleto Arquero", "/img/units/skeletons/4. SArquero/banebow-bow.png", 180, 25, 130, 85, "La flecha de la muerte, el Esqueleto Arquero lanza sus proyectiles con una precisión mortal. Desde la distancia, derrumba a sus enemigos uno por uno, como un cazador que acecha a su presa.",3, 3, 0.4);
        entityManager.persist(heroe18);
        Heroe heroe19 = new Heroe("Esqueleto mago", "/img/units/skeletons/5. SMago/ancient-lich.png", 220, 20, 150, 55, "El hechicero del abismo, el Esqueleto Mago invoca oscuros hechizos que devoran todo a su paso. Su magia arcana es tan poderosa como su cuerpo esqueleto, y arrastra a sus enemigos hacia la oscuridad.",3, 5, 0.35);
        entityManager.persist(heroe19);
    
        Heroe heroe20 = new Heroe("Criatura Legendaria", "/img/units/legendary/fireghost/fireghost.png", 400, 120, 200, 40, "El espectro de fuego eterno, la Criatura Legendaria es una aparición envuelta en llamas ardientes, cuya existencia desafía la realidad. Con habilidades extraordinarias, su poder es tan vasto como las llamas que consume, arrasando con todo a su paso.",4, 6, 0.05);
        entityManager.persist(heroe20);

        entityManager.flush(); // <- implícito al final de la transacción
        */

        /*
        Objeto objeto = new Objeto("Espada", "/img/items/sword.png", "Una espada afilada.", 1);
        entityManager.persist(objeto);
        entityManager.flush();
        */
        
        /*
        // Crear y persistir una partida
        Partida partida = new Partida("Partida 1");
        entityManager.persist(partida);

        // Crear y persistir un héroe
        Heroe heroe = new Heroe("Tanque", "/img/units/humans/1. Tanque/heavyinfantry.png", 25, 50, 75, 100, "Es la primera línea de defensa.", 0);
        entityManager.persist(heroe);

        // Crear y persistir un jugador
        List<Heroe> masJugados = new ArrayList<>();
        masJugados.add(heroe);
        Jugador jugador = new Jugador("Jugador 1", "/img/players/player1.png", 1, 1000, 10, 5, 0, masJugados, false, null);
        jugador.setPartida(partida);
        entityManager.persist(jugador);

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

        // // Criaturas legendarias
        legendarios.add(new Heroe("Criatura Legendaria", "/img/units/legendary/1. Legendaria/legendary-creature.png", 400, 120, 200, 40, "Una criatura de leyenda con habilidades extraordinarias.",4));
        */
        List<Heroe> humanos = heroeRepository.findByFaccion(0);
        List<Heroe> dragones = heroeRepository.findByFaccion(1);
        List<Heroe> trolls = heroeRepository.findByFaccion(2);
        List<Heroe> noMuertos = heroeRepository.findByFaccion(3);
        List<Heroe> legendarios = heroeRepository.findByFaccion(4);

        List<Objeto> objetos = itemRepository.findAll();

        model.addAttribute("humanos", humanos);
        model.addAttribute("trolls", trolls);
        model.addAttribute("dragones", dragones);
        model.addAttribute("noMuertos", noMuertos);
        model.addAttribute("legendarios", legendarios);
        
        model.addAttribute("objetos", objetos);
        return "galeria";
    }
}