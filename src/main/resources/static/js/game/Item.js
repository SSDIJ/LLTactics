class Item {
    constructor(name, effect) {
        this.name = name;
        this.effect = effect; // Podría ser un incremento en atributos
    }

    applyEffect(unit) {
        this.effect(unit);
    }
}

export default Item;