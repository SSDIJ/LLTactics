package es.ucm.fdi.iw.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.ucm.fdi.iw.Clases.Item;
import es.ucm.fdi.iw.repositories.ItemRepository;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository; // Inyecta el repositorio

    private final Random random = new Random();

    public List<Item> getRandomItems(int count) {

        List<Item> items = itemRepository.findAll();

        System.out.println("Items en la base de datos: " + items.size());
        if (items.size() <= count) {
            return items;
        }

        Collections.shuffle(items, random);
        return items.subList(0, count);
    }
    
}
