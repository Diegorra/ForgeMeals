package es.ucm.fdi.iw.controller;

import es.ucm.fdi.iw.LocalData;
import es.ucm.fdi.iw.model.*;
import es.ucm.fdi.iw.model.User.Role;
import es.ucm.fdi.iw.model.WeekPlanMeal.DayTime;
import es.ucm.fdi.iw.model.WeekPlanMeal.WeekDay;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;
import java.lang.ProcessHandle.Info;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *  User management.
 *
 *  Access to this end-point is authenticated.
 */
@Controller()
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

	@GetMapping("/{id}")
	public String profile(@PathVariable long id, Model model, HttpSession session){
		User target = entityManager.find(User.class, id);
        model.addAttribute("user", target);
		return "profile";
	}

	@GetMapping("/checkout")
	public String checkout(Model model, HttpSession session) {
		Order order = (Order)session.getAttribute("order");
		//User requester = (User)session.getAttribute("u");
		if(order == null){
			order = new Order();
			session.setAttribute("order", order);
			session.setAttribute("orderId", 1);
		}
		model.addAttribute("order", order);
		return "checkout";
	}

	@GetMapping("/payment")
	public String payment(Model model){return "/Forms/payment";}

	@GetMapping("/settings")
	public String settings(){return "settings";}

	
	
	@Transactional
	@GetMapping("/test")
	public String test(Model model, HttpSession session){
		User requester = (User)session.getAttribute("u");
		User u = entityManager.find(User.class, requester.getId());
		

		u.assignMeal(entityManager.find(Recipe.class, 1L), WeekDay.Lunes, DayTime.Desayuno, entityManager);
		u.assignMeal(entityManager.find(Recipe.class, 2L), WeekDay.Lunes, DayTime.Comida, entityManager);
		entityManager.flush();
		return weekplan(model, session);
		
	}
	
	@Transactional
	@GetMapping("/weekplan")
	public String weekplan(Model model, HttpSession session){
		
		// inicialmente pasar lista vacia al modelo
		User requester = (User)session.getAttribute("u");
		User u = entityManager.find(User.class, requester.getId());
		
		u.assignMeal(entityManager.find(Recipe.class, 1L), WeekDay.Lunes, DayTime.Desayuno, entityManager);
		u.assignMeal(entityManager.find(Recipe.class, 2L), WeekDay.Lunes, DayTime.Comida, entityManager);
		entityManager.flush();
		
		model.addAttribute("weekdays", WeekPlanMeal.WeekDay.values());
		model.addAttribute("daytimes", WeekPlanMeal.DayTime.values()); 
		model.addAttribute("user", u);
		return "weekplan";
	}

// TODO no funcionan. O por el go en weekplan.html o algo de la gestión aquí. Cómo se modifica el modelo si solo hay addAtribute?
	@Transactional
	@ResponseBody
	@PostMapping("/weekplan/removeMeal")
	public String removeMeal(Model model, @RequestBody JsonNode data, HttpSession session){
		//User requester = (User)session.getAttribute("u");
		User requester = (User)session.getAttribute("u");
		User u = entityManager.find(User.class, requester.getId()); 
		// Mi confusión era que nosé si AJAX cambia el enum a String, porque en el JS le estoy pasando el enum
		// u.removeMeal(WeekDay.valueOf(data.get("day").asText()), DayTime.valueOf(data.get("time")).asText);
		u.removeMeal(WeekDay.Lunes, DayTime.Desayuno, entityManager);
		
		//model.addAttribute("user", u);	
		return "{}";
	}

	@Transactional
	@ResponseBody
	@PostMapping("/weekplan/add")
	public String addMeal(Model model, @RequestBody JsonNode data, HttpSession session){
		User u = (User)session.getAttribute("u");
		// TODO añadir cosas. También hay q modificar el javascript para q reciba el input, y luego conseguir q sean recetas
		session.setAttribute("u", u);	
		return "{}";
	}

	@Transactional
	@ResponseBody
	@PostMapping("/weekplan/addToCart")
	public String addMealToCart(Model model, @RequestBody JsonNode data, HttpSession session){
		User u = (User)session.getAttribute("u");
		// TODO código para añadir al carrito, usando Order. Maybe usar el addToCart que está más arriba y eliminar este método 
		session.setAttribute("u", u);	
		return "{}";
	}
	
	
	@GetMapping("/addRecipe")
	public String newRecipe(Model model){
		List<Ingredient> ingredients = entityManager.createQuery("select i from Ingredient i", Ingredient.class).getResultList();
		model.addAttribute("ingredients",ingredients);
		return "/Forms/recipeForm";
	}
	
	@ResponseBody
	@Transactional
	@PostMapping("/addRecipe")
	public String newRecipe(Model model, @RequestBody JsonNode data, HttpSession session){
		String id = "pruebaReceta";
		Recipe recipeNew = new Recipe();
		User requester = (User)session.getAttribute("u");

		File f = localData.getFile("user", ""+id+".jpg");
		

		ArrayList<RecipeIngredient> ingredientes = new ArrayList<RecipeIngredient>();
		JsonNode it = data.get("ingredientNames");
		JsonNode it2 = data.get("ingredientCant");
		for(int i = 0; i < it.size(); i++){
		
			
			List<Ingredient> is = entityManager.createNamedQuery("Ingredient.byName", Ingredient.class)
				.setParameter("iname", it.get(i).asText())
				.getResultList();
			
			
			if(is.size() == 0) continue;
			RecipeIngredient ingredienteCompleto = new RecipeIngredient();
			ingredienteCompleto.setIngredient(is.get(0));
			ingredienteCompleto.setQuantity(it2.get(i).asInt());
			ingredientes.add(ingredienteCompleto);
		}
		recipeNew.setIngredients(ingredientes);
		recipeNew.setSrc(f.getAbsolutePath());
		recipeNew.setDescription(data.get("description").textValue());
		recipeNew.setAuthor(entityManager.find(User.class, requester.getId()));
		//recipeNew.setAuthor((User)session.getAttribute("u"));
		recipeNew.setName(data.get("name").textValue());		
		recipeNew.setPrice(BigDecimal.TEN);
		recipeNew.setDateRegistered(LocalDateTime.now());
		entityManager.persist(recipeNew);
		//entityManager.flush();

		return "{}";
		
	}


	@PostMapping("/addRecipeImage")
	@ResponseBody
    public String addRecipeImage(@RequestParam("photo") MultipartFile photo,
        HttpServletResponse response, HttpSession session, Model model) throws IOException {

        /*User target = entityManager.find(User.class, id);
        model.addAttribute("user", target);
		
		// check permissions
		User requester = (User)session.getAttribute("u");
		if (requester.getId() != target.getId() &&
				! requester.hasRole(Role.ADMIN)) {
            throw new NoEsTuPerfilException();
		}*/
		String id = "pruebaReceta";
		log.info("Updating photo for user {}", id);
		File f = localData.getFile("user", ""+id+".jpg");
		//File f = new File("user/pruebaReceta.jpg");
		if (photo.isEmpty()) {
			
		} else {
			try (BufferedOutputStream stream =
					new BufferedOutputStream(new FileOutputStream(f))) {
				byte[] bytes = photo.getBytes();
				stream.write(bytes);
                
			} catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				
			}
		}
		return "{}";
    }
    
	/**
	 * Remove the recipe con id "id" if the requester is the author or is the admin
	 *
	 * @param id
	 * @param model
	 * @param session
	 * @return index
	 */
	@Transactional
	@PostMapping("/removeRecipe/{id}")
	public String removeRecipe(@PathVariable long id, Model model, HttpSession session){
		//Comprobamos que el usuario que quiere borrar es admin o el propio autor
		Recipe recipe = entityManager.find(Recipe.class, id);
		User requester = (User)session.getAttribute("u");
		if ((requester.getId() == recipe.getAuthor().getId()) || requester.hasRole(Role.ADMIN)) {
			entityManager.remove(recipe);//borramos la receta
		}else{
			throw new NoEsTuPerfilException();
		}

		return "redirect:/";
	}

	private static int nextOrderId(HttpSession session) {
		int next = (Integer)session.getAttribute("orderId");
		session.setAttribute("orderId", next + 1);
		return next;
	}

	@ResponseBody
	@PostMapping("/addToCart")
	public String addToCart(Model model, @RequestBody JsonNode data, HttpSession session){


		Order order = (Order)session.getAttribute("order");
		User requester = (User)session.getAttribute("u");
		if(order == null){
			order = new Order();
			order.setUser(requester);
			session.setAttribute("orderId", 1);
		}
		Recipe receta = entityManager.find(Recipe.class, data.get("receta").asLong());
		for (RecipeIngredient ri : receta.getIngredients()) {
			ri.getIngredient().getAllergen();
		}
		
		//receta = new Recipe(receta);
		//receta = new Recipe("Pizza", "https://w6h5a5r4.rocketcdn.me/wp-content/uploads/2019/06/pizza-con-chorizo-jamon-y-queso-1080x671.jpg" ,new BigDecimal("3"));
		OrderRecipe orderRecipe = new OrderRecipe();
		orderRecipe.setId(UserController.nextOrderId(session));
		orderRecipe.setRecipe(receta);
		orderRecipe.setQuantity(1);
		order.addRecipe(orderRecipe);
		order.actPrecio();
		//order.getRecipes().add(orderRecipe);
        session.setAttribute("order", order);		
		return "{}";
	}


	/**
	 * Sing out the usser in the session
	 *
	 * @param model
	 * @param session
	 * @return index
	 */
	@GetMapping("/logout")
	public String logout(Model model, HttpSession session){
		session.invalidate();
		return "redirect:/";
	}

	@Transactional
	@ResponseBody
	@PostMapping("/removeFromCart")
	public String removeFromCard(Model model, @RequestBody JsonNode data, HttpSession session){

		Order order = (Order)session.getAttribute("order");
		order.removeRecipe(data.get("receta").asLong());
        session.setAttribute("order", order);		
		return "{}";
	}


	@Transactional
	@ResponseBody
	@PostMapping("/changequantCart")
	public String changequantCart(Model model, @RequestBody JsonNode data, HttpSession session){
		Order order = (Order)session.getAttribute("order");
		order.changeQuant(data.get("receta").asLong(), data.get("quantity").asInt());
        session.setAttribute("order", order);		

		return "{}";
	}


	/**
     * Exception to use when denying access to unauthorized users.
     * 
     * In general, admins are always authorized, but users cannot modify
     * each other's profiles.
     */
	@ResponseStatus(
		value=HttpStatus.FORBIDDEN, 
		reason="No eres administrador, y éste no es tu perfil")  // 403
	public static class NoEsTuPerfilException extends RuntimeException {}

	/**
	 * Encodes a password, so that it can be saved for future checking. Notice
	 * that encoding the same password multiple times will yield different
	 * encodings, since encodings contain a randomly-generated salt.
	 * @param rawPassword to encode
	 * @return the encoded password (typically a 60-character string)
	 * for example, a possible encoding of "test" is 
	 * {bcrypt}$2y$12$XCKz0zjXAP6hsFyVc8MucOzx6ER6IsC1qo5zQbclxhddR1t6SfrHm
	 */
	public String encodePassword(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

    /**
     * Generates random tokens. From https://stackoverflow.com/a/44227131/15472
     * @param byteLength
     * @return
     */
    public static String generateRandomBase64Token(int byteLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[byteLength];
        secureRandom.nextBytes(token);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(token); //base64 encoding
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
			@RequestParam(required=false) String pass2,
			Model model, HttpSession session) throws IOException {

        User requester = (User)session.getAttribute("u");
        User target = null;
        if (id == -1 && requester.hasRole(Role.ADMIN)) {
            // create new user with random password
            target = new User();
            target.setPassword(encodePassword(generateRandomBase64Token(12)));
            target.setEnabled(true);
            entityManager.persist(target);
            entityManager.flush(); // forces DB to add user & assign valid id
            id = target.getId();   // retrieve assigned id from DB
        }
        
        // retrieve requested user
        target = entityManager.find(User.class, id);
        model.addAttribute("user", target);
		
		if (requester.getId() != target.getId() &&
				! requester.hasRole(Role.ADMIN)) {
			throw new NoEsTuPerfilException();
		}
		
		if (edited.getPassword() != null) {
            if ( ! edited.getPassword().equals(pass2)) {
                // FIXME: complain
            } else {
                // save encoded version of password
                target.setPassword(encodePassword(edited.getPassword()));
            }
		}		
		target.setUsername(edited.getUsername());


		// update user session so that changes are persisted in the session, too
        if (requester.getId() == target.getId()) {
            session.setAttribute("u", target);
        }

		return "profile";
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
        File f = localData.getFile("pics", ""+id+".jpg");
        InputStream in = new BufferedInputStream(f.exists() ?
            new FileInputStream(f) : UserController.defaultPic());
        return os -> FileCopyUtils.copy(in, os);
    }

    /**
     * Uploads a profile pic for a user id
     * 
     * @param id
     * @return
     * @throws IOException
     */
    @PostMapping("{id}/pic")
    public String setPic(@RequestParam("photo") MultipartFile photo, @PathVariable long id, 
        HttpServletResponse response, HttpSession session, Model model) throws IOException {

        User target = entityManager.find(User.class, id);
        model.addAttribute("user", target);
		
		// check permissions
		User requester = (User)session.getAttribute("u");
		if (requester.getId() != target.getId() &&
				! requester.hasRole(Role.ADMIN)) {
            throw new NoEsTuPerfilException();
		}
		
		log.info("Updating photo for user {}", id);
		File f = localData.getFile("user", ""+id);
		if (photo.isEmpty()) {
			log.info("failed to upload photo: emtpy file?");
		} else {
			try (BufferedOutputStream stream =
					new BufferedOutputStream(new FileOutputStream(f))) {
				byte[] bytes = photo.getBytes();
				stream.write(bytes);
                log.info("Uploaded photo for {} into {}!", id, f.getAbsolutePath());
			} catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				log.warn("Error uploading " + id + " ", e);
			}
		}
		return "profile";
    }
    
    /**
     * Returns JSON with all received messages
     */
    @GetMapping(path = "received", produces = "application/json")
	@Transactional // para no recibir resultados inconsistentes
	@ResponseBody  // para indicar que no devuelve vista, sino un objeto (jsonizado)
	public List<Message.Transfer> retrieveMessages(HttpSession session) {
		long userId = ((User)session.getAttribute("u")).getId();		
		User u = entityManager.find(User.class, userId);
		log.info("Generating message list for user {} ({} messages)", 
				u.getUsername(), u.getReceived().size());
		return  u.getReceived().stream().map(Transferable::toTransfer).collect(Collectors.toList());
	}	
    
    /**
     * Returns JSON with count of unread messages 
     */
	@GetMapping(path = "unread", produces = "application/json")
	@ResponseBody
	public String checkUnread(HttpSession session) {
		long userId = ((User)session.getAttribute("u")).getId();		
		long unread = entityManager.createNamedQuery("Message.countUnread", Long.class)
			.setParameter("userId", userId)
			.getSingleResult();
		session.setAttribute("unread", unread);
		return "{\"unread\": " + unread + "}";
    }
    
    /**
     * Posts a message to a user.
     * @param id of target user (source user is from ID)
     * @param o JSON-ized message, similar to {"message": "text goes here"}
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
				User.class, ((User)session.getAttribute("u")).getId());
		model.addAttribute("user", u);
		
		// construye mensaje, lo guarda en BD
		Message m = new Message();
		m.setRecipient(u);
		m.setSender(sender);
		m.setDateSent(LocalDateTime.now());
		m.setText(text);
		entityManager.persist(m);
		entityManager.flush(); // to get Id before commit
		
		// construye json
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();
		rootNode.put("from", sender.getUsername());
		rootNode.put("to", u.getUsername());
		rootNode.put("text", text);
		rootNode.put("id", m.getId());
		String json = mapper.writeValueAsString(rootNode);
		
		log.info("Sending a message to {} with contents '{}'", id, json);

		messagingTemplate.convertAndSend("/user/"+u.getUsername()+"/queue/updates", json);
		return "{\"result\": \"message sent.\"}";
	}


	

}