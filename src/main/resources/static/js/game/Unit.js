class Unit {

    static nextId = 1;
    MAX_ITEMS = 2;

    constructor(armadura, daño, descripcion, faccion, id, imagen, nombre, price, velocidad, vida) {
        this.armadura = armadura;
        this.daño = daño;
        this.descripcion = descripcion;
        this.faccion = faccion;
        this.id = id;
        this.imagen = imagen;
        this.nombre = nombre;
        this.price = price;
        this.velocidad = velocidad;
        this.vida = vida;
        this.vidaMax = vida;
        this.items = new Array(this.MAX_ITEMS).fill(null);
    }

    getLifePercentage() {
        return this.vida / this.vidaMax * 100;
    }

    hasFullInventory() {
        return this.items.every(item => item !== null);;
    }

    addItem(item) {
        if (!this.hasFullInventory()) {
            for (let i = 0; i < this.MAX_ITEMS; i++) {
                if (this.items[i] === null) {
                    this.items[i] = item;
                    this.upgradeStats(item);
                    return true;
                }
            }
        }
        return false;
    }

    upgradeStats(item) {
        this.vida += item.life;
        this.vidaMax += item.life;
        this.daño += item.damage;
        this.velocidad += item.velocidad;
        this.armadura += item.armadura;
    }

    degradeStats(item) {
        this.vida -= item.life;
        this.vidaMax -= item.life;
        this.daño -= item.damage;
        this.velocidad -= item.velocidad;
        this.armadura -= item.armadura;
    }


}

export default Unit;
