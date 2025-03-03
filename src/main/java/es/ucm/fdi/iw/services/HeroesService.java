package es.ucm.fdi.iw.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.ucm.fdi.iw.Clases.Heroe;
import es.ucm.fdi.iw.repositories.HeroeRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HeroesService {

    @Autowired
    private HeroeRepository heroeRepository; // Inyecta el repositorio

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
    
}
