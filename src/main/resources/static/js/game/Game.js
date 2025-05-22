import Unit from "./Unit.js";

class Game {
    // Representa el estado de una partida

    constructor(players) {
        this.round = 1;
        this.isBattleRound = false;
        this.players = players;
        this.preferredPlayer = null;
    }

    getRound() {
        return this.round;
    }

    setPreferredPlayer(playerName) {
        this.preferredPlayer = playerName;
    }
    
    changeRound() {
        this.round++;  // Increment the round
        this.isBattleRound = !this.isBattleRound;
    }

    isBattleRound() {
        return this.isBattleRound;
    }

    // Comienza el combate entre unidades
    async startBattle() {
        if (this.isBattleRound) {
            return await this.fight(this.players[0], this.players[1]);
        }
    }

    async fight(player1, player2) {

        let unit1 = player1.units.slice().reverse().find(u => u.unitID && u.unitID !== null);
        let unit2 = player2.units.find(u => u.unitID && u.unitID !== null);

        if (!unit1) player1.buyUnit(player1.getDefaultUnit());
        if (!unit2) player2.buyUnit(player2.getDefaultUnit(), false);

        let ok = true;
        while (ok) {

            // Cogemos la primera unidad válida de cada jugador
            let unit1 = player1.units.slice().reverse().find(u => u.unitID && u.unitID !== null);
            let unit2 = player2.units.find(u => u.unitID && u.unitID !== null);

            if (!unit1 || !unit2) ok = false; // Salimos si no hay unidades válidas

            // Dos unidades se atacan en bucle hasta que una derrota a la otra
            while (unit1.health > 0 && unit2.health > 0 && ok) {

                // Comparamos las velocidades de las unidades para determinar quién ataca primero
                if (unit1.speed > unit2.speed) {
                    await this.attackWithDelay(unit1, unit2);
                    if (unit2.health > 0) await this.attackWithDelay(unit2, unit1);
                } else if (unit2.speed > unit1.speed) {
                    await this.attackWithDelay(unit2, unit1);
                    if (unit1.health > 0) await this.attackWithDelay(unit1, unit2);
                } else {

                    // Velocidades iguales → decidir por preferencia
                    if (player1.name == this.preferredPlayer) {
                        await this.attackWithDelay(unit1, unit2);
                        if (unit2.health > 0) await this.attackWithDelay(unit2, unit1);
                    } else {
                        await this.attackWithDelay(unit2, unit1);
                        if (unit1.health > 0) await this.attackWithDelay(unit1, unit2);
                    }
                }
            }

            if (unit1.health <= 0) {
                let nullUnit = player1.getNullUnit();
                player1.units = player1.units.map(u => (u === unit1 ? nullUnit : u));
            }
            if (unit2.health <= 0) {
                let nullUnit = player2.getNullUnit();
                player2.units = player2.units.map(u => (u === unit2 ? nullUnit : u));
            }
        }

        // Determina el ganador
        if (player1.units.every(u => u.health <= 0 || !u.unitID)) {
            return false;
        } else if (player2.units.every(u => u.health <= 0 || !u.unitID)) {
            return true;
        }
    }

    // Realiza un ataque (con delay para la animación)
    async attackWithDelay(unit1, unit2) {
        await new Promise(resolve => setTimeout(resolve, 200));
        this.attack(unit1, unit2);
    }

    // Realiza un ataque de una unidad a otra
    attack(attacker, defender) {
        let damage = Math.max(attacker.damage - defender.armor, 1); // Daño mínimo = 1
        defender.health -= damage;
    }

    // Resetea la vida de todas las unidades del jugador
    resetHealth() {
        this.players.forEach(p => {
            p.resetUnitHealth();
        });
    }
}

export default Game;
