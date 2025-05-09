package es.ucm.fdi.iw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.annotation.PostConstruct;
import es.ucm.fdi.iw.model.Heroe;
import es.ucm.fdi.iw.model.Objeto;
import es.ucm.fdi.iw.repositories.HeroeRepository;
import es.ucm.fdi.iw.repositories.ItemRepository;

import java.util.List;

/* Recibe petición HTTP para mostrar la galería y devuelve la vista "galeria" con toda la información de los héroes agrupados por facción */
@Controller
public class HeroeController {

    @Autowired
    private HeroeRepository heroeRepository;

    private List<Heroe> todosLosHeroes;

    @PostConstruct
    public void init() {
        todosLosHeroes = heroeRepository.findAll();  // Carga todos los héroes al iniciar la aplicación
        System.out.println("Héroes cargados al iniciar la aplicación: " + todosLosHeroes.size());
    }

    public List<Heroe> getTodosLosHeroes() {
        return todosLosHeroes;
    }

    public Heroe findbyId(int id){
        long findID= (long) id;
        Heroe findHeroe=heroeRepository.findById(findID);
        return findHeroe;
    }
   


}
