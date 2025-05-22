import Item from "./Item.js";
import Unit from "./Unit.js";
import Shop from "./Shop.js"

class Player {
    // Representa un jugador dentro de la partida

    static MAX_ITEMS = 6;
    static MAX_UNITS = 4;

    constructor(name) {
        this.name = name;
        this.health = 100;
        this.stars = 1000;
        this.inventory = new Set();  // Usamos un Set para el inventario
        this.shop = new Shop();  // Cada jugador tiene su propia tienda
        this.resetUnits();
    }

    canBuy(unitToBuy) {
        const index = this.units.slice().reverse().findIndex(unit => (unit.image == null || unit.image == ""));
        const ok = (this.stars >= unitToBuy.price) && (index !== -1);
        return ok;
    }

    getNullUnit(){
        return new Unit(0, 0, "", "", null, "", null, 0, 0, 0, []);
    } 

    resetUnits() {
        this.units = [
            this.getNullUnit(),
            this.getNullUnit(),
            this.getNullUnit(),
            this.getNullUnit()
        ];
    }

    getHealth() {
        return this.health;
    }

    getStars() {
        return this.stars;
    }

    canRefreshShop() {
        if (this.stars >= this.shop.getRefreshPrice()) {
            return true;
        }
        else {
            console.log("No tienes suficientes estrellas.")
        }

        return false;
    }

    updateShop(shop) {
        this.shop.update(shop)
    }


    // Comprar un objeto de la tienda
    buyItem(item, isOpponent=false) {
        if (this.stars >= item.price && this.inventory.size < Player.MAX_ITEMS && (this.shop.buyItem(item.id) || isOpponent)) {
            this.stars -= item.price;
            console.log(`${this.name} compró un objeto: ${item.name}`);

            this.inventory.add(item); 
            return true;
        }
        return false;
    }

    updateUnits(units) {
        if (Array.isArray(units)) {
            this.units = units.map(unitObj => Unit.fromUnit(unitObj));
        }
    }

    updateItems(items) {
        if (Array.isArray(items)) {
            this.inventory = new Set(
                items
                    .filter(item => item.imageUrl !== null && item.imageUrl !== "")
                    .map(itemObj => Item.fromItem(itemObj))
            );
        }
    }

    sellItem(soldItem) {
        for (let item of this.inventory) {
            if (item.id === soldItem.id) {
                this.inventory.delete(item);
                this.stars += item.price;
                console.log(`${this.name} vendió un objeto: ${item.name}`);
                return;
            }
        }
        console.log(`No se encontró el objeto con ID: ${soldItem.id}`);
    }

    removeFromInventory(removedItem) {
        for (let item of this.inventory) {
            if (item.id === removedItem.id) {
                this.inventory.delete(item);
                return;
            }
        }
    }

    resetUnitHealth() {
        this.units.forEach((u) => u.life = u.maxHealth)
    }
    
}

export default Player;