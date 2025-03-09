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
  **Vista de admin**
  * Muestra 4 secciones: Partidas en curso, partidas terminadas, usuarios reportados y búsqueda de usuarios.
  * A través de esta vista se podrán visualizar partidas y administrar usuarios.

  **NOTA:** Todos los sprites de los personajes pertenecen a [Wesnoth](https://github.com/wesnoth/wesnoth)

## Implementacion BD Actual
Actualmente la base de datos es funcional para la galeria y el ranking, ademas se añadieron los siguientes archivos para la gestion de 
la base de datos:
-HeroeRepositoy: Gestiona el acceso a la base de datos de los Heroes jugables dentro de partida, accesible en HeroeController.java
-playerRepositoy: Gestiona el acceso a la base de datos de los usuarios registrados, actualmente esta implementado en el rankingController para la visualización ordenada por puntos de los distintos jugadores.

## Contenido futuro
- Implementación de las acciones de los admins
- Plantear el balanceo del juego (estadísticas, economía, objetos)
- Añadir dichos objetos a la galería
- Sistema de busqueda de partida con otros usuarios
- Juego jugable

## Miembros del grupo:
- Samuel Carrillo
- David Cendejas
- Sandra Sanz
- Iván Toribio
- Javier Martín
