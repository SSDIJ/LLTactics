import Unit from "./Unit.js";

class Game {

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

        while (true) {

            let unit1 = player1.units.slice().reverse().find(u => u.unitID && u.unitID !== null);
            let unit2 = player2.units.find(u => u.unitID && u.unitID !== null);

            if (!unit1 || !unit2) break; // End fight if no valid units

            while (unit1.health > 0 && unit2.health > 0) {
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

        // Determine the winner
        if (player1.units.every(u => u.health <= 0 || !u.unitID)) {
            player1.health -= 5;
            return false;
        } else if (player2.units.every(u => u.health <= 0 || !u.unitID)) {
            player2.health -= 5;
            return true;
        }
    }

    // Perform an attack with delay
    async attackWithDelay(unit1, unit2) {
        await new Promise(resolve => setTimeout(resolve, 200));
        this.attack(unit1, unit2);
    }

    attack(attacker, defender) {
        let damage = Math.max(attacker.damage - defender.armor, 1); // Minimum damage of 1
        defender.health -= damage;
    }

    resetHealth() {
        this.players.forEach(p => {
            p.resetUnitHealth();
        });
    }
}

export default Game;
