package es.ucm.fdi.iw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import es.ucm.fdi.iw.model.Partida;
import es.ucm.fdi.iw.repositories.userRepository;
import jakarta.persistence.EntityManager;
import es.ucm.fdi.iw.repositories.partidasRepository;

@Controller
public class PartidasController {

    @Autowired
	private EntityManager entityManager;

    @Autowired
    private partidasRepository partidasRepository;

    @GetMapping("/creaPartida")
    public String creaPartida(Model model, Long jid1, Long jid2, int duracionMin) {
        Partida partida = new Partida();
        //partida.setJid1(jid1);
        //partida.setJid2(jid2);
        partida.setDuracionMin(duracionMin);

        // Guarda la nueva partida en la base de datos
		entityManager.persist(partida);
		entityManager.flush();  

        return "gestPartidas";
    }
    
}
