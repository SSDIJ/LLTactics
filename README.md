# LLTactics, proyecto de IW
LLTactics es un juego web de fantasía medieval donde jugadores deberán luchar por la victoria comprando personajes y objetos.
## Contenido del proyecto
- **Vista principal** (index.html)
  * Lo primero que se ve al abrir la web y desde donde puedes acceder al resto de vistas
- **Vista Juego** (game.html)
  * Necesario iniciar sesión para acceder
  * Aunque aún no esté implementado el matchmaking, ya hay una versión funcional del juego con sus rondas: 
    - 1. Compra de personajes, equipación de objetos 
    - 2. Batalla
- **Vista ranking** (ranking.html)
  * Muestra a los mejores jugadores del server, junto a sus estadísticas
  * Implementada mediante thymeleaf y fragmentos dinámicos
  * Se ha creado RankingController.Java, que se apoya en la clase Jugador para mandar a ranking.html las listas de jugadores
  * La vista es dinámica y permite hacer click en los jugadores para ver su perfil
- **Vista galería** (galeria.html)
  * Muestra todos los personajes y objetos disponibles en el juego, así como sus estadísticas y descripciones
  * Implementada mediante thymeleaf y fragmentos dinámicos
  * Se ha creado GaleríaController.java, que se apoya en la clase Héroe para mandar a galeria.html las listas de personajes
  * Galeria.html usa la clase card.html para mostrar a los personajes, con sus estadísticas y su descripción 

- **Vista reglas** (reglas.html)
  * Muestra las reglas del juego 
- **Vista autores** (autores.html)
  * Muestra los nombres de los miembros del grupo y creadores del juego
- **Vista inicio de sesión** (login.html)
  * Permite iniciar sesión a los jugadores

  **Vista registro** (registro.html)
  * Permite a los jugadores registrarse y escoger una foto de perfil

  **Vistas de admin**
  * Las vistas exclusivas de los administradores, les permitirá llevar un control del sistema y moderarlo
  * Muestra 3 secciones: 
    - **Gestión de héroes** (gestHeroes.html)
        * Permite añadir un héroe nuevo o borrar alguno ya existente
    - **Gestión de usuarios** (gestUsuarios.html)
        * Muestra la información de todos los usuarios del servidor y permite visitar su perfil
        * Incluye un filtro para la búsqueda
    - **Gestión de partidas** (gestPartidas.html)
        * Muestra la información de todas las partidas ya jugadas del servidor y permite crear una de prueba

  **Vista de perfil ajeno** (viewProfile.html)
  * Permite visitar el perfil de otros jugadores y ver sus estadisticas

  **Vista de tu perfil** (user.html)
  * Permite visitar tu perfil y ver tus estadisticas

  **NOTAS:**
  - Todos los sprites de los personajes pertenecen a [Wesnoth](https://github.com/wesnoth/wesnoth)
  - Se han usado ciertas imagenes de internet para las fotos de perfil y la decoración del juego

## Implementacion BD Actual
Las tablas actualmente funcionales de la base de datos son:
* **IWUser**, que almacena la información de los usuarios
* **Heroe**, que almacena la informacion de los heroes
* **Objeto**, que almacena la informacion de los objetos
* **Partida**, que almacena la informacion de las partidas

Que se apoyan en sus correspondientes controladores, repositorios y servicios para los accesos e inserciones

En un futuro, cuando el juego sea completamente funcional mediante websockets, se les dará uso a las tablas Topic y Unidad

## Contenido futuro
- Sistema de mensajería offgame al visitar un perfil ajeno
- Sistema de matchmaking con otros usuarios, con su correspondiente sincronización, mensajería y guardado en la BD al finalizar

## Miembros del grupo:
- Samuel Carrillo
- David Cendejas
- Sandra Sanz
- Iván Toribio
- Javier Martín
