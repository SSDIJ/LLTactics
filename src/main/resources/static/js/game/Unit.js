import Item from "./Item.js";

class Unit {

    static nextId = 1;
    MAX_ITEMS = 2;

    constructor(armor, damage, description, faction, id, image, name, price, speed, health) {
        this.armor = armor;
        this.damage = damage;
        this.description = description;
        this.faction = faction;
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.speed = speed;
        this.health = health;
        this.maxHealth = health;
        this.unitID = name ? Unit.nextId++ : null;
        this.items = new Array(this.MAX_ITEMS).fill(null);
    }

    static fromUnit(otherUnit) {
        const newUnit = new Unit(
            otherUnit.armor,
            otherUnit.damage,
            otherUnit.description,
            otherUnit.faction,
            otherUnit.id,
            otherUnit.image,
            otherUnit.name,
            otherUnit.price,
            otherUnit.speed,
            otherUnit.health
        );

        newUnit.unitID = otherUnit.unitID;
        newUnit.items = [...otherUnit.items].map((item) => {
            if (item) {
                return Item.fromItem(item);
            }
            else {
                return item;
            }
            
        });
        return newUnit;
    }

    getHealthPercentage() {
        return this.health / this.maxHealth * 100;
    }

    hasFullInventory() {
        return this.items.every(item => item !== null);
    }

    addItem(item) {
        if (!this.hasFullInventory()) {
            for (let i = 0; i < this.MAX_ITEMS; i++) {
                if (this.items[i] === null) {
                    this.items[i] = item;
                    this.upgradeStats(item);
                    console.log("Item has been added");
                    return true;
                }
            }
        }
        return false;
    }

    getReport() {
        let report = "\n";
        report += this.name;
        report += "\n";
        report += this.description;
        report += "\n\n";

        report += "VIDA (" + this.health + " / " + this.maxHealth + ")\n";
        report += "ARMADURA (" + this.armor + ")\n";
        report += "DAÑO (" + this.damage + ")\n";
        report += "VELOCIDAD (" + this.speed + ")\n";
        return report;
    }

    upgradeStats(item) {
        this.health += item.life;
        this.maxHealth += item.life;
        this.damage += item.damage;
        this.speed += item.velocity;
        this.armor += item.armor;
    }

    degradeStats(item) {
        this.health -= item.life;
        this.maxHealth -= item.life;
        this.damage -= item.damage;
        this.speed -= item.velocity;
        this.armor -= item.armor;
    }
}

export default Unit;