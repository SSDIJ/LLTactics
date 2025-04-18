package es.ucm.fdi.iw.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.ucm.fdi.iw.model.Heroe;
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
            return heroes;
        }

        List<Heroe> selectedHeroes = new ArrayList<>();
        double totalProbabilidad = 0;  
        for (Heroe heroe : heroes) {  
            totalProbabilidad += heroe.getProbabilidad();  
        }

        for(int i = 0; i < count; i++) {
            double randVal = random.nextDouble() * totalProbabilidad;
            double cumulProbability = 0;

            for (Heroe heroe : heroes) {
                cumulProbability += heroe.getProbabilidad();
                if (randVal <= cumulProbability) {
                    selectedHeroes.add(heroe);
                    break;
                }
            }
        }

        return selectedHeroes;
    }

    public List<Heroe> obtenerHeroesDeFaccion(String faccion) {
        int idFaccion = -1;
        switch (faccion) {
            case "humanos":
                idFaccion = 0;
                break;
            case "dragones":
                idFaccion = 1;
                break;
            case "trolls":
                idFaccion = 2;
                break;
            case "noMuertos":
                idFaccion = 3;
                break;
            case "legendarios":
                idFaccion = 4;
                break;
            default:
                return Collections.emptyList();
        }
        return heroeRepository.findByFaccion(idFaccion);
    }
    
}
