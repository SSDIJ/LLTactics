package es.ucm.fdi.iw.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 *  Non-authenticated requests only.
 */
@Controller
public class RootController {

    private static final Logger log = LogManager.getLogger(RootController.class);

    @ModelAttribute
    public void populateModel(HttpSession session, Model model) {        
        for (String name : new String[] {"u", "url", "ws"}) {
            model.addAttribute(name, session.getAttribute(name));
        }
    }

	@GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        boolean error = request.getQueryString() != null && request.getQueryString().indexOf("error") != -1;
        model.addAttribute("loginError", error);
        return "login";
    }

    @GetMapping("/registro")
    public String registro(Model model, HttpServletRequest request) {
        return "registro";
    }
    
    @GetMapping("/reglas1")
    public String reglas1(Model model) {
        return "reglas1";
    }

    @GetMapping("/game")
    public String game(Model model) {
        return "game";
    }
/* 
     @GetMapping("/ranking")
     public String ranking (Model model){
        return "ranking";
     }
    
     
     @GetMapping("/galeria")
     public String galeria (Model model){
        return "galeria";
     }  */

    @GetMapping("/autores")
    public String autores(Model model) {
        return "autores";
    }
    @GetMapping("/profile")
    public String profile(Model model) {
        return "profile";
    }
	@GetMapping("/")
    public String index(Model model) {
        return "index";
    }
}
