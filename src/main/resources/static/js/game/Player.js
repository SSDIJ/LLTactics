import Shop from "./Shop.js"

class Player {
    constructor(name) {
        this.name = name;
        this.health = 100;
        this.stars = 1000;
        this.units = [];
        this.shop = new Shop();  // Cada jugador tiene su propia tienda
    }

    getHealth() {
        return this.health;
    }

    getStars() {
        return this.stars;
    }

    refreshShop() {
        if (this.stars >= this.shop.getRefreshPrice()) {
            this.stars -= this.shop.getRefreshPrice();
            this.shop.refresh();
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
        if (this.stars >= item.price) {
            item.applyEffect(this);
            this.stars -= item.price;
            console.log(`${this.name} compró un objeto: ${item.name}`);
        } else {
            console.log(`${this.name} no tiene suficientes monedas.`);
        }
    }

    // Ver las unidades compradas por el jugador
    showUnits() {
        console.log(`${this.name}'s unidades:`);
        this.units.forEach(unit => console.log(unit.name));
    }
}

export default Player;