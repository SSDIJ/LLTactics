class Item {

    constructor(armor, damage, description, id, imageUrl, name, price, unit, velocity, life) {

        this.armor = parseInt(armor, 10) || 0;
        this.damage = parseInt(damage, 10) || 0;;
        this.description = description;
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.velocity = parseInt(velocity, 10) || 0;;
        this.life = parseInt(life, 10) || 0;;
    }

    static fromItem(item) {
        return new Item(
            item.armor,
            item.damage,
            item.description,
            item.id,
            item.imageUrl,
            item.name,
            item.price,
            item.unit,
            item.velocity,
            item.life
        );
    }
    
}

export default Item;