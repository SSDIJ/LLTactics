package es.ucm.fdi.iw.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.ucm.fdi.iw.Clases.Heroe;
import es.ucm.fdi.iw.repositories.HeroeRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/* Hace uso de la base de datos (a través del repositorio HeroeRepository) para obtener y organizar los datos */

@Service
public class HeroesService {

    @Autowired
    private HeroeRepository heroeRepository; // Inyecta el repositorio

    private final Random random = new Random();

    public Map<String, List<Heroe>> obtenerHeroesPorFaccion() {
        Map<String, List<Heroe>> heroesPorFaccion = new HashMap<>();
    
        // Asignar los nombres de las facciones como claves
        Map<String, Integer> facciones = Map.of(
            "humanos", 0,
            "dragones", 1,
            "trolls", 2,
            "noMuertos", 3,
            "legendarios", 4
        );
    
        facciones.forEach((nombre, id) -> {
            List<Heroe> heroes = heroeRepository.findByFaccion(id);
            heroesPorFaccion.put(nombre, heroes);
        });
    
        return heroesPorFaccion;
    }

    public List<Heroe> getRandomHeroes(int count) {
        
        List<Heroe> heroes = heroeRepository.findAll();
        if (heroes.size() <= count) {
            return heroes; // Si hay menos de 'count' héroes, devolvemos todos.
        }

        Collections.shuffle(heroes, random); // Barajar la lista
        return heroes.subList(0, count); // Tomar los primeros 'count' héroes
    }
    
}
