import Player from "./Player.js";
import Game from "./Game.js";

// Elementos de una ronda
const timerElement = document.getElementById('round-timer');
const roundElement = document.getElementById('round-name');

// Inventario del jugador
const inventoryContainer = document.getElementById("player-objects-container");

// Unidades del jugador
const playerUnitsContainer = document.getElementById("player-units-container")

// Estadísticas del jugador
const playerStarsElement = document.getElementById('game-player-stars');
const playerLifeElement = document.getElementById('game-player-life');
const playerPositionElement = document.getElementById('game-position');

// Tienda
const shopUnitsContainers = document.querySelectorAll('.shop-units-container');
const shopItemsContainers = document.querySelectorAll('.shop-objects-container');
const refreshShopBtns = document.querySelectorAll(".refresh-btn");

// Chat
const chatContainer = document.getElementById("chat-container")

// Actualizar unidades
function updateUnits() {
    
    const unitCells = playerUnitsContainer.querySelectorAll(".unit-container");

    // Rellenamos los primeros huecos con las imágenes de las unidades
    player1.units.forEach((unit, index) => {

        const unitCell = unitCells[index]
        if (unitCell) {

            const missing = unitCell.querySelector(".missing-unit");
            const unitStats = unitCell.querySelector(".unit-stats");
            const unitName = unitStats.querySelector(".unit-name");
            const unitLife = unitStats.querySelector(".unit-life");

            let img = unitCell.querySelector("img");

            if (missing && unit.imagen)  {
                missing.classList.add("hidden");
            }
            
            if (unit.imagen) {

                img.src = unit.imagen || "";
                img.classList.remove("hidden");
                unitLife.classList.remove("hidden")
                unitName.textContent = unit.nombre;

                const progressBar = unitCell.querySelector('.progress-bar');

                if (progressBar) {
                    console.log(unit.getLifePercentage())
                    progressBar.style.width = unit.getLifePercentage() + "%";
                    progressBar.textContent = unit.vida;
    
                    // Actualizar el atributo aria-valuenow (accesibilidad)
                    progressBar.parentElement.setAttribute("aria-valuenow", unit.getLifePercentage());
                }

            }

            const unitTemp = unit;
            // Opción de eliminar el objeto
            unitCell.addEventListener('dblclick', () => {
                player1.sellUnit(unitTemp)
                updatePlayerStats();
                updateInventory();
                
                img.classList.add("hidden")
                missing.classList.remove("hidden")
                unitName.textContent = ""
                unitLife.classList.add("hidden")

            });
        
        }
    });
}

// Actualizar inventario
function updateInventory() {

    const objectCells = inventoryContainer.querySelectorAll(".object-cell");

    // Eliminamos primero todas las imágenes
    objectCells.forEach(cell => {
        const imageInCell = cell.querySelector("img");
        if (imageInCell) {
            imageInCell.remove();
        }
    });
    
    // Rellenamos los primeros huecos con las imágenes de los objetos
    [...player1.inventory].forEach((item, index) => {
        if (objectCells[index]) {
            let img = objectCells[index].querySelector("img");
            
            if (!img && item.imageUrl) {
                img = document.createElement("img");
                img.classList.add("object-img");
                objectCells[index].appendChild(img);
            }
            
            if (img) {
                img.src = item.imageUrl || "";
            }

            const itemTemp = item;
            // Opción de eliminar el objeto
            objectCells[index].addEventListener('dblclick', () => {
                player1.sellItem(itemTemp)
                updatePlayerStats();
                updateInventory();
                img.remove();
            });
        }
    });
} 

// Actualiza la tienda
function updateShop() {
    // Actualiza las celdas de unidades de la tienda
    shopUnitsContainers.forEach((shopUnitsContainer) => {
        player1.shop.units.forEach((unidad, index) => {
            const unidadDiv = shopUnitsContainer.children[index];

            if (unidadDiv) {
                // Reemplaza el nodo para eliminar event listeners previos
                const newUnidadDiv = unidadDiv.cloneNode(true);
                shopUnitsContainer.replaceChild(newUnidadDiv, unidadDiv);

                newUnidadDiv.classList.remove("sold");

                const valorContainer = newUnidadDiv.querySelector('.shop-value-container');
                const valorP = valorContainer.querySelector('.value-num');
    
                valorP.textContent = unidad.price;

                const imagenUnidad = newUnidadDiv.querySelector('.shop-unit-game-img');
                imagenUnidad.setAttribute('src', unidad.imagen);

                // Añade la opción de compra de las unidades
                newUnidadDiv.addEventListener('click', () => {
                    console.log(`Intentando comprar unidad: ${unidad.nombre}`); // TODO: Borrar
                    if (player1.buyUnit(unidad)) {
                        updatePlayerStats();
                        updateInventory();
                        updateUnits();
                        newUnidadDiv.classList.add("sold");
                    }
                });
            }
        });
    });

    // Actualiza las celdas de objetos de la tienda
    shopItemsContainers.forEach((shopItemsContainer) => {
        player1.shop.items.forEach((item, index) => {
            const itemDiv = shopItemsContainer.children[index];

            if (itemDiv) {
                // Reemplaza el nodo para eliminar event listeners previos
                const newItemDiv = itemDiv.cloneNode(true);
                shopItemsContainer.replaceChild(newItemDiv, itemDiv);

                newItemDiv.classList.remove("sold");

                const valorContainer = newItemDiv.querySelector('.shop-value-container');
                const valorP = valorContainer.querySelector('.value-num');
                valorP.textContent = item.price;

                const imagenUnidad = newItemDiv.querySelector('.object-img');
                imagenUnidad.setAttribute('src', item.imageUrl);

                // Añade la opción de compra de los objetos
                newItemDiv.addEventListener('click', () => {
                    if (player1.buyItem(item)) {
                        updatePlayerStats();
                        updateInventory();
                        newItemDiv.classList.add("sold");
                    }
                });
            }
        });
    });
}

function updateChat() {

}
    
// Efecto del botón de refrescar de la tienda
refreshShopBtns.forEach((refreshShopBtn) => {
    refreshShopBtn.addEventListener("click", async () => {
        if (await player1.refreshShop()) {

            updatePlayerStats();
            updateShop();
            
            // Animación de destello

            shopUnitsContainers.forEach((shopItemsContainer) => {
                Array.from(shopItemsContainer.children).forEach((item) => {
                    item.classList.add("flash-effect");
                    setTimeout(() => {
                        item.classList.remove("flash-effect");
                    }, 500);
                });
            });

            shopItemsContainers.forEach((shopItemsContainer) => {
                Array.from(shopItemsContainer.children).forEach((item) => {
                    item.classList.add("flash-effect");
                    setTimeout(() => {
                        item.classList.remove("flash-effect");
                    }, 500);
                });
            });

            refreshShopBtn.classList.add("flash-effect");
            setTimeout(() => {
                refreshShopBtn.classList.remove("flash-effect");
            }, 500);
        }
    });
});


function updatePlayerInventory() {
    
}

const player1 = new Player("Jugador 1");
const player2 = new Player("Jugador 2");

const game = new Game([player1, player2]);

// Función para actualizar el temporizador
const TIME_SHOP = 1000;  

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

refreshShopBtns[0].click();
updateInventory();
updateShop();
updatePlayerStats();
updateRoundNumber();
startTimer();
