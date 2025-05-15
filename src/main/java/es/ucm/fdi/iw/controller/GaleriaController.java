package es.ucm.fdi.iw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import es.ucm.fdi.iw.model.Heroe;
import es.ucm.fdi.iw.model.Message;
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

    @ModelAttribute
    public void populateModel(HttpSession session, Model model) {
        for (String name : new String[] { "u", "url", "ws" })
            model.addAttribute(name, session.getAttribute(name));
    }

    @Transactional
    @GetMapping("/galeria")
    public String mostrarGaleria(Model model) {

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