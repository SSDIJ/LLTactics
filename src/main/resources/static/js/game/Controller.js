import Player from "./Player.js";
import Game from "./Game.js";
import Unit from "./Unit.js";

// Elementos de una ronda
const timerElement = document.getElementById('round-timer');
const roundElement = document.getElementById('round-name');
const roundPanel = document.getElementById('round-panel');

// Inventario del jugador
const inventoryContainer = document.getElementById("player-objects-container");

// Unidades del jugador
const playerUnitsContainer = document.getElementById("player-units-container")
const opponentUnitsContainer = document.getElementById("opponent-units-container")

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

function toggleUnitsContainer() {
    if (playerUnitsContainer.classList.contains("no-click"))
        playerUnitsContainer.classList.remove("no-click")
    else playerUnitsContainer.classList.add("no-click")
}

function toggleRoundPanel(text) {

    roundPanel.innerText = text;
    roundPanel.style.display = 'block';

    setTimeout(() => {
        roundPanel.style.display = 'none';
    }, 3000)
    
    
}

function attack(player, isOpponent = false) {
    const unitCont = isOpponent ? opponentUnitsContainer : playerUnitsContainer;
    const unitCells = unitCont.querySelectorAll(".unit-container");

    // Seleccionar la primera unidad (suponiendo que haya al menos una unidad)
    const firstUnitCell = unitCells[0];
    const firstUnitImage = firstUnitCell.querySelector("img");

    if (firstUnitImage) {
        // Asegurarse de que la imagen no esté oculta
        firstUnitImage.classList.remove("hidden");

        // Añadir una clase para animar el movimiento (esto puede ser una animación CSS)
        firstUnitImage.classList.add("attack-move");

        // Si deseas que el movimiento sea breve, podrías usar un setTimeout para eliminar la clase de animación después de un tiempo.
        setTimeout(() => {
            firstUnitImage.classList.remove("attack-move");
        }, 500); // Tiempo en milisegundos (ajústalo según lo que necesites)
    }
}



function updateAllHealthBars(player, opponent) {
    // Actualizamos las barras de vida del jugador
    const playerUnitCont = playerUnitsContainer;
    const playerUnitCells = playerUnitCont.querySelectorAll(".unit-container");

    player.units.forEach((unit, index) => {
        const unitCell = playerUnitCells[index]

        if (!unit.unitID) {
            updateUnits(player)
            return;
        }

        if (unitCell) {
   
            if (unit.imagen) {

                const progressBar = unitCell.querySelector('.progress-bar');

                // Vida
                if (progressBar) {
        
                    progressBar.style.width = unit.getLifePercentage() + "%";
                    progressBar.textContent = unit.vida;
    
                    // Actualizar el atributo aria-valuenow (accesibilidad)
                    progressBar.parentElement.setAttribute("aria-valuenow", unit.getLifePercentage());
                }
            }
        }
    });

    // Actualizamos las barras de vida del oponente
    const opponentUnitCont = opponentUnitsContainer;
    const opponentUnitCells = opponentUnitCont.querySelectorAll(".unit-container");

    opponent.units.forEach((unit, index) => {

        if (!unit.unitID) {
            updateUnits(opponent, true);
            return;
        }
        const unitCell = opponentUnitCells[index]
        if (unitCell) {
   
            if (unit.imagen) {

                const progressBar = unitCell.querySelector('.progress-bar');

                // Vida
                if (progressBar) {
        
                    progressBar.style.width = unit.getLifePercentage() + "%";
                    progressBar.textContent = unit.vida;
    
                    // Actualizar el atributo aria-valuenow (accesibilidad)
                    progressBar.parentElement.setAttribute("aria-valuenow", unit.getLifePercentage());
                }
            }
        }
    });
}



// Actualizar unidades
function updateUnits(player, isOpponent = false) {
    
    const unitCont = isOpponent ? opponentUnitsContainer : playerUnitsContainer;
    const unitCells = unitCont.querySelectorAll(".unit-container");

    // Primero, establecemos todas las unidades como "missing"
    unitCells.forEach(unitCell => {
        const missing = unitCell.querySelector(".missing-unit");
        const unitStats = unitCell.querySelector(".unit-stats");
        const unitName = unitStats.querySelector(".unit-name");
        const unitLife = unitStats.querySelector(".unit-life");
        const unitObjectsContainer = unitCell.querySelector(".unit-object-container");
        const unitItemCells = unitObjectsContainer.querySelectorAll(".object-cell");
        let img = unitCell.querySelector("img");

        // Marcar como missing
        if (missing) {
            missing.classList.remove("hidden");
        }
        
        // Ocultar imagen y vida
        img.src = "";
        img.classList.add("hidden");
        unitLife.classList.add("hidden");
        unitName.textContent = "";

        // Ocultar los objetos
        unitItemCells.forEach(cell => {
            const objImg = cell.querySelector("img");
            objImg.src = "";
            objImg.classList.add("hidden");
        });
    });

    // Rellenamos los primeros huecos con las imágenes de las unidades
    player.units.forEach((unit, index) => {

        const unitCell = unitCells[index]
        if (unitCell) {

            const missing = unitCell.querySelector(".missing-unit");
            const unitStats = unitCell.querySelector(".unit-stats");
            const unitName = unitStats.querySelector(".unit-name");
            const unitLife = unitStats.querySelector(".unit-life");
            const unitObjectsContainer = unitCell.querySelector(".unit-object-container");
            const unitItemCells = unitObjectsContainer.querySelectorAll(".object-cell");

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

                // Vida
                if (progressBar) {
        
                    progressBar.style.width = unit.getLifePercentage() + "%";
                    progressBar.textContent = unit.vida;
    
                    // Actualizar el atributo aria-valuenow (accesibilidad)
                    progressBar.parentElement.setAttribute("aria-valuenow", unit.getLifePercentage());
                }

                // Mostramos los objetos
                unit.items.forEach((item, index) => {
                    const imgObj = unitItemCells[index].querySelector("img");


                    if (item) { 

                        imgObj.src = item.imageUrl;
                        imgObj.classList.remove("hidden");
                    }
                    else {
                        imgObj.src = "";
                        imgObj.classList.add("hidden");
                    }
                })

            }

            const unitTemp = unit;

            if (isOpponent) return;

            // Opción de eliminar la unidad
            img.addEventListener('dblclick', () => {

                player1.sellUnit(unitTemp)
                updatePlayerStats();
                updateInventory();
                
                img.classList.add("hidden")
                missing.classList.remove("hidden")
                unitName.textContent = ""
                unitLife.classList.add("hidden")

                unitItemCells.forEach(cell => {
                    const objImg = cell.querySelector("img");
                    objImg.src = "";
                    objImg.classList.add("hidden")
                })
                
            });

            
        
        }
    });
}

// Actualiza el inventario
function updateInventory() {
    let objectCells = inventoryContainer.querySelectorAll(".object-cell");
    const unitObjects = playerUnitsContainer.querySelectorAll(".unit-object-container");

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

            // Limpiar y volver a agregar event listeners
            let newCell = objectCells[index].cloneNode(true);
            objectCells[index].replaceWith(newCell);

            // Venta del item
            newCell.addEventListener('dblclick', () => {
                newCell.classList.remove("selected");
                img.remove();
                player1.sellItem(itemTemp);
                unitObjects.forEach(unit => {
                    unit.querySelectorAll(".object-cell").forEach(cell => cell.classList.remove("selectable"));
                });
                updatePlayerStats();
                updateInventory();
                
                
            });

            newCell.addEventListener('click', () => {

                objectCells = inventoryContainer.querySelectorAll(".object-cell");
                // Eliminar selección previa
                objectCells.forEach(cell => cell.classList.remove("selected"));
                newCell.classList.add("selected");

                const selectedItem = itemTemp;
                let assigned = false;

                // Habilitar la selección de unidades
                

                unitObjects.forEach(container => {
                    const objContainers = container.querySelectorAll(".object-cell");
                    const unitImage = container.parentElement.querySelector(".unit-game-img")
                    objContainers.forEach(objContainer => {

                        
                        if (unitImage.classList.contains("hidden") || !objContainer.querySelector(".hidden")) {
                            return;
                        }

                        objContainer.classList.add("selectable");

                        // **Reemplazar el nodo para limpiar event listeners previos**
                        let newObjContainer = objContainer.cloneNode(true);
                        objContainer.replaceWith(newObjContainer);

                        newObjContainer.addEventListener('click', function assignItem() {

                            const noc = newObjContainer;

                            if (assigned || !noc.classList.contains("selectable"))
                                return;

                            // Asignar el objeto a la unidad
                            const unitIndex = Array.from(unitObjects).indexOf(container);
                            player1.units[unitIndex].addItem(selectedItem);
                            assigned = true;
                            player1.removeFromInventory(selectedItem)

                            // Limpiar selección
                            objectCells.forEach(cell => cell.classList.remove("selected"));
                            unitObjects.forEach(unit => {
                                unit.querySelectorAll(".object-cell").forEach(cell => cell.classList.remove("selectable"));
                            });

                            // Eliminar el event listener después de ejecutarse
                            newObjContainer.removeEventListener('click', assignItem);

                            updateUnits(player1);
                            updateInventory();
                        }, { once: true });
                    });
                });
            });
        }
    });
}

function openShop() {
    console.log("TIENDA ABIERTA");
    shopUnitsContainers.forEach((shopUnitsContainer) => {
        Array.from(shopUnitsContainer.children).forEach((c) => {
            c.classList.remove("closed");
        })
    })
}

function closeShop() {

    console.log("TIENDA CERRADA");
    shopUnitsContainers.forEach((shopUnitsContainer) => {
        Array.from(shopUnitsContainer.children).forEach((c) => {
            c.classList.add("closed");
        })
    })
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
                        updateUnits(player1);
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


const player1 = new Player("Jugador 1");
const player2 = new Player("Jugador 2");

player2.units[0] = new Unit(10, 100, "", "", 500, "/img/units/trolls/1. TTanque/great-troll.png", "U1", 2, 5, 2000)

updateUnits(player2, true);

const game = new Game([player1, player2]);

// Función para actualizar el temporizador
const TIME_SHOP = 10;  
const TIME_AFTER_BATTLE = 3;

let timeLeft = TIME_SHOP;


function updateRoundNumber() {
    roundElement.innerText = `Ronda ${game.getRound()}`;
}

function updatePlayerStats() {
    playerStarsElement.innerText = player1.getStars();
    playerLifeElement.innerText = player1.getHealth();
}

function winAnimation(isOpponent) {
    const unitCont = isOpponent ? opponentUnitsContainer : playerUnitsContainer;
    const unitCells = unitCont.querySelectorAll(".unit-container");


    unitCells.forEach(unitCell => {
        const img = unitCell.querySelector("img");


        if (img) {
            // Añadir el efecto "flash" a la imagen
            img.classList.add("win-effect");

            // Eliminar el efecto "flash" después de 500ms
            setTimeout(() => {
                img.classList.remove("win-effect");
            }, 2000);
        }
        
        
        
    });
}


// Iniciar temporizador
async function startTimer() {

    // Volvemos a iniciar el temporizador cada nueva ronda
    const intervalId = setInterval(async () => {
        if (timeLeft <= 0) {

            clearInterval(intervalId);
            
            game.changeRound();
            game.resetHealth();

            updateRoundNumber();
            toggleUnitsContainer();
            
            if (game.isBattleRound) {

                toggleRoundPanel("Batalla")
                closeShop();

                timerElement.innerText = `BATALLA`;

                const intervalHealthsId = setInterval(() => {
                    updateAllHealthBars(player1, player2);
                }, 300);

                const player1Wins = await game.startBattle();

                clearInterval(intervalHealthsId);
                winAnimation(!player1Wins);
                
            }
            else {
                toggleRoundPanel("Compra")
                openShop();
            }

            timeLeft = game.isBattleRound ? TIME_AFTER_BATTLE : TIME_SHOP;

            updateUnits(player1);
            updateUnits(player2, true);
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

toggleRoundPanel("Compra")
updateInventory();
updateShop();
updatePlayerStats();
updateRoundNumber();
startTimer();
