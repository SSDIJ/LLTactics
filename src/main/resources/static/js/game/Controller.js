import Player from "./Player.js";
import Game from "./Game.js";
import Unit from "./Unit.js";

let playerReady = false;

// ID de la partida
const roomId = sessionStorage.getItem('roomId');
const player1NameContainer = document.getElementById("player1Name");
const player2NameContainer = document.getElementById("player2Name");;

// Elementos de una ronda
const timerElement = document.getElementById('round-timer');
const roundElement = document.getElementById('round-name');
const roundPanel = document.getElementById('round-panel');
const readyBtn = document.getElementById('ready-btn');

// Inventario del jugador
const inventoryContainer = document.getElementById("player-objects-container");
const opponentInventoryContainer = document.getElementById("opponent-object-container");

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
const chatBoxes = document.querySelectorAll(".chat-box");
const chatInputs = document.querySelectorAll(".chat-input");
const chatSendBtn = document.getElementById("chat-send-btn");
const reportBtn = document.querySelector(".report-btn");
const reportBtnOffCanvas = document.querySelector(".report-btn-offCanvas");

function toggleUnitsContainer() {
    if (playerUnitsContainer.classList.contains("no-click"))
        playerUnitsContainer.classList.remove("no-click")
    else playerUnitsContainer.classList.add("no-click")
}

function showWinnerContainer(winner) {
    const container = document.getElementById('winner-container');
    const winnerName = document.getElementById('winnerName');
    winnerName.textContent = winner
    container.style.display = 'flex';
    setTimeout(() => {
        container.classList.add('show');
    }, 10);
}

function toggleRoundPanel(text) {

    roundPanel.innerText = text;
    roundPanel.style.display = 'block';

    setTimeout(() => {
        roundPanel.style.display = 'none';
    }, 3000)
    
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
   
            if (unit.image) {

                const progressBar = unitCell.querySelector('.progress-bar');

                // Vida
                if (progressBar) {
        
                    progressBar.style.width = unit.getHealthPercentage() + "%";
                    progressBar.textContent = unit.health;
    
                    // Actualizar el atributo aria-valuenow (accesibilidad)
                    progressBar.parentElement.setAttribute("aria-valuenow", unit.getHealthPercentage());
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
   
            if (unit.image) {

                const progressBar = unitCell.querySelector('.progress-bar');

                // Vida
                if (progressBar) {
        
                    progressBar.style.width = unit.getHealthPercentage() + "%";
                    progressBar.textContent = unit.health;
    
                    // Actualizar el atributo aria-valuenow (accesibilidad)
                    progressBar.parentElement.setAttribute("aria-valuenow", unit.getHealthPercentage());
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

            if (missing && unit.image)  {
                missing.classList.add("hidden");
            }
            
            if (unit.image) {

                img.setAttribute('src', `/user/${unit.id}/heroe`);
                img.classList.remove("hidden");
                unitLife.classList.remove("hidden")
                unitName.textContent = unit.name;

                const progressBar = unitCell.querySelector('.progress-bar');

                // Vida
                if (progressBar) {
        
                    progressBar.style.width = unit.getHealthPercentage() + "%";
                    progressBar.textContent = unit.health;
    
                    // Actualizar el atributo aria-valuenow (accesibilidad)
                    progressBar.parentElement.setAttribute("aria-valuenow", unit.getHealthPercentage());
                }

                // Mostramos los objetos
                unit.items.forEach((item, index) => {
                    const imgObj = unitItemCells[index].querySelector("img");

                    if (item) { 
                        imgObj.setAttribute('src', `/user/${item.id}/objeto`);
                        imgObj.classList.remove("hidden");
                    }
                    else {
                        imgObj.src = "";
                        imgObj.classList.add("hidden");
                    }
                })

            }

            if (isOpponent) return;

            // Opción de eliminar la unidad
            const clonedImg = img.cloneNode(true); // Clonamos el nodo de la imagen
            clonedImg.addEventListener('dblclick', () => {

                sendAction({
                    "actionType": "SELL_UNIT",
                    "playerName": player1.name,
                    "actionDetails": JSON.stringify(unit)
                });

                updatePlayerStats();

                unitItemCells.forEach(cell => {
                    const objImg = cell.querySelector("img");
                    objImg.src = "";
                    objImg.classList.add("hidden");
                })
                
            }, { once: true });

            // Reemplazamos la imagen original con el clon
            img.replaceWith(clonedImg); 
        
        }
    });
}

// Actualiza el inventario
function updateInventory(isOpponent = false) {

    const player = isOpponent ? player2 : player1;
    const invContainer = isOpponent ? opponentInventoryContainer: inventoryContainer;
    const unitsContainer = isOpponent ? opponentUnitsContainer : playerUnitsContainer;
    let objectCells = invContainer.querySelectorAll(".object-cell");
    const unitObjects = unitsContainer.querySelectorAll(".unit-object-container");

    // Eliminamos primero todas las imágenes
    objectCells.forEach(cell => {
        const imageInCell = cell.querySelector("img");
        if (imageInCell) {
            imageInCell.remove();
        }
    });


    // Rellenamos los primeros huecos con las imágenes de los objetos
    [...player.inventory].forEach((item, index) => {
        if (objectCells[index]) {
            let img = objectCells[index].querySelector("img");

            if (!img && item.imageUrl) {
                img = document.createElement("img");
                img.classList.add("object-img");
                objectCells[index].appendChild(img);
            }

            if (img) {
                img.setAttribute('src', `/user/${item.id}/objeto`);
            }

            const itemTemp = item;

            // Limpiar y volver a agregar event listeners
            let newCell = objectCells[index].cloneNode(true);
            objectCells[index].replaceWith(newCell);

            if (isOpponent) return;

            // Venta del item
            newCell.addEventListener('dblclick', () => {
                newCell.classList.remove("selected");
                img.remove();
                player1.sellItem(itemTemp);
                unitObjects.forEach(unit => {
                    unit.querySelectorAll(".object-cell").forEach(cell => cell.classList.remove("selectable"));
                });

                sendAction({
                    "actionType": "SELL_ITEM",
                    "playerName": player1.name,
                    "actionDetails": JSON.stringify(itemTemp)}
                );

                updatePlayerStats();
                updateInventory();
                
            });

            newCell.addEventListener('click', () => {

                objectCells = invContainer.querySelectorAll(".object-cell");
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
                            assigned = true;
                            selectedItem.unitUnitId = player1.units[unitIndex].unitID

                            sendAction({
                                "actionType": "ASSIGN_ITEM_TO_UNIT",
                                "playerName": player1.name,
                                "actionDetails": JSON.stringify(selectedItem)
                            });

                            // Limpiar selección
                            objectCells.forEach(cell => cell.classList.remove("selected"));
                            unitObjects.forEach(unit => {
                                unit.querySelectorAll(".object-cell").forEach(cell => cell.classList.remove("selectable"));
                            });

                            // Eliminar el event listener después de ejecutarse
                            newObjContainer.removeEventListener('click', assignItem);

                        }, { once: true });
                    });
                });
            });
        }
    });
}

function openShop() {
    shopUnitsContainers.forEach((shopUnitsContainer) => {
        Array.from(shopUnitsContainer.children).forEach((c) => {
            c.classList.remove("closed");
        })
    })

    shopItemsContainers.forEach((shopItemsContainer) => {
        Array.from(shopItemsContainer.children).forEach((c) => {
            c.classList.remove("closed");
        })
    })

    refreshShopBtns.forEach((rb) => {
        rb.classList.remove("closed");
    })

    readyBtn.classList.remove("closed")
}

function closeShop() {
    shopUnitsContainers.forEach((shopUnitsContainer) => {
        Array.from(shopUnitsContainer.children).forEach((c) => {
            c.classList.add("closed");
        })
    })

    shopItemsContainers.forEach((shopItemsContainer) => {
        Array.from(shopItemsContainer.children).forEach((c) => {
            c.classList.add("closed");
        })
    })

    refreshShopBtns.forEach((rb) => {
        rb.classList.add("closed");
    })
    

    readyBtn.classList.add("closed")
}

function closeInventory() {
    inventoryContainer.classList.add("closed")
}

function openInventory() {
    inventoryContainer.classList.remove("closed")
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
                imagenUnidad.setAttribute('src', `/user/${unidad.id}/heroe`);

                // Añade la opción de compra de las unidades
                newUnidadDiv.addEventListener('click', () => {
                    
                    
                    if (player1.canBuy(unidad)) {

                        sendAction({
                            "actionType": "BUY_UNIT",
                            "playerName": player1.name,
                            "actionDetails": JSON.stringify(unidad)

                        });

                        updatePlayerStats();
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
                imagenUnidad.setAttribute('src', `/user/${item.id}/objeto`);

                // Añade la opción de compra de los objetos
                newItemDiv.addEventListener('click', () => {
                    if (player1.buyItem(item)) {

                        sendAction({
                            "actionType": "BUY_ITEM",
                            "playerName": player1.name,
                            "actionDetails": JSON.stringify(item)

                        });

                        updatePlayerStats();
                        newItemDiv.classList.add("sold");
                    }
                });
            }
        });
    });
}

const player1 = new Player("J1");
const player2 = new Player("J2");
    
// Efecto del botón de refrescar de la tienda
refreshShopBtns.forEach((refreshShopBtn) => {
    refreshShopBtn.addEventListener("click", async () => {
        if (player1.canRefreshShop()) {

            sendAction({
                "actionType": "REFRESH_SHOP",
                "playerName": player1.name,
                "actionDetails": ""
            });

            updatePlayerStats();
            
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




updateUnits(player2, true);

const game = new Game([player1, player2]);

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

// ------ CHAT -------


// Función para enviar un mensaje al servidor
async function sendMessage(chatInput) {

    const message = chatInput.value.trim();
    if (message === "") return;

    const messageAction = {
        "actionType": "SEND_MESSAGE",
        "playerName": player1.name,
        "actionDetails": message
    };

    sendAction(messageAction);

    chatInput.value = ""; // Limpia el campo de entrada
}


// Función para mostrar un mensaje en el chat
function displayMessage(messageAction) {

    const message = messageAction.message.text;
    const time =  messageAction.message.timestamp;
    
    const date = new Date(time * 1000);
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
    const timeFormatted = `${hours}:${minutes}`;

    const messageElement = document.createElement("div");
    messageElement.classList.add("chat-message");

    // Hora del mensaje
    const timeElement = document.createElement("span");
    timeElement.classList.add("chat-time");
    timeElement.textContent = `[${timeFormatted}] `;

    // Nombre de usuario
    const userElement = document.createElement("span");
    userElement.classList.add("chat-username");
    userElement.textContent = `${messageAction.message.playerName}:`;
    userElement.style.cursor = "pointer"; // Cambia el cursor

    // Texto del mensaje
    const textElement = document.createElement("span");
    textElement.classList.add("chat-text");
    textElement.textContent = ` ${message}`;

    // Ensamblar el mensaje
    messageElement.appendChild(timeElement);
    messageElement.appendChild(userElement);
    messageElement.appendChild(textElement);

    chatBoxes.forEach(chatBox => {
        const clonedMessage = messageElement.cloneNode(true);
        chatBox.appendChild(clonedMessage);
        chatBox.scrollTop = chatBox.scrollHeight;
    });
}

// Configurar el botón de reporte
if (reportBtn) {
    reportBtn.addEventListener("click", () => {
        console.log("Reportar chat (pantalla completa)");
        // TODO: Lógica de reportar chat
    });
}
if (reportBtnOffCanvas) {
    reportBtnOffCanvas.addEventListener("click", () => {
        console.log("Reportar chat (offcanvas)");
        // TODO: Lógica de reportar chat
    });
}

// Configurar el botón de enviar y la tecla Enter
chatSendBtn.addEventListener("click", () => {
    console.log("click");
    const chatInput = document.querySelector(".chat-input"); // Asegúrate de seleccionar el input correcto
    sendMessage(chatInput);
});
chatInputs.forEach(chatInput => {
    chatInput.addEventListener("keypress", function (event) {
        if (event.key === "Enter") {
            sendMessage(chatInput);
        }
    });  
})

function readyForNextRound() {
    go("/game/ready/" + roomId, "POST");
}

function closeAll() {
    closeShop();
    closeInventory();
}


readyBtn.addEventListener("click", () => {
    readyForNextRound();
    closeAll();
})

document.addEventListener("DOMContentLoaded", () => {
    chatBoxes.forEach(chatBox => {
        chatBox.scrollTop = chatBox.scrollHeight;
    });
})

// Iniciar temporizador
async function startTimer(timeLeft) {

    // Volvemos a iniciar el temporizador cada nueva ronda
    const intervalId = setInterval(async () => {
        if (timeLeft <= 0) {
            clearInterval(intervalId);
        } else {
            const minutes = Math.floor(timeLeft / 60);
            const seconds = timeLeft % 60;
            timerElement.innerText = `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
            timeLeft--;  // Restar un segundo
        }
    }, 1000);
}


// --- WEBSOCKETS ---


document.addEventListener("DOMContentLoaded", () => {  

    player1.name = player1NameContainer.innerText;
    player2.name = player2NameContainer.innerText;
    
    const socketUrl = sessionStorage.getItem("socketUrl")
    ws.initialize(socketUrl, ["/topic/game/" + roomId]);

    ws.receive = (action) => {
        processAction(action)
    };

    setTimeout(() => {
        sendAction({
            actionType: "GENERAL",
            playerName: player1.name,
            actionDetails: ""
        });
    }, 100);

});


function sendAction(action) {
    go("/game/action/" + roomId, "POST", action);
}

async function processAction(action) {

    const name1 = player1.name;
    const name2 = player2.name;


    if (!(action.phase === "buy" || action.phase === "battle")) {
        player1.health = action[`health_${name1}`];
        player2.health = action[`health_${name2}`];

        player1.stars = action[`stars_${name1}`];
        player2.stars = action[`stars_${name2}`];
    }
    

    updatePlayerStats();

    if (action.isWinner !== undefined) {
        showWinnerContainer(action.winner);
        return;
    }

    if (action.updateAll !== undefined) {

        player1.updateUnits((action[`units_${name1}`] || []).reverse());
        updateUnits(player1);

        player2.updateUnits(action[`units_${name2}`] || []);
        updateUnits(player2, true);

        player1.updateItems(action[`items_${name1}`] || []);
        updateInventory();

        player2.updateItems(action[`items_${name2}`] || []);
        updateInventory(true);

        const shop1 = action[`shop_${name1}`];
        if (shop1) {
            player1.updateShop(shop1);
            updateShop()
        }


        player1.name = action[`name_${name1}`] || name1;
        player2.name = action[`name_${name2}`] || name2;

        game.setPreferredPlayer(action["preferredPlayer"]);

        playerPositionElement.innerText = player1.health >= player2.health ? "#1" : "#2";

        game.round = action["currentRound"];
        updateRoundNumber();

        
        if (action["currentPhase"] == "BUY") {
            console.log(1)
            openShop();
            timerElement.innerText = "COMPRA";
        }
        else if (action["currentPhase"] == "BATTLE") {
            closeAll();
            timerElement.innerText = "BATALLA";

            if (!playerReady) {
                playerReady = true
                const delay = Math.floor(Math.random() * (10000 - 3000 + 1)) + 3000;
                await new Promise(resolve => setTimeout(resolve, delay));
                readyForNextRound();
            } 
            
        }
       

        const ready = action["ready_" + name1]

        if (ready || action["currentRound"] == 0) {
            closeAll();
        }
        
        return;
    }

    const actor = action["actor"]
    const isOpponent = actor == player2.name;
    const player = isOpponent ? player2 : player1;
    const playerName = player.name;


    // Actualizar unidades
    if (action.updateUnits) {
        const units = action[`units_${playerName}`] || [];
        player.updateUnits(isOpponent ? units : units.reverse());
        updateUnits(player, isOpponent);
    }

    // Actualizar inventario
    if (action.updateItems) {
        const items = action[`items_${playerName}`] || [];
        player.updateItems(items);
        updateInventory(isOpponent);
    }

    // Actualizar tienda
    if (action.updateShop) {
        const shop = action[`shop_${playerName}`];
        if (shop) {
            player.updateShop(shop);
            updateShop();
        }
        
    }

    // Añadir mensaje
    if (action.newMessage) {
        displayMessage(action);
    }

    if (action.phase === "buy") {
        game.round = action.round;

        updateRoundNumber();
        timerElement.innerText = `COMPRA`;

        toggleRoundPanel("Compra");
        openShop();
        openInventory();

        if (game.round !== 1) {
            game.changeRound();
        }
    } else if (action.phase === "battle") {
        game.round = action.round;
        updateRoundNumber();
        game.changeRound();

        toggleRoundPanel("Batalla");
    
        timerElement.innerText = `BATALLA`;

        const intervalHealthsId = setInterval(() => {
            updateAllHealthBars(player1, player2);
        }, 300);

        playerReady = false;

        const player1Wins = await game.startBattle();

        await new Promise(resolve => setTimeout(resolve, 1000));
        clearInterval(intervalHealthsId);
        winAnimation(!player1Wins);

        playerReady = true;

        const delay = Math.floor(Math.random() * (10000 - 3000 + 1)) + 3000;
        await new Promise(resolve => setTimeout(resolve, delay));
        readyForNextRound();
    }   
}



