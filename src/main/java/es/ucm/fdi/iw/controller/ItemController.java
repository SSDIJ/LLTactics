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
import es.ucm.fdi.iw.services.ItemService;

@RestController
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    public List<Objeto> getRandomItems(int count) {
        return itemService.getRandomItems(count);
    }
}
