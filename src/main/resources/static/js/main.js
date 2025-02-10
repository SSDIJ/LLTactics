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
            this.add.text(300, 50, 'LLTactics - Prototipo', { fontSize: '24px', fill: '#fff' });

            this.playerPositions = [100, 200, 300, 400, 500];
            this.enemyPositions = [700, 800, 900, 1000, 1100];
            
            this.playerSlots = {};
            this.enemySlots = {};
            this.shopSlots = {}; 
            this.draggingCharacter = null; 
            this.previousSlot = null; 

            this.playerPositions.forEach((x, index) => {
                let slot = this.add.rectangle(x, 300, 50, 50, 0x00ff00).setOrigin(0.5);
                slot.setData('index', index);
                this.playerSlots[index] = null;

                slot.setInteractive();
                this.input.setDraggable(slot);
            });

            this.enemyPositions.forEach((x, index) => {
                let enemySlot = this.add.rectangle(x, 300, 50, 50, 0xff0000).setOrigin(0.5);
                enemySlot.setData('index', index);
                this.enemySlots[index] = null;    
            });

            this.playerPositions.forEach((x, index) => {
                let slotTienda = this.add.rectangle(x + 30, this.scale.height - 70, 50, 50, 0x000000).setOrigin(0.5);
                slotTienda.setData('index', index);
                this.shopSlots[index] = null;
                slotTienda.setInteractive();
                this.input.setDraggable(slotTienda);

                slotTienda.on('pointerover', () => slotTienda.setFillStyle(0x555555));
                slotTienda.on('pointerout', () => slotTienda.setFillStyle(0x000000));
            });

            this.input.on('dragstart', (pointer, gameObject) => {
                let slotIndex = gameObject.getData('index');
                if (this.shopSlots[slotIndex] === null) {
                    this.draggingCharacter = this.add.image(pointer.x, pointer.y, 'personaje').setAlpha(0.5);
                } else {
                    this.draggingCharacter = this.shopSlots[slotIndex];
                    this.previousSlot = slotIndex;
                }
            });

            this.input.on('drag', (pointer, gameObject, dragX, dragY) => {
                if (this.draggingCharacter) {
                    this.draggingCharacter.x = dragX;
                    this.draggingCharacter.y = dragY;
                }
            });

            this.input.on('dragend', (pointer, gameObject) => {
                let droppedOnSlot = this.playerPositions.findIndex(posX => Math.abs(pointer.x - posX) < 25);
                if (droppedOnSlot !== -1 && this.playerSlots[droppedOnSlot] === null) {
                    this.draggingCharacter.x = this.playerPositions[droppedOnSlot];
                    this.draggingCharacter.y = 300;
                    this.draggingCharacter.setAlpha(1);
                    this.playerSlots[droppedOnSlot] = this.draggingCharacter;
                    if (this.previousSlot !== null) {
                        this.playerSlots[this.previousSlot] = null;
                    }
                } else {
                    if (this.previousSlot !== null) {
                        this.draggingCharacter.x = this.playerPositions[this.previousSlot];
                        this.draggingCharacter.y = 300;
                    } else {
                        this.draggingCharacter.destroy();
                    }
                }
                this.draggingCharacter = null;
                this.previousSlot = null;
            });
        },
        update: function () {
        }
    }
};

var game = new Phaser.Game(config);
