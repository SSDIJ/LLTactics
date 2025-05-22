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
