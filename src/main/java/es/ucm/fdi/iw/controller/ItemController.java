package es.ucm.fdi.iw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.ucm.fdi.iw.Clases.Heroe;
import es.ucm.fdi.iw.Clases.Objeto;
import es.ucm.fdi.iw.Clases.Unidad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.ucm.fdi.iw.services.ItemService;

@RestController
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }
    @GetMapping("/api/shopItems")
    
    public List<Objeto> getRandomItems(@RequestParam int count) {
        return itemService.getRandomItems(count);
    }
}
