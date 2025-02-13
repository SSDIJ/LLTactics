# LLTactics, proyecto de IW
LLTactics es un juego web de fantasía medieval donde jugadores deberán luchar por la victoria comprando personajes y objetos.
## Contenido del proyecto
- **Vista principal**
  * Lo primero que se ve al abrir la web y desde donde puedes acceder al resto de vistas
- **Vista Juego** (de momento estática)
  * Necesario iniciar sesión para acceder
  * Todavía no jugable, pero ya hay una base bastánte sólida en JavaScript
- **Vista ranking**
  * Muestra a los mejores jugadores del server, junto a sus estadísticas
  * Implementada mediante thymeleaf y fragmentos dinámicos
  * Se ha creado RankingController.Java, que se apoya en la clase Jugador para mandar a ranking.html las listas de jugadores
- **Vista galería**
  * Muestra todos los personajes disponibles en el juego
  * Implementada mediante thymeleaf y fragmentos dinámicos
  * Se ha creado GaleríaController.java, que se apoya en la clase Héroe para mandar a galeria.html las listas de personajes
  * Galeria.html usa la clase card.html para mostrar a los personajes, con sus estadísticas y su descripción 
- **Vista perfil**
  * Muestra tu perfil, con tus estadísticas
  * Por el momento muestra un perfil estático con datos inventados, lo modificaremos en la futura entrega
- **Vista reglas**
  * Muestra las reglas del juego 
- **Vista autores**
  * Muestra los nombres de los miembros del grupo y creadores del juego
- **Vista inicio de sesión**
  * Permite iniciar sesión a los jugadores

  **NOTA:** Todos los sprites de los personajes pertenecen a [Wesnoth](https://github.com/wesnoth/wesnoth)

## Contenido futuro
- Vista y acciones de los admins
- Plantear el balanceo del juego (estadísticas, economía, objetos)
- Añadir dichos objetos a la galería
- Estructura y accesos a la BD
- Sistema de busqueda de partida con otros usuarios
- Juego jugable
