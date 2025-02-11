// Rutas de las imágenes de los personajes
const characters = [
    {
        name: "general", 
        animation: [
            "../img/units/humans/general/general-melee1.png", 
            "../img/units/humans/general/general-melee2.png", 
            "../img/units/humans/general/general-melee3.png",
            "../img/units/humans/general/general-melee4.png",
            "../img/units/humans/general/general-melee5.png",
            "../img/units/humans/general/general-melee6.png",
        ]
    },
    {
        name: "leader",
        animation: [
            "../img/units/orcs/leader/leader-attack-1.png",
            "../img/units/orcs/leader/leader-attack-2.png",
            "../img/units/orcs/leader/leader-attack-3.png",
            "../img/units/orcs/leader/leader-attack-4.png",
            "../img/units/orcs/leader/leader-attack-5.png",
        ]
    },
    {
        name: "spearman",
        animation: [
            "../img/units/goblins/spearman/spearman-idle-2.png",
            "../img/units/goblins/spearman/spearman-idle-1.png",
            "../img/units/goblins/spearman/spearman-idle-3.png",
            "../img/units/goblins/spearman/spearman-idle-4.png",
            "../img/units/goblins/spearman/spearman-idle-5.png",
            "../img/units/goblins/spearman/spearman-idle-6.png",
            "../img/units/goblins/spearman/spearman-idle-7.png",
            "../img/units/goblins/spearman/spearman-idle-8.png",
            "../img/units/goblins/spearman/spearman-idle-9.png",
            "../img/units/goblins/spearman/spearman-idle-10.png",
            "../img/units/goblins/spearman/spearman-idle-11.png",
            "../img/units/goblins/spearman/spearman-idle-12.png",

        ]
    }

];

// Índice actual de personaje y animación
let currentCharacterIndex = 0;
let currentAnimationIndex = 0;

const TIME_TO_CHANGE_CHARACTER = 5000;

// Función para cambiar de personaje
function changeCharacter() {
    currentCharacterIndex = (currentCharacterIndex + 1) % characters.length;
    const character = characters[currentCharacterIndex];
    startCharacterAnimation(character.animation);
}

// Función para iniciar la animación del personaje
function startCharacterAnimation(animationFrames) {
    let animationInterval = setInterval(() => {
        document.getElementById("homepage-character").src = animationFrames[currentAnimationIndex];
        currentAnimationIndex = (currentAnimationIndex + 1) % animationFrames.length;
    }, 200); // Cambia la imagen de animación

    // Detiene la animación
    setTimeout(() => {
        clearInterval(animationInterval);
    }, TIME_TO_CHANGE_CHARACTER );
}

// setInterval(changeCharacter, TIME_TO_CHANGE_CHARACTER);
// changeCharacter();

// - PARTÍCULAS -

// Función para crear partículas
function crearParticula() {

    const particula = document.createElement("div");
    particula.classList.add("particula");
  
    const x = Math.random() * 600 + window.innerWidth / 2 - 300;    // Posición horizontal aleatoria
    const size = Math.random() * 5 + 5;                             // Tamaño aleatorio entre 5px y 10px
    
    particula.style.left = `${x}px`;  
    particula.style.width = `${size}px`;
    particula.style.height = `${size}px`;
  
    document.getElementById("particulas").appendChild(particula);
  
    // Eliminamos partículas para evitar que se acumulen
    setTimeout(() => {
      particula.remove();
    }, 6000);
  }
  
  setInterval(crearParticula, 500 + Math.random() * 3);
