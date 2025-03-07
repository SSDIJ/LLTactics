class Unit {
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
        this.items = []
    }

    getLifePercentage() {
        return this.vida / this.vidaMax * 100;
    }

}

export default Unit;
