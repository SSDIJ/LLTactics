import Unit from "./Unit.js";
import Item from "./Item.js";

class Shop {

    REFRESH_PRICE = 2;

    constructor() {
        this.units = [];  // Unidades disponibles
        this.items = [];  // Objetos disponibles
        this.itemsPurchased = [];
        this.unitsPurchased = [];
        this.refresh();
    }

    getRefreshPrice() {
        return this.REFRESH_PRICE;
    }

    buyItem(itemId) {

        const index = this.items.findIndex(item => 
            item.id == itemId
        );

        if (index !== -1 && !this.itemsPurchased[index]) {
            this.itemsPurchased[index] = true;
            return true;
        } 
        return false;
    }

    buyUnit(unitId) {

        const index = this.units.findIndex(unit => 
            unit.id == unitId
        );

        if (index !== -1 && !this.unitsPurchased[index]) {
            this.unitsPurchased[index] = true;
            return true;
        } 
        return false;
    }

    resetPurchases() {
        this.itemsPurchased = [false, false]
        this.unitsPurchased = [false, false, false, false]
    }

    // Refrescar la tienda con nuevas unidades y objetos
    async refresh() {    
        const fetchUnits = fetch('/api/shopUnits?count=3')
            .then(response => response.json())
            .then(data => {
                this.units = data.map(unitData => new Unit(
                    unitData.armadura,
                    unitData.daño,
                    unitData.descripcion,
                    unitData.faccion,
                    unitData.id,
                    unitData.imagen,
                    unitData.nombre,
                    unitData.precio,
                    unitData.velocidad,
                    unitData.vida
                ));
            });
    
        const fetchItems = fetch('/api/shopItems?count=2')
            .then(response => response.json())
            .then(data => {
                this.items = data.map(itemData => new Item(
                    itemData.armadura,
                    itemData.daño,
                    itemData.descripcion,
                    itemData.idObjeto,
                    itemData.imagen,
                    itemData.nombre,
                    itemData.precio,
                    itemData.unidad,
                    itemData.velocidad,
                    itemData.vida
                ));
            });
    
        return Promise.all([fetchUnits, fetchItems]).then(() => {
            this.resetPurchases();
            console.log("Tienda actualizada correctamente");
            console.log(this.units);
            console.log(this.items);
        });
    }
}

export default Shop;
