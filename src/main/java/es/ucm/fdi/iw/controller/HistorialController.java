package es.ucm.fdi.iw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import es.ucm.fdi.iw.model.User;
import es.ucm.fdi.iw.repositories.PartidasRepository;
import jakarta.servlet.http.HttpSession;
import es.ucm.fdi.iw.model.Partida;


import es.ucm.fdi.iw.repositories.UserRepository;
import es.ucm.fdi.iw.repositories.PartidasRepository;

import java.util.List;

@Controller
public class HistorialController {

    @Autowired
    private PartidasRepository partidasRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/historial/{id}")
    public String historial(@PathVariable long id, Model model, HttpSession session) {
      User u =userRepository.findById(id).get();
        if (u == null) {
            return "redirect:/login";
        }
        else{
            System.out.println("Encontrado el user: "+ u.getUsername());
        }
        User user= userRepository.findById(id).get();
        List<Partida> partidas= partidasRepository.findByJugador1(user);
        System.out.println("Partidas cargadas"+ partidas.size());
        model.addAttribute("partidas", partidas);
        model.addAttribute("usuarioHistorial", u);
        return "historial";  
    }
}
