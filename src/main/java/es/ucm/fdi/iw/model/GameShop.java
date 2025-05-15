package es.ucm.fdi.iw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DurationFormat.Unit;
import org.springframework.stereotype.Component;

import es.ucm.fdi.iw.services.HeroesService;
import es.ucm.fdi.iw.services.ItemService;
import lombok.Data;

@Data
public class GameShop {

    private final int NUM_UNITS = 3;
    private final int NUM_ITEMS = 2;

    private List<Heroe> units;
    private List<Objeto> items;
    private List<Boolean> itemsPurchased;
    private List<Boolean> unitsPurchased;


    public GameShop() {
        this.units = new ArrayList<>();
        this.items = new ArrayList<>();
        this.itemsPurchased = new ArrayList<>();
        this.unitsPurchased = new ArrayList<>();

    }

    public int getRefreshPrice() {
        return GameRoom.SHOP_REFRESH_PRICE;
    }

    public boolean buyItem(long itemId) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getIdObjeto() == itemId && !itemsPurchased.get(i)) {
                itemsPurchased.set(i, true);
                return true;
            }
        }
        return false;
    }

    public boolean buyUnit(long unitId) {
        for (int i = 0; i < units.size(); i++) {
            if (units.get(i).getIdHeroe() == unitId && !unitsPurchased.get(i)) {
                unitsPurchased.set(i, true);
                return true;
            }
        }
        return false;
    }

    public void resetPurchases() {
        itemsPurchased.clear();
        unitsPurchased.clear();
        // Por defecto se espera 2 objetos y 4 unidades
        for (int i = 0; i < 2; i++) itemsPurchased.add(false);
        for (int i = 0; i < 4; i++) unitsPurchased.add(false);
    }

    public void refresh(List<Heroe> heroes, List<Objeto> items) {

        this.units = heroes;
        this.items = items;
        resetPurchases();
    }
}
