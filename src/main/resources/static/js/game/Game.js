import Unit from "./Unit.js";

class Game {

    constructor(players) {
        this.round = 1;
        this.isBattleRound = false;
        this.players = players
    }

    getRound() {
        return this.round;
    }

    changeRound() {
        this.round++;  // Incrementar la ronda

        this.isBattleRound = !this.isBattleRound; 
        console.log(`Ronda ${this.round}: ${this.isBattleRound ? 'Batalla' : 'Compra'}`);

    }

    isBattleRound() {
        return this.isBattleRound;
    }

    async startBattle() {
        if (this.isBattleRound) {
            return await this.fight(this.players[0], this.players[1])
        }
    }


    async fight(player1, player2) {

        console.log('Comienza la batalla entre jugadores');

        let unit1 = player1.units.slice().reverse().find(u => u.unitID && u.unitID !== null);
        let unit2 = player2.units.find(u => u.unitID && u.unitID !== null);

        if (!unit1) player1.buyUnit(player1.getDefaultUnit())
        if (!unit2) player2.buyUnit(player2.getDefaultUnit(), false)


        while (true) {

            let unit1 = player1.units.slice().reverse().find(u => u.unitID && u.unitID !== null);

            console.log(player2.units)
            let unit2 = player2.units.find(u => u.unitID && u.unitID !== null);
    
            if (!unit1 || !unit2) break; // Si no hay unidades válidas, se termina el combate
    
            console.log(`\n🔹 ${unit1.nombre} (de ${player1.name}) vs ${unit2.nombre} (de ${player2.name})`);
    
            while (unit1.vida > 0 && unit2.vida > 0) {
                if (unit1.velocidad >= unit2.velocidad) {
                    await this.attackWithDelay(unit1, unit2);
                    if (unit2.vida > 0) await this.attackWithDelay(unit2, unit1);
                } else {
                    await this.attackWithDelay(unit2, unit1);
                    if (unit1.vida > 0) await this.attackWithDelay(unit1, unit2);
                }
            }
    
            if (unit1.vida <= 0) {
                console.log(`❌ ${unit1.nombre} de ${player1.name} ha sido derrotado.`);
                let nullUnit = player1.getNullUnit();
                player1.units = player1.units.map(u => (u === unit1 ? nullUnit : u));
            }
            if (unit2.vida <= 0) {
                console.log(`❌ ${unit2.nombre} de ${player2.name} ha sido derrotado.`);
                let nullUnit = player2.getNullUnit();
                player2.units = player2.units.map(u => (u === unit2 ? nullUnit : u));
            }
        }
    
        // Determinar el ganador del enfrentamiento
        if (player1.units.every(u => u.vida <= 0 || !u.unitID)) {
            player1.health -= 5;
            console.log(`🏆 ${player2.name} gana el duelo contra ${player1.name}`);
            return false;
        } else if (player2.units.every(u => u.vida <= 0 || !u.unitID)) {
            player2.health -= 5;
            console.log(`🏆 ${player1.name} gana el duelo contra ${player2.name}`);
            return true;
        }
    }
    
    // Función para realizar el ataque con un retraso
    async attackWithDelay(unit1, unit2) {
        await new Promise(resolve => setTimeout(resolve, 200));
        this.attack(unit1, unit2);  // Llamada al método de ataque
    }
    

    attack(attacker, defender) {
        let damage = Math.max(attacker.daño - defender.armadura, 1); // Daño mínimo de 1
        defender.vida -= damage;
        console.log(`⚔️ ${attacker.nombre} ataca a ${defender.nombre}, causando ${damage} de daño. Vida restante: ${defender.vida}`);
    }

    resetHealth() {
        this.players.forEach(p => {
            p.resetUnitHealth()
        });
    }
}

export default Game;