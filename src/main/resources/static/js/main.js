var config = {
    type: Phaser.AUTO,
    width: 1200,
    height: 600,
    backgroundColor: '#222',
    scene: {
        preload: function () {
            this.load.image('personaje', 'img/personaje.png');
        },
        create: function () {
            /* DEBUG */
            this.debugText = this.add.text(10, 10, 'Debug info:', { fontSize: '8px', fill: '#fff' });
            /* DEBUG */

            this.add.text(300, 50, 'LLTactics - Prototipo', { fontSize: '24px', fill: '#fff' });

            this.playerPositions = [100, 200, 300, 400, 500];
            this.enemyPositions = [700, 800, 900, 1000, 1100];

            this.playerSlots = {};
            this.shopSlots = {}; 
            this.draggingCharacter = null; 
            this.previousSlot = null; 

            // Crear posiciones del jugador
            this.playerPositions.forEach((x, index) => {
                let slot = this.add.rectangle(x, 300, 50, 50, 0x00ff00).setOrigin(0.5);
                slot.setData('index', index);
                this.playerSlots[index] = null;
                slot.setInteractive();
            });

            // Crear posiciones enemigas
            this.enemyPositions.forEach((x, index) => {
                let slot = this.add.rectangle(x, 300, 50, 50, 0xff0000).setOrigin(0.5); // Color rojo para enemigos
                slot.setData('index', index);
            });

            // Crear posiciones de la tienda
            this.playerPositions.forEach((x, index) => {
                let slotTienda = this.add.rectangle(x + 30, this.scale.height - 70, 50, 50, 0x000000).setOrigin(0.5);
                slotTienda.setData('index', index);
                slotTienda.setInteractive();

                let personaje = this.add.image(x + 30, this.scale.height - 70, 'personaje').setInteractive();
                personaje.setData('origin', 'shop');
                personaje.setData('index', index);
                this.input.setDraggable(personaje);

                this.shopSlots[index] = personaje;

                slotTienda.on('pointerover', () => slotTienda.setFillStyle(0x555555));
                slotTienda.on('pointerout', () => slotTienda.setFillStyle(0x000000));
            });

            // Evento de inicio de arrastre
            this.input.on('dragstart', (pointer, gameObject) => {
                let slotIndex = gameObject.getData('index');
                let origin = gameObject.getData('origin');

                if (origin === 'shop' || this.playerSlots[slotIndex] !== null) {
                    this.draggingCharacter = gameObject;
                    this.previousSlot = slotIndex;
                } else {
                    this.draggingCharacter = null;
                }
            });

            // Evento mientras arrastras
            this.input.on('drag', (pointer, gameObject, dragX, dragY) => {
                if (this.draggingCharacter) {
                    this.draggingCharacter.x = dragX;
                    this.draggingCharacter.y = dragY;
                }
            });

            // Evento al soltar el personaje
            this.input.on('dragend', (pointer, gameObject) => {
                if (!this.draggingCharacter) return;

                let droppedOnSlot = this.playerPositions.findIndex(posX => Math.abs(pointer.x - posX) < 25);

                if (droppedOnSlot !== -1) {
                    let personajeEnDestino = this.playerSlots[droppedOnSlot];

                    // Si hay un personaje en la posición destino, lo intercambiamos
                    if (personajeEnDestino) {
                        personajeEnDestino.x = this.playerPositions[this.previousSlot];
                        this.playerSlots[this.previousSlot] = personajeEnDestino;
                    } else {
                        // Si la posición estaba vacía, solo movemos
                        this.playerSlots[this.previousSlot] = null;
                    }

                    // Asignamos la nueva posición al personaje
                    this.draggingCharacter.x = this.playerPositions[droppedOnSlot];
                    this.draggingCharacter.y = 300;
                    this.playerSlots[droppedOnSlot] = this.draggingCharacter;
                } else {
                    // Si no se soltó en una posición válida, vuelve a su sitio
                    if (this.draggingCharacter.getData('origin') === 'shop') {
                        this.draggingCharacter.x = this.playerPositions[this.previousSlot] + 30;
                        this.draggingCharacter.y = this.scale.height - 70;
                    } else {
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
