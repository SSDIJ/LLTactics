package es.ucm.fdi.iw.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.ucm.fdi.iw.model.Heroe;
import es.ucm.fdi.iw.model.Objeto;
import es.ucm.fdi.iw.model.Unidad;
import es.ucm.fdi.iw.services.HeroesService;
import jakarta.servlet.http.HttpSession;

@RestController
public class UnitController {

    private final HeroesService heroeService;

    @ModelAttribute
    public void populateModel(HttpSession session, Model model) {
        for (String name : new String[] { "u", "url", "ws" })
            model.addAttribute(name, session.getAttribute(name));
    }

    public UnitController(HeroesService heroeService) {
        this.heroeService = heroeService;
    }
    
    public List<Heroe> getRandomUnits(int count) {
        return heroeService.getRandomHeroes(count);
    }
}
