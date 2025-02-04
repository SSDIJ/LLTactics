var config = {
    type: Phaser.AUTO,
    width: 1200,
    height: 600,
    backgroundColor: '#222',
    scene: {
        preload: function () {
            // Aquí cargamos los assets (sprites de personajes, background y esas cosas)
            this.load.image('personaje', 'personaje.png') /* Ejemplo!!!!! */
        },
        create: function () {
            this.add.text(300, 50, 'AutoChess Mini - Prototipo', { fontSize: '24px', fill: '#fff' });

            // Posiciones de los personajes
            this.playerPositions = [100, 200, 300, 400, 500];
            this.enemyPositions = [700, 800, 900, 1000, 1100];
            this.playerSlots = {}; // Contenido de las posiciones del jugador

            /* Dibujar las posiciones */
            this.playerPositions.forEach((x, index) => {
                let slot = this.add.rectangle(x, 300, 50, 50, 0x00ff00).setOrigin(0.5); // TODO: Esto hay que sustituirlo por un sprite lindo🥰
                slot.setData('index', index);
                this.playerSlots[index] = null;
            });

            this.enemyPositions.forEach((x, index) => {
                this.add.rectangle(x, 300, 50, 50, 0xff0000).setOrigin(0.5);
            });

            /* Botón (tmp) para arrastrar el personaje */
            let button = this.add.text(50, 550, 'Arrastra Personaje', { fontSize: '18px', fill: '#fff', backgroundColor: '#444' })
                .setInteractive()
                .setOrigin(0.5);
            
            this.input.setDraggable(button); // Se puede arrastrar

            // Comienzo arrastrando
            this.input.on('dragstart', (pointer, gameObject) => {
                gameObject.setAlpha(0.5);
                this.draggingCharacter = this.add.image(pointer.x, pointer.y, 'personaje');
            });

            // Arrastrando
            this.input.on('drag', (pointer, gameObject, dragX, dragY) => {
                if (this.draggingCharacter) {
                    this.draggingCharacter.x = dragX;
                    this.draggingCharacter.y = dragY;
                }
            });

            // Final arrastrando
            this.input.on('dragend', (pointer, gameObject) => {
                gameObject.setAlpha(1);
                let droppedOnSlot = this.playerPositions.findIndex(posX => Math.abs(pointer.x - posX) < 25);
                if (droppedOnSlot !== -1 && !this.playerSlots[droppedOnSlot]) {
                    this.draggingCharacter.x = this.playerPositions[droppedOnSlot];
                    this.draggingCharacter.y = 300;
                    this.playerSlots[droppedOnSlot] = this.draggingCharacter;
                } else {
                    this.draggingCharacter.destroy();
                }
                this.draggingCharacter = null;
            });
        },
        
        update: function () {
            // Lógica de los personajes combate, tienda, interacciones...
            
            // TODO: Tienda aleatoria, botón para randomizar
        }
    }
};

var game = new Phaser.Game(config);