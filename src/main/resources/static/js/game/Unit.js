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
    }

    mostrarInfo() {
        console.log(`Unidad: ${this.nombre}`);
        console.log(`Descripción: ${this.descripcion}`);
        console.log(`Facción: ${this.faccion}`);
        console.log(`Vida: ${this.vida}`);
        console.log(`Daño: ${this.daño}`);
        console.log(`Armadura: ${this.armadura}`);
        console.log(`Velocidad: ${this.velocidad}`);
        console.log(`Precio: ${this.price}`);
        console.log(`Imagen: ${this.imagen}`);
    }

}

export default Unit;
