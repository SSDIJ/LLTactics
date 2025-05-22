// Paquete al que pertenece esta clase.
package es.ucm.fdi.iw.controller;

// Importaciones necesarias para el funcionamiento del controlador
import org.springframework.beans.factory.annotation.Autowired; // Para inyección automática de dependencias
import org.springframework.stereotype.Controller;              // Marca esta clase como un controlador web de Spring
import org.springframework.ui.Model;                          // Permite pasar atributos a la vista (HTML)
import org.springframework.web.bind.annotation.GetMapping;    // Para manejar solicitudes GET
import org.springframework.web.bind.annotation.ModelAttribute; // Para añadir atributos al modelo automáticamente

// Importaciones de los modelos y repositorio usados en este controlador
import es.ucm.fdi.iw.model.Heroe; // (No se utiliza en este archivo, podría eliminarse si no se necesita)
import es.ucm.fdi.iw.model.User; // Modelo que representa a un usuario
import es.ucm.fdi.iw.repositories.UserRepository; // Repositorio de usuarios para acceder a la base de datos

// Importación para trabajar con la sesión HTTP
import jakarta.servlet.http.HttpSession;

// Importación de estructuras de datos necesarias
import java.util.ArrayList;
import java.util.List;

// Esta clase actúa como un controlador en Spring MVC
@Controller
public class RankingController {

    // Inyección automática del repositorio de usuarios para hacer consultas a la base de datos
    @Autowired
    private UserRepository userRepository;

    /**
     * Este método se ejecuta automáticamente antes de cada método controlador.
     * Añade al modelo ciertos atributos extraídos de la sesión (como el usuario logueado, URL, etc.)
     */
    @ModelAttribute
    public void populateModel(HttpSession session, Model model) {
        // Por cada atributo clave esperado en la sesión, lo añadimos al modelo
        for (String name : new String[] { "u", "url", "ws" }) {
            model.addAttribute(name, session.getAttribute(name));
        }
    }

    /**
     * Controlador que maneja las peticiones GET a la ruta "/ranking".
     * Este método prepara y envía al modelo los datos necesarios para mostrar la página de ranking.
     */
    @GetMapping("/ranking")
    public String mostrarRanking(Model model, HttpSession session) {

        // Variables para guardar el usuario logueado y su puntuación
        User user = null;
        int puntuacion = -1;

        // Si hay una sesión activa, intentamos obtener el usuario desde ella
        if (session != null) {
            user = (User) session.getAttribute("u");
        }

        // Si el usuario está logueado
        if (user != null) {
            // Mensaje de depuración en consola
            System.out.println("Usuario autenticado: " + user.getUsername());

            // Se indica en el modelo que el usuario está logueado
            model.addAttribute("usuarioLogueado", true);

            // Se obtiene su puntuación
            puntuacion = user.getPuntuacion();
        } else {
            // Si no hay usuario logueado, se indica también en el modelo
            System.out.println("Usuario no autenticado.");
            model.addAttribute("usuarioLogueado", false);
        }

        // Recuperamos todos los usuarios de la base de datos
        List<User> usuarios = userRepository.findAll();

        // Creamos listas para almacenar el top 10 y el bloque de ranking del usuario logueado
        List<User> topRanking = new ArrayList<>();
        List<User> miRanking = new ArrayList<>();

        // Ordenamos los usuarios por puntuación de mayor a menor
        usuarios.sort((j1, j2) -> Integer.compare(j2.getPuntuacion(), j1.getPuntuacion()));

        // Creamos la lista con los 10 primeros usuarios (o menos si hay menos de 10)
        int top = Math.min(10, usuarios.size());
        for (int j = 0; j < top; j++) {
            topRanking.add(usuarios.get(j));
        }

        // Si hay un usuario logueado, buscamos su posición en la lista y construimos su bloque personalizado
        if (user != null) {
            int index = -1; // Índice del usuario logueado

            // Recorremos la lista ordenada para encontrar la posición del usuario actual
            for (int k = 0; k < usuarios.size(); k++) {
                if (usuarios.get(k).getId() == user.getId()) {
                    index = k;
                    break;
                }
            }

            // Si se encontró al usuario en la lista
            if (index != -1) {
                // Definimos el rango de usuarios a mostrar: 5 antes y 5 después del usuario
                int from = Math.max(0, index - 5); // No bajamos de 0
                int to = Math.min(usuarios.size(), index + 6); // +6 para incluir al propio usuario y 5 más

                // Extraemos ese subranking de la lista completa
                miRanking = usuarios.subList(from, to);
            }
        }

        // Añadimos las listas y atributos necesarios al modelo para la vista
        model.addAttribute("usuarios", usuarios);         // Lista completa (opcional)
        model.addAttribute("miRanking", miRanking);       // Ranking personalizado si hay sesión
        model.addAttribute("topRanking", topRanking);     // Top 10 global
        model.addAttribute("userLogueado", session.getAttribute("u")); // Usuario logueado actual

        // Devolvemos el nombre de la plantilla que se va a renderizar (ranking.html)
        return "ranking";
    }
}
