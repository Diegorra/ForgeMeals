package es.ucm.fdi.iw.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.net.http.WebSocket.Listener;
import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.iw.model.Recipe;
import es.ucm.fdi.iw.model.RecipeIngredient;
import es.ucm.fdi.iw.model.Ingredient;
import es.ucm.fdi.iw.model.OrderRecipe;

import javax.persistence.EntityManager;
import javax.persistence.SequenceGenerator;
import javax.transaction.Transactional;

/**
 *  Non-authenticated requests only.
 */
@Controller
public class RootController {

    @Autowired
    private EntityManager entityManager;

	private static final Logger log = LogManager.getLogger(RootController.class);

	@GetMapping("/login")
    public String login(Model model) {
        return "/Forms/login";
    }

    @GetMapping("/register")
    public String register(Model model){return "/Forms/register"; }

    @GetMapping("/contact")
    public String contact(Model model){return "contact";}

    @GetMapping("/")
    @Transactional
    public String index(Model model) {
        List<Recipe> recipes = entityManager.createQuery("select r from Recipe r", Recipe.class).getResultList();

        /*
        recipes.add(new Recipe("Pizza", "https://w6h5a5r4.rocketcdn.me/wp-content/uploads/2019/06/pizza-con-chorizo-jamon-y-queso-1080x671.jpg", new BigDecimal("3")));
        recipes.add(new Recipe("Hamburguesa", "https://okdiario.com/img/2021/05/28/hamburguesa-3-655x368.jpg", new BigDecimal("2.5")));
        recipes.add(new Recipe("Pasta Carbonara", "https://www.elespectador.com/resizer/VDIYcF2ol0HmQ3bC9SvoI7R23Es=/920x613/filters:format(jpeg)/cloudfront-us-east-1.images.arcpublishing.com/elespectador/TMTI6JW2CZETZOJTCUN3MQPHIY.jpg", new BigDecimal("7.99")));
        recipes.add(new Recipe("Pasta Boloñesa", "https://w6h5a5r4.rocketcdn.me/wp-content/uploads/2019/05/espaguetis-a-la-bolonesa-1080x671.jpg", new BigDecimal("7.99")));
        recipes.add(new Recipe("Perrito Caliente", "https://imag.bonviveur.com/perrito-caliente.jpg", new BigDecimal("1.99")));

        for (Recipe r : recipes) {
            entityManager.persist(r);
        }
*/
        model.addAttribute("recipes", recipes);
        return "index";
    }

}
