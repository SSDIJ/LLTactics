package es.ucm.fdi.iw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.ucm.fdi.iw.model.Heroe;
import es.ucm.fdi.iw.model.Objeto;
import es.ucm.fdi.iw.model.Unidad;
import es.ucm.fdi.iw.services.HeroesService;

@RestController
public class UnitController {

    private final HeroesService heroeService;

    public UnitController(HeroesService heroeService) {
        this.heroeService = heroeService;
    }
    @GetMapping("/api/shopUnits")
    public List<Heroe> getRandomUnits(@RequestParam int count) {
        return heroeService.getRandomHeroes(count);
    }
}
