import Unit from "./Unit.js";
import Item from "./Item.js";

class Shop {

    REFRESH_PRICE = 2;

    constructor() {
        this.units = [];  // Unidades disponibles
        this.items = [];  // Objetos disponibles
        this.refresh();
    }

    getRefreshPrice() {
        return this.REFRESH_PRICE;
    }

    // Refrescar la tienda con nuevas unidades y objetos
    refresh() {

        // Actualiza unidades
        fetch('/api/shopUnits?count=3')  
            .then(response => response.json())
            .then(data => {
                // Mapear la respuesta a objetos Unit
                this.units = data.map(unitData => {
                    return new Unit(
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
                    );
                });
            })
            .catch(error => console.error('Error:', error));

        
        // Actualizar items
        fetch('/api/shopItems?count=2')  
            .then(response => response.json())
            .then(data => {
  

                console.log(data)
                this.items = data.map(itemData => {
                    return new Item(
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
                        
                    );
                });
            })
            .catch(error => console.error('Error:', error));

        console.log(this.items)
        console.log("Se ha refrescado la tienda");
    }
}

export default Shop;
