var config = {
    type: Phaser.AUTO,
    width: 1200,
    height: 600,
    backgroundColor: 'rgba(255, 255, 255, 0)',
    parent: 'game-container',
    transparent: true,
    scene: {
        preload: function () {
            /* IMAGENES */
            this.load.image('tanque', 'img/units/humans/1. Tanque/heavyinfantry-attack-1.png');
            this.load.image('general', 'img/units/humans/2. General/general-breeze1.png');
            this.load.image('caballero', 'img/units/humans/3. Caballero/knight-se-bob1.png');
            this.load.image('arquero', 'img/units/humans/4. Arquero/longbowman-idle-1.png');
            this.load.image('mago', 'img/units/humans/5. Mago/white-mage-idle-1.png');
        },
        
        create: function () {
            /* DEBUG */
            this.debugText = this.add.text(10, 10, 'Debug info:', { fontSize: '8px', fill: '#000' });
            /* DEBUG */

            /* DECLARACIONES */
            this.add.text(300, 50, 'LLTactics - Prototipo', { fontSize: '24px', fill: '#000' });

            this.playerPositions = [100, 200, 300, 400, 500];
            this.enemyPositions = [700, 800, 900, 1000, 1100];

            this.playerSlots = {};
            this.shopSlots = {}; 
            this.draggingCharacter = null; // Objeto arrastrado
            this.previousSlot = null; // Slot origen del objeto arrastrado


            const personajesDisponibles = ['tanque', 'general', 'caballero', 'arquero', 'mago']; // TODO: Añadir rarezas (%)

            // Crear posiciones del JUGADOR
            this.playerPositions.forEach((x, index) => {
                let slot = this.add.rectangle(x, 300, 50, 50, 0x00ff00).setOrigin(0.5);
                slot.setData('index', index);
                this.playerSlots[index] = null;
                slot.setInteractive();
            });

            // Crear posiciones del ENEMIGO
            this.enemyPositions.forEach((x, index) => {
                let slot = this.add.rectangle(x, 300, 50, 50, 0xff0000).setOrigin(0.5);
                slot.setData('index', index);
            });

            // Crear posiciones de la TIENDA
            this.playerPositions.forEach((x, index) => { 
                // Cuadrados interactuables
                let slotTienda = this.add.rectangle(x + 30, this.scale.height - 70, 50, 50, 0x000000).setOrigin(0.5);
                slotTienda.setData('index', index);
                slotTienda.setInteractive();

                // Info del nuevo personaje en tienda
                let personajeRandom = Phaser.Math.RND.pick(personajesDisponibles); // Clase random
                let personaje = this.add.image(x + 30, this.scale.height - 70, personajeRandom).setInteractive(); // Imagen
                personaje.setData('origin', 'shop'); // Ejemplo: if tienda, eliminar dinero; if aliado, intercambiar posiciones...
                personaje.setData('index', index);
                personaje.setData('clase', personajeRandom);
                this.input.setDraggable(personaje);

                // Añadir personaje a tienda
                this.shopSlots[index] = personaje;

                // Estilo de la tienda
                slotTienda.on('pointerover', () => slotTienda.setFillStyle(0x555555));
                slotTienda.on('pointerout', () => slotTienda.setFillStyle(0x000000));
            });

            /* LÓGICA ARRASTRADO */

            // INICIO de arrastre
            this.input.on('dragstart', (pointer, gameObject) => {
                let slotIndex = gameObject.getData('index'); // Informacion origen
                let origin = gameObject.getData('origin');

                // Si origen es tienda o slot de jugador
                if (origin === 'shop' || this.playerSlots[slotIndex] !== null) {
                    // Actualizamos
                    this.draggingCharacter = gameObject;

                    for (let idx in this.playerSlots) {
                        if (this.playerSlots[idx] === gameObject) {
                            this.previousSlot = parseInt(idx); // Guardamos el slot REAL
                            return;
                        }
                    }
                    this.previousSlot = slotIndex;
                } else { 
                    // Inválido
                    this.draggingCharacter = null;
                }
            });

            // MIENTRAS arrastre
            this.input.on('drag', (pointer, gameObject, dragX, dragY) => {
                // Si existe objeto arrastrado
                if (this.draggingCharacter) { 
                    // Update de posiciones
                    this.draggingCharacter.x = dragX;
                    this.draggingCharacter.y = dragY;
                }
            });

            // FINAL arrastre
            this.input.on('dragend', (pointer, gameObject) => {
                if (!this.draggingCharacter) return;

                // Rango de 25px para seleccionar slot
                let droppedOnSlot = this.playerPositions.findIndex(posX => Math.abs(pointer.x - posX) < 25);

                if (droppedOnSlot === this.previousSlot) {
                    this.draggingCharacter.x = this.playerPositions[this.previousSlot];
                    this.draggingCharacter.y = 300;
                    return;
                }
                if (droppedOnSlot !== -1) { // Si se soltó en una posición válida
                    if (this.playerSlots[droppedOnSlot] === null) { 
                        // Si el slot está vacío, mover directamente
                        this.playerSlots[droppedOnSlot] = this.draggingCharacter;
                    } else {
                        // Si hay un personaje, hacer intercambio
                        let personajeEnDestino = this.playerSlots[droppedOnSlot]; // TODO: no funciona la mayor parte del tiempo :C

                        this.playerSlots[this.previousSlot] = personajeEnDestino; // Intercambio destino -> origen
                        personajeEnDestino.x = this.playerPositions[this.previousSlot]; // Update posición de imagen intercambiada
                        personajeEnDestino.y = 300;

                        this.playerSlots[droppedOnSlot] = this.draggingCharacter; // Arrastrado en slot seleccionado
                        this.draggingCharacter.x = this.playerPositions[droppedOnSlot];
                        this.draggingCharacter.y = 300;
                        return;
                    }

                    // Mueve el personaje a su nueva posición
                    this.draggingCharacter.x = this.playerPositions[droppedOnSlot]; // Update posición imagen arrastrada
                    this.draggingCharacter.y = 300;
                    
                    // Vacia el slot anterior
                    if (this.previousSlot !== null) {
                        this.playerSlots[this.previousSlot] = null;
                    }
                } 
                else {
                    // Se suelta en posición inválida -> Pos original
                    if (this.draggingCharacter.getData('origin') === 'shop') { // Vuelve a su posición en tienda
                        this.draggingCharacter.x = this.playerPositions[this.previousSlot] + 30;
                        this.draggingCharacter.y = this.scale.height - 70;
                    } else { // Vuelve a su posición en playerSlots
                        this.draggingCharacter.x = this.playerPositions[this.previousSlot];
                        this.draggingCharacter.y = 300;
                    }
                }

                this.draggingCharacter = null;
                this.previousSlot = null;
            });
        },
        update: function () {
            /* DEBUG */
            let debugInfo = `Dragging: ${this.draggingCharacter ? 'Yes' : 'No'}\n`;
            debugInfo += `Previous Slot: ${this.previousSlot}\n`;
            debugInfo += `Player Slots: ${JSON.stringify(this.playerSlots, null, 2)}\n`;
        
            this.debugText.setText(debugInfo);
            /* DEBUG */
        }
        
    }
};

var game = new Phaser.Game(config);
