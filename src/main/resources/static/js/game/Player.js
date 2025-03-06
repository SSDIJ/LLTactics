import Item from "./Item.js";
import Shop from "./Shop.js"

class Player {

    MAX_ITEMS = 7;
    MAX_UNITS = 4;

    constructor(name) {
        this.name = name;
        this.health = 100;
        this.stars = 1000;
        this.units = [];
        this.inventory = new Set();  // Usamos un Set para el inventario
        this.shop = new Shop();  // Cada jugador tiene su propia tienda
    }

    getHealth() {
        return this.health;
    }

    getStars() {
        return this.stars;
    }

    async refreshShop() {
        if (this.stars >= this.shop.getRefreshPrice()) {
            this.stars -= this.shop.getRefreshPrice();
            await this.shop.refresh();
            return true;
        }
        else {
            console.log("No tienes suficientes estrellas.")
        }

        return false;
    }

    // Comprar una unidad de la tienda
    buyUnit(unit) {
        if (this.stars >= unit.price) {
            this.units.push(unit);
            this.stars -= unit.price;
            console.log(`${this.name} compró una unidad: ${unit.name}`);
        } else {
            console.log(`${this.name} no tiene suficientes monedas.`);
        }
    }

    // Comprar un objeto de la tienda
    buyItem(item) {
        if (this.stars >= item.price && this.inventory.size < this.MAX_ITEMS && this.shop.buyItem(item.id)) {
            this.stars -= item.price;
            console.log(`${this.name} compró un objeto: ${item.name}`);

            this.inventory.add(item); 
            return true;
        }
        return false;
    }

    sellItem(soldItem) {
        if (this.inventory.has(soldItem)) {
            this.inventory.delete(soldItem);
            this.stars += soldItem.price;
            console.log(`${this.name} vendió un objeto: ${soldItem.name}`);
        } 
    }
}

export default Player;