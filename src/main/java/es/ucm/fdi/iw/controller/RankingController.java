package es.ucm.fdi.iw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.ucm.fdi.iw.model.Heroe;
import es.ucm.fdi.iw.model.User;
import es.ucm.fdi.iw.repositories.userRepository;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RankingController {

    @Autowired
    private userRepository userRepository;
    

    @GetMapping("/ranking")
    public String mostrarRanking(Model model) {
    // Obtener jugadores de la base de datos
    List<User> usuarios = userRepository.findAll();

    /*
    // Ordenar por diferencia entre partidas ganadas y perdidas (descendente)
    usuarios.sort((j1, j2) -> Integer.compare(
        j2.getPartidasGanadas() - j2.getPartidasPerdidas(),
        j1.getPartidasGanadas() - j1.getPartidasPerdidas()
    ));

    */
    
    model.addAttribute("usuarios", usuarios);
    return "ranking"; // Retorna la vista "ranking.html"
}
}