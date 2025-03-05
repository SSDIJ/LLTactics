import Player from "./Player.js";
import Game from "./Game.js";

// Elementos de una ronda
const timerElement = document.getElementById('round-timer');
const roundElement = document.getElementById('round-name');

// Estadísticas del jugador
const playerStarsElement = document.getElementById('game-player-stars');
const playerLifeElement = document.getElementById('game-player-life');
const playerPositionElement = document.getElementById('game-position');

// Tienda
const shopUnitsContainer = document.getElementById('shop-units-container');
const refreshShopBtn = document.getElementById("refresh-container")


function updateShop() {
    // Iterar a través de las unidades y actualizar los elementos existentes en el contenedor
    player1.shop.units.forEach((unidad, index) => {
        // Seleccionar la carta correspondiente en el contenedor
        const unidadDiv = shopUnitsContainer.children[index];

        console.log(unidadDiv)

        if (unidadDiv) {
            // Seleccionar el contenedor de valor y actualizarlo
            const valorContainer = unidadDiv.querySelector('.shop-value-container');
            const valorP = valorContainer.querySelector('.value-num');
            valorP.textContent = unidad.price;

            // Seleccionar la imagen de la unidad y actualizarla
            const imagenUnidad = unidadDiv.querySelector('.shop-unit-game-img');
            imagenUnidad.setAttribute('src', unidad.imagen);
        }
    });
}
    


refreshShopBtn.addEventListener("click", () => {
    if (player1.refreshShop()) {

        updatePlayerStats();
        updateShop();
        // Animación de destello
        refreshShopBtn.classList.add('flash-effect');
        setTimeout(function() {
            refreshShopBtn.classList.remove('flash-effect');
        }, 500);
        
    }
    
})

const player1 = new Player("Jugador 1");
const player2 = new Player("Jugador 2");

const game = new Game([player1, player2]);

// Función para actualizar el temporizador
const TIME_SHOP = 15;  

let timeLeft = TIME_SHOP;


function updateRoundNumber() {
    roundElement.innerText = `Ronda ${game.getRound()}`;
}

function updatePlayerStats() {
    playerStarsElement.innerText = player1.getStars();
    playerLifeElement.innerText = player1.getHealth();
}

// Iniciar temporizador
function startTimer() {

    // Volvemos a iniciar el temporizador cada nueva ronda
    const intervalId = setInterval(() => {
        if (timeLeft <= 0) {
            clearInterval(intervalId);
            timeLeft = TIME_SHOP;

            game.changeRound();
            
            updateRoundNumber();
            updatePlayerStats();

            startTimer();
        } else {
            const minutes = Math.floor(timeLeft / 60);
            const seconds = timeLeft % 60;
            timerElement.innerText = `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
            timeLeft--;  // Restar un segundo
        }
    }, 1000);
}

updatePlayerStats();
updateRoundNumber()
startTimer();
