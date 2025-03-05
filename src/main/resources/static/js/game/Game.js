class Game {
    constructor() {
        this.round = 1;
        this.isBattleRound = false;
    }

    getRound() {
        return this.round;
    }

    changeRound() {
        this.round++;  // Incrementar la ronda

        this.isBattleRound = !this.isBattleRound; 
        console.log(`Ronda ${this.round}: ${this.isBattleRound ? 'Batalla' : 'Compra'}`);

        // Aquí agregas la lógica específica de tu juego, por ejemplo:
        if (this.isBattleRound) {
            this.startBattle();
        } else {
            this.refreshShop();
        }
    }

    startBattle() {
        console.log('Comienza la batalla entre jugadores');
    }

    refreshShop() {
        console.log('Refrescar la tienda para la ronda de compra');
    }
}

export default Game;