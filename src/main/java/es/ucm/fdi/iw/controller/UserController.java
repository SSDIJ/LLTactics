package es.ucm.fdi.iw.controller;

import es.ucm.fdi.iw.LocalData;
import es.ucm.fdi.iw.LoginSuccessHandler;
import es.ucm.fdi.iw.model.FaccionUsos;
import es.ucm.fdi.iw.model.GameUnit;
import es.ucm.fdi.iw.model.Heroe;
import es.ucm.fdi.iw.model.HeroeUsos;
import es.ucm.fdi.iw.model.Message;
import es.ucm.fdi.iw.model.Transferable;
import es.ucm.fdi.iw.model.User;
import es.ucm.fdi.iw.model.User.Role;
import es.ucm.fdi.iw.repositories.UserRepository;
import es.ucm.fdi.iw.repositories.HeroeUsosRepository;
import es.ucm.fdi.iw.repositories.FaccionRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;

/**
 * User management.
 *
 * Access to this end-point is authenticated.
 */
@Controller

@RequestMapping("user")
public class UserController {

	private static final Logger log = LogManager.getLogger(UserController.class);

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private LocalData localData;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HeroeController heroeController;

	@Autowired
	private HeroeUsosRepository heroeUsosRepository;

	@Autowired
	private FaccionRepository faccionUsosRepository;

	// Añadido para el inicio de sesion automatico tras registrarse
	@Autowired
	private AuthenticationManager authenticationManager;

	@ModelAttribute
	public void populateModel(HttpSession session, Model model) {
		for (String name : new String[] { "u", "url", "ws" }) {
			model.addAttribute(name, session.getAttribute(name));
		}
	}

	/**
	 * Exception to use when denying access to unauthorized users.
	 * 
	 * In general, admins are always authorized, but users cannot modify
	 * each other's profiles.
	 */
	@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "No eres administrador, y éste no es tu perfil") // 403
	public static class NoEsTuPerfilException extends RuntimeException {
	}

	/**
	 * Encodes a password, so that it can be saved for future checking. Notice
	 * that encoding the same password multiple times will yield different
	 * encodings, since encodings contain a randomly-generated salt.
	 * 
	 * @param rawPassword to encode
	 * @return the encoded password (typically a 60-character string)
	 *         for example, a possible encoding of "test" is
	 *         {bcrypt}$2y$12$XCKz0zjXAP6hsFyVc8MucOzx6ER6IsC1qo5zQbclxhddR1t6SfrHm
	 */
	public String encodePassword(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

	/**
	 * Generates random tokens. From https://stackoverflow.com/a/44227131/15472
	 * 
	 * @param byteLength
	 * @return
	 */
	public static String generateRandomBase64Token(int byteLength) {
		SecureRandom secureRandom = new SecureRandom();
		byte[] token = new byte[byteLength];
		secureRandom.nextBytes(token);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(token); // base64 encoding
	}

	/**
	 * Landing page for a user profile
	 */
	@GetMapping("{id}")
public String index(@PathVariable long id, Model model, HttpSession session) {
    User target = entityManager.find(User.class, id);
    model.addAttribute("user", target);

    // === Cargar imágenes disponibles ===
    String rutaImgs = "/img/profile_pics";
    File folder = new File("src/main/resources/static/" + rutaImgs);
    File[] listOfFiles = folder.listFiles((dir, name) -> name.endsWith(".png") || name.endsWith(".jpg"));

    List<String> availablePics = new ArrayList<>();
    if (listOfFiles != null && listOfFiles.length > 0) {
        for (File file : listOfFiles) {
            availablePics.add(rutaImgs + "/" + file.getName());
        }
    } else {
        availablePics.add("pepe"); // Imagen por defecto
    }
    model.addAttribute("availablePics", availablePics);

    // === Obtener facciones y héroes usados del usuario ===
    List<FaccionUsos> facciones = faccionUsosRepository.findByUser(target);
    List<HeroeUsos> heroes = heroeUsosRepository.findByUser(target);

    // === Facción favorita ===
    FaccionUsos faccionFavorita = null;
    int maxUsosFaccion = -1;

    for (FaccionUsos faccion : facciones) {
        if (faccion.getUsos() > maxUsosFaccion) {
            maxUsosFaccion = faccion.getUsos();
            faccionFavorita = faccion;
        }
    }

    if (faccionFavorita != null) {
        target.setFaccionFavorita(faccionFavorita.getFaccion());
        model.addAttribute("faccionFavorita", faccionFavorita.getFaccion());
    }

    // === Héroe favorito ===
    HeroeUsos heroeFavorito = null;
    Heroe heroeMostrar = null;
    int maxUsosHeroe = -1;

    for (HeroeUsos heroeUso : heroes) {
        if (heroeUso.getUsos() > maxUsosHeroe) {
            maxUsosHeroe = heroeUso.getUsos();
            heroeFavorito = heroeUso;
        }
    }

    if (heroeFavorito != null) {
        heroeMostrar = heroeFavorito.getHeroe();
        List<Heroe> atributoHeroesMostrar = new ArrayList<>();
        atributoHeroesMostrar.add(heroeMostrar);
        target.setMasJugados(atributoHeroesMostrar);
        model.addAttribute("heroeFavorito", heroeMostrar);
    }

    return "user";
}


	/**
	 * Alter or create a user
	 */
	@PostMapping("/{id}")
	@Transactional
	public String postUser(
			HttpServletResponse response,
			@PathVariable long id,
			@ModelAttribute User edited,
			@RequestParam(required = false) String pass2,
			Model model, HttpSession session) throws IOException {

		User requester = (User) session.getAttribute("u");
		User target = null;
		if (id == -1 && requester.hasRole(Role.ADMIN)) {
			// create new user with random password
			target = new User();
			target.setPassword(encodePassword(generateRandomBase64Token(12)));
			target.setEnabled(true);
			entityManager.persist(target);
			entityManager.flush(); // forces DB to add user & assign valid id
			id = target.getId(); // retrieve assigned id from DB
		}

		// retrieve requested user
		target = entityManager.find(User.class, id);
		model.addAttribute("user", target);

		if (requester.getId() != target.getId() &&
				!requester.hasRole(Role.ADMIN)) {
			throw new NoEsTuPerfilException();
		}

		if (edited.getPassword() != null) {
			if (!edited.getPassword().equals(pass2)) {
				log.warn("Passwords do not match - returning to user form");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				model.addAttribute("user", target);
				return "user";
			} else {
				// save encoded version of password
				target.setPassword(encodePassword(edited.getPassword()));
			}
		}
		target.setUsername(edited.getUsername());
		target.setFirstName(edited.getFirstName());
		target.setLastName(edited.getLastName());
		target.setIdfotoPerfil(edited.getIdfotoPerfil());
		target.setFaccionFavorita(edited.getFaccionFavorita());
		target.setPartidasGanadas(edited.getPartidasGanadas());
		target.setPartidasPerdidas(edited.getPartidasPerdidas());
		target.setPuntuacion(edited.getPuntuacion());
		target.setIndiceRanking(edited.getIndiceRanking());
		target.setRoles(edited.getRoles());
		target.setEnabled(edited.isEnabled());

		// update user session so that changes are persisted in the session, too
		if (requester.getId() == target.getId()) {
			session.setAttribute("u", target);
		}

		return "user";
	}

	/**
	 * Returns the default profile pic
	 * 
	 * @return
	 */
	private static InputStream defaultPic() {
		return new BufferedInputStream(Objects.requireNonNull(
				UserController.class.getClassLoader().getResourceAsStream(
						"static/img/default-pic.jpg")));
	}

	/**
	 * Downloads a profile pic for a user id
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@GetMapping("{id}/pic")
	public StreamingResponseBody getPic(@PathVariable long id) throws IOException {
		File f = localData.getFile("user", "" + id + ".png"); // Obtiene la imagen del usuario de iwdata
		InputStream in = new BufferedInputStream(f.exists() ? new FileInputStream(f) : UserController.defaultPic());
		return os -> FileCopyUtils.copy(in, os);
	}

	@GetMapping("{id}/heroe")
	public StreamingResponseBody getHeroePic(@PathVariable long id) throws IOException {
		File f = localData.getFile("heroes", "" + id + ".png"); // Obtiene la imagen del heroe de iwdata
		InputStream in = new BufferedInputStream(f.exists() ? new FileInputStream(f) : UserController.defaultPic());
		return os -> FileCopyUtils.copy(in, os);
	}

	@GetMapping("{id}/objeto")
	public StreamingResponseBody getObjetoPic(@PathVariable long id) throws IOException {
		File f = localData.getFile("objetos", "" + id + ".png"); // Obtiene la imagen del heroe de iwdata
		InputStream in = new BufferedInputStream(f.exists() ? new FileInputStream(f) : UserController.defaultPic());
		return os -> FileCopyUtils.copy(in, os);
	}

	/**
	 * Uploads a profile pic for a user id
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@Transactional
	@PostMapping("{id}/pic")
	public String setPic(@RequestParam("photo") MultipartFile photo, @PathVariable long id,
			HttpServletResponse response, HttpSession session, Model model, RedirectAttributes redirectAttributes)
			throws IOException {

		User target = entityManager.find(User.class, id);
		model.addAttribute("user", target);

		// check permissions
		User requester = (User) session.getAttribute("u");
		if (requester.getId() != target.getId() &&
				!requester.hasRole(Role.ADMIN)) {
			throw new NoEsTuPerfilException();
		}

		// Define the directory where the photos are stored
		File userDir = localData.getFile("user", "");
		if (!userDir.exists()) {
			userDir.mkdirs(); // Create the directory if it doesn't exist
		}

		// Find the next available file name (e.g., 1.png, 2.png, ...)
		Long nextAvailableId = (long) 1;
		while (new File(userDir, nextAvailableId + ".png").exists()) {
			nextAvailableId++;
		}

		// Define the file to save the photo
		File f = new File(userDir, nextAvailableId + ".png");

		if (photo.isEmpty()) {
			log.info("Failed to upload photo: empty file?");
			redirectAttributes.addFlashAttribute("error", "El archivo está vacío.");
		} else {
			try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(f))) {
				byte[] bytes = photo.getBytes();
				stream.write(bytes);
				log.info("Uploaded photo for {} into {}!", id, f.getAbsolutePath());
				redirectAttributes.addFlashAttribute("success", "Foto subida correctamente.");
			} catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				log.warn("Error uploading " + id + " ", e);
				redirectAttributes.addFlashAttribute("error", "Error al subir la foto.");
			}
		}

		target.setIdfotoPerfil(nextAvailableId); // Update the user's profile picture ID
		entityManager.persist(target); // Persist the changes to the database
		entityManager.flush(); // Ensure the changes are saved
		session.setAttribute("u", target);

		// Redirect to the user's profile page
		return "redirect:/user/" + id;
	}

	@GetMapping("error")
	public String error(Model model, HttpSession session, HttpServletRequest request) {
		model.addAttribute("sess", session);
		model.addAttribute("req", request);
		return "error";
	}

	/**
	 * Returns JSON with all received messages
	 */
	@GetMapping(path = "received", produces = "application/json")
	@Transactional // para no recibir resultados inconsistentes
	@ResponseBody // para indicar que no devuelve vista, sino un objeto (jsonizado)
	public List<Message.Transfer> retrieveMessages(HttpSession session) {
		long userId = ((User) session.getAttribute("u")).getId();
		User u = entityManager.find(User.class, userId);
		log.info("Generating message list for user {} ({} messages)",
				u.getUsername(), u.getReceived().size());
		return u.getReceived().stream().map(Transferable::toTransfer).collect(Collectors.toList());
	}

	/**
	 * Returns JSON with count of unread messages
	 */
	@GetMapping(path = "unread", produces = "application/json")
	@ResponseBody
	public String checkUnread(HttpSession session) {
		long userId = ((User) session.getAttribute("u")).getId();
		long unread = entityManager.createNamedQuery("Message.countUnread", Long.class)
				.setParameter("userId", userId)
				.getSingleResult();
		session.setAttribute("unread", unread);
		return "{\"unread\": " + unread + "}";
	}

	/**
	 * Posts a message to a user.
	 * 
	 * @param id of target user (source user is from ID)
	 * @param o  JSON-ized message, similar to {"message": "text goes here"}
	 * @throws JsonProcessingException
	 */
	@PostMapping("/{id}/msg")
	@ResponseBody
	@Transactional
	public String postMsg(@PathVariable long id,
			@RequestBody JsonNode o, Model model, HttpSession session)
			throws JsonProcessingException {

		String text = o.get("message").asText();
		User u = entityManager.find(User.class, id);
		User sender = entityManager.find(
				User.class, ((User) session.getAttribute("u")).getId());
		model.addAttribute("user", u);

		// construye mensaje, lo guarda en BD
		Message m = new Message();
		m.setRecipient(u);
		m.setSender(sender);
		m.setDateSent(LocalDateTime.now());
		m.setText(text);
		entityManager.persist(m);
		entityManager.flush(); // to get Id before commit

		ObjectMapper mapper = new ObjectMapper();
		/*
		 * // construye json: método manual
		 * ObjectNode rootNode = mapper.createObjectNode();
		 * rootNode.put("from", sender.getUsername());
		 * rootNode.put("to", u.getUsername());
		 * rootNode.put("text", text);
		 * rootNode.put("id", m.getId());
		 * String json = mapper.writeValueAsString(rootNode);
		 */
		// persiste objeto a json usando Jackson
		String json = mapper.writeValueAsString(m.toTransfer());

		log.info("Sending a message to {} with contents '{}'", id, json);

		messagingTemplate.convertAndSend("/user/" + u.getUsername() + "/queue/updates", json);
		return "{\"result\": \"message sent.\"}";
	}

	@Autowired
	private LoginSuccessHandler handler;

	/**
	 * Registra un nuevo usuario e inicia la sesión automáticamente.
	 */
	@PostMapping("/register")
	@Transactional
	public String registerUser(
			HttpServletResponse response,
			@ModelAttribute User newUser,
			@RequestParam(required = false) String pass2,
			@RequestParam(required = false) Long idprofilePic, // Captura la foto de perfil seleccionada
			Model model, HttpSession session, HttpServletRequest request) throws IOException {

		// Verifica si las contraseñas coinciden
		if (newUser.getPassword() != null && !newUser.getPassword().equals(pass2)) {
			log.warn("Passwords do not match - returning to registration form");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			model.addAttribute("error", "Passwords do not match");
			return "registro"; // Redirige al formulario de registro
		}

		// Verifica si el nombre de usuario ya existe
		User existingUser = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
				.setParameter("username", newUser.getUsername())
				.getResultStream()
				.findFirst()
				.orElse(null);

		if (existingUser != null) {
			log.warn("Username {} is already taken", newUser.getUsername());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			model.addAttribute("error", "Username is already taken");
			return "registro"; // Redirige al formulario de registro
		}

		// Codifica la contraseña antes de guardarla
		newUser.setPassword(encodePassword(newUser.getPassword()));
		newUser.setEnabled(true); // Activa al usuario por defecto
		newUser.setRoles("USER");

		newUser.setFaccionFavorita(0);
		newUser.setPartidasGanadas(0);
		newUser.setPartidasPerdidas(0);
		newUser.setPuntuacion(0);
		newUser.setIndiceRanking(0);

		newUser.setIdfotoPerfil(idprofilePic);
		List<Heroe> heroes = heroeController.getTodosLosHeroes();

		for (Heroe heroe : heroes) {
			HeroeUsos hu = new HeroeUsos();
			hu.setJugador(newUser);
			hu.setHeroe(heroe);
			hu.setUsos(0);
			entityManager.persist(hu);
		}

		for (int i = 0; i < 4; i++) {
			FaccionUsos fu = new FaccionUsos();
			fu.setJugador(newUser);
			fu.setFaccion(i);
			fu.setUsos(0);
			entityManager.persist(fu);
		}

		// Guarda el nuevo usuario en la base de datos
		entityManager.persist(newUser);
		entityManager.flush(); // Asegura que el usuario se ha guardado y tiene un id válido

		log.info("User {} registered successfully", newUser.getUsername());

		// AUTENTICAR AUTOMÁTICAMENTE AL USUARIO (PAra que inicie sesión automáticamente
		// tras registrarse)
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(newUser.getUsername(),
				pass2);
		Authentication authentication = authenticationManager.authenticate(authToken);

		// Asegurar que la autenticación se propague en TODA la aplicación
		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		securityContext.setAuthentication(authentication);
		SecurityContextHolder.setContext(securityContext);
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

		// Llama al método onAuthenticationSuccess
		try {
			handler.onAuthenticationSuccess(request, response, authentication, false);
		} catch (Exception e) {
			log.error("Error handling authentication success", e);
		}
		return "redirect:/"; // Redirige a la página principal
	}

	// Funcion que sirve para cargar las fotos de la carpeta al abrir el user.html
	@GetMapping("/viewProfile")
public String searchUser(@RequestParam("username") String username, Model model) {
    log.info("Entrando en el método viewProfile");

    Optional<User> usuarioBuscado = userRepository.findByUsernameContainingIgnoreCase(username);

    if (usuarioBuscado.isEmpty()) {
        log.info("El usuario no existe");
        model.addAttribute("error", "Usuario no encontrado.");
        model.addAttribute("usuarioBuscado", null);
        return "viewProfile";
    } else {
        log.info("El usuario existe");
        User user = usuarioBuscado.get();
        

        List<FaccionUsos> facciones = faccionUsosRepository.findByUser(user);
        List<HeroeUsos> heroes = heroeUsosRepository.findByUser(user);


        FaccionUsos faccionFavorita = null;
        int maxUsosFaccion = -1;

        for (FaccionUsos faccion : facciones) {
            if (faccion.getUsos() > maxUsosFaccion) {
                maxUsosFaccion = faccion.getUsos();
                faccionFavorita = faccion;
            }
        }


        if (faccionFavorita != null) {
            user.setFaccionFavorita(faccionFavorita.getFaccion());
            log.info("La facción favorita del usuario es: {}", faccionFavorita.getFaccion());
        } else {
            log.warn("No se encontraron facciones para este usuario.");
        }


        HeroeUsos heroeFavorito = null;
		Heroe heroeMostrar=null;
        int maxUsosHeroe = -1;

        for (HeroeUsos heroeUso : heroes) {
            if (heroeUso.getUsos() > maxUsosHeroe) {
                maxUsosHeroe = heroeUso.getUsos();
                heroeFavorito = heroeUso;
            }
        }

        if (heroeFavorito != null) {
			heroeMostrar=heroeFavorito.getHeroe();
			List<Heroe> atributoHeroesMostrar= new ArrayList<>();
			atributoHeroesMostrar.add(heroeMostrar);
			user.setMasJugados(atributoHeroesMostrar);
            log.info("El héroe favorito del usuario es: {}", heroeFavorito.getHeroe().getNombre());
        } else {
            log.warn("No se encontraron héroes para este usuario.");
        }

        log.info("El nombre del usuario es: {}", user.getUsername());
        model.addAttribute("usuarioBuscado", user);
    }

    log.info("Salimos de la funcion viewProfile");
    return "viewProfile";
}


	@PostMapping("/updateFoto/{id}")
	public String updateProfilePicture(@PathVariable Long id, @RequestParam(required = true) Long IdselectedPic,
			Model model, RedirectAttributes redirectAttributes) {
		// Buscar al usuario por su id
		System.out.println("Buenaaas");
		System.out.println("Cambiando foto por: " + IdselectedPic);
		User user = userRepository.findById(id).orElse(null);

		if (user == null) {
			// Si no se encuentra el usuario, devolver un mensaje de error
			model.addAttribute("error", "Usuario no encontrado.");
			return "error"; // Puedes redirigir a una página de error o mostrar un mensaje adecuado
		}

		// Mostrar por consola el ID y la nueva foto
		System.out.println("ID del usuario: " + id);
		System.out.println("Nueva imagen: " + IdselectedPic);

		// Verificar que la nueva foto de perfil no sea nula o vacía
		if (IdselectedPic != null && IdselectedPic < 0) {
			// Actualizar la foto de perfil
			user.setIdfotoPerfil(IdselectedPic);
			// Guardar el usuario con la nueva foto de perfil en la base de datos
			userRepository.save(user);
			model.addAttribute("success", "Foto de perfil actualizada correctamente.");
		} else {
			model.addAttribute("error", "La foto de perfil no puede estar vacía.");
		}

		// Retornar a la vista del usuario con el modelo actualizado

		redirectAttributes.addAttribute("id", id);
		return "redirect:/user/{id}";

	}

	/*
	 * Esta funcion unicamente hace una actualizacion de la puntuacion.
	 */
	@ResponseBody
	public String updateWinner(
			@RequestParam String winnerUsername,
			@RequestParam String defeatedUsername) {

		Optional<User> optionalWinnerUser = userRepository.findByUsername(winnerUsername);
		Optional<User> optionalDefeatedUser = userRepository.findByUsername(defeatedUsername);

		User winnerUser = optionalWinnerUser.orElse(null);
		User defeatedUser = optionalDefeatedUser.orElse(null);

		if (winnerUser == null || defeatedUser == null) {
			System.out.println("Hubo problemas para encontrar a los jugadores");
			return "error";
		}

		int puntuacionWinner = winnerUser.getPuntuacion();
		int puntuacionDefeated = defeatedUser.getPuntuacion();

		puntuacionWinner += 20;
		// Esta funcion basicamente pone la puntuacion a 0 si es menor que 20
		puntuacionDefeated = Math.max(puntuacionDefeated - 20, 0);

		winnerUser.setPuntuacion(puntuacionWinner);
		defeatedUser.setPuntuacion(puntuacionDefeated);

		userRepository.save(winnerUser);
		userRepository.save(defeatedUser);

		return "OK";
	}

	public void updateUserByUsername(String username, GameUnit heroe) {
		// Buscar al usuario por su nombre de usuario
		User updatableUser = userRepository.findByUsername(username).orElse(null);

		// Verificar que el usuario exista
		if (updatableUser == null) {
			// Manejar el caso cuando el usuario no se encuentra
			System.out.println("Usuario no encontrado.");
			return; // O lanzar una excepción
		}

		// Buscar al héroe por su id
		Heroe updatableHeroe = heroeController.findByNombre(heroe.getName());

		// Verificar que el héroe exista
		if (updatableHeroe == null) {
			// Manejar el caso cuando el héroe no se encuentra
			System.out.println("Héroe no encontrado.");
			return; // O lanzar una excepción
		}

		// Buscar la instancia de HeroeUsos correspondiente al User y Heroe
		HeroeUsos heroeUsos = heroeUsosRepository.findByUserAndHeroe(updatableUser, updatableHeroe);

		if (heroeUsos != null) {
			// Si ya existe la relación, incrementar las veces jugadas
			heroeUsos.setUsos(heroeUsos.getUsos() + 1);
			// Guardar los cambios
			heroeUsosRepository.save(heroeUsos);
			System.out.println("Veces jugadas de " + updatableHeroe.getNombre() + " incrementadas en 1.");

		} else {
			HeroeUsos nuevoHeroeUso = new HeroeUsos();
			nuevoHeroeUso.setJugador(updatableUser);
			nuevoHeroeUso.setHeroe(updatableHeroe);
			nuevoHeroeUso.setUsos(1); // Primer uso
			heroeUsosRepository.save(nuevoHeroeUso);
			System.out.println("Nuevo registro de uso de héroe creado.");
		}
		FaccionUsos usosFaccion = faccionUsosRepository.findByUserAndFaccion(updatableUser, updatableHeroe.getFaccion());
		if (usosFaccion != null) {
			usosFaccion.setUsos(usosFaccion.getUsos() + 1);
			faccionUsosRepository.save(usosFaccion);
		} else {
			FaccionUsos nuevoUso = new FaccionUsos();
			nuevoUso.setJugador(updatableUser);
			nuevoUso.setFaccion(updatableHeroe.getFaccion());
			nuevoUso.setUsos(1);
			faccionUsosRepository.save(nuevoUso);
		}

	}

}
