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
];

// Índice actual de personaje y animación
let currentCharacterIndex = 0;
let currentAnimationIndex = 0;

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
    }, 10000);
}

setInterval(changeCharacter, 10000);
changeCharacter();

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
