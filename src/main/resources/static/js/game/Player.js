import Item from "./Item.js";
import Unit from "./Unit.js";
import Shop from "./Shop.js"

class Player {

    MAX_ITEMS = 6;
    MAX_UNITS = 4;

    constructor(name) {
        this.name = name;
        this.health = 100;
        this.stars = 1000;
        this.inventory = new Set();  // Usamos un Set para el inventario
        this.shop = new Shop();  // Cada jugador tiene su propia tienda
        this.resetUnits();
    }

    getNullUnit(){
        return new Unit(0, 0, "", "", null, "", null, 0, 0, 0, []);
    } 

    getDefaultUnit() {
        return new Unit(30, 20, "Defiende sus tierras.", 0, 0, "/img/units/humans/0. Campesino/peasant.png", 'Campesino', 0, 10, 150);
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
    buyUnit(unit, toEnd = true) {

        console.log(this.units);

        if (this.stars >= unit.price) {
            // Busca la primera unidad undefined en el array
            let index;
            if (toEnd) index = this.units.slice().reverse().findIndex(unit => unit.imagen == "");
            else index = this.units.findIndex(unit => unit.imagen == "");
            // Reemplaza la unidad undefined
            if (index !== -1) {

                index = toEnd ? this.MAX_UNITS - index - 1 : index;

                this.units[index] = unit; 
            }
            else {
                console.log(`${this.name} no puede tener más unidades.`);
                return false; // No se pudo comprar la unidad
            }

            this.stars -= unit.price;
            console.log(`${this.name} compró una unidad: ${unit.nombre}`);
            return true;
        } 

        console.log(`${this.name} no tiene suficientes monedas.`);

        return false;
    }

    sellUnit(soldUnit) {

        console.log("Vendiendo unidad")
        console.log(soldUnit)
        const index = this.units.findIndex(unit => unit.unitID == soldUnit.unitID);
            
        // Reemplaza la unidad undefined
        if (index !== -1) {
            this.stars += this.units[index].price;
            this.units[index].items.forEach(item => {
                if (item)
                    this.inventory.add(item);
            })
            this.units[index] = new Unit(0, 0, "", "", 0, "", null, 0, 0, 0, []); 
        }
        
        console.log(`Se ha vendido la unidad ${soldUnit.nombre}`);
        console.log("Unidades restantes:")
        console.log(this.units)
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

    removeFromInventory(item) {
        if (this.inventory.has(item))
            this.inventory.delete(item);
    }

    resetUnitHealth() {
        this.units.forEach((u) => u.vida = u.vidaMax)
    }
    
}

export default Player;