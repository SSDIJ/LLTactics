package es.ucm.fdi.iw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import es.ucm.fdi.iw.model.Heroe;
import es.ucm.fdi.iw.model.User;
import es.ucm.fdi.iw.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RankingController {

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void populateModel(HttpSession session, Model model) {
        for (String name : new String[] { "u", "url", "ws" })
            model.addAttribute(name, session.getAttribute(name));
    }

    @GetMapping("/ranking")
    public String mostrarRanking(Model model, HttpSession session) {
        User user = null;
        int puntuacion = -1;
        if (session != null) {
            user = (User) session.getAttribute("u");
        }

        if (user != null) {
            System.out.println("Usuario autenticado: " + user.getUsername());
            model.addAttribute("usuarioLogueado", true);
            puntuacion = user.getPuntuacion();
        } else {
            System.out.println("Usuario no autenticado.");
            model.addAttribute("usuarioLogueado", false);
        }

        // Lógica del ranking
        List<User> usuarios = userRepository.findAll();
        List<User> topRanking = new ArrayList<>();
        List<User> miRanking = new ArrayList<>();

        usuarios.sort((j1, j2) -> Integer.compare(
                j2.getPartidasGanadas() - j2.getPartidasPerdidas(),
                j1.getPartidasGanadas() - j1.getPartidasPerdidas()));

        // Top 5 ranking
        int top = Math.min(5, usuarios.size());
        for (int j = 0; j < top; j++) {
            topRanking.add(usuarios.get(j));
        }

        // Si hay usuario logueado, calculamos su posición y generamos su bloque de 11
        if (user != null) {
            int index = -1;

            // Buscar al usuario en la lista ordenada (por ID)
            for (int k = 0; k < usuarios.size(); k++) {
                if (usuarios.get(k).getId() == user.getId()) {
                    index = k;
                    break;
                }
            }

            if (index != -1) {
                int from = Math.max(0, index - 5);
                int to = Math.min(usuarios.size(), index + 6); // +6 para incluirse a sí mismo y 5 abajo

                miRanking = usuarios.subList(from, to);
            }
        }
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("miRanking", miRanking);
        model.addAttribute("topRanking", topRanking);
        model.addAttribute("user", session.getAttribute("u"));
        return "ranking";
    }

}