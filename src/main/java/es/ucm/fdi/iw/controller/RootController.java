package es.ucm.fdi.iw.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import javax.persistence.SequenceGenerator;

/**
 *  Non-authenticated requests only.
 */
@Controller
public class RootController {

	private static final Logger log = LogManager.getLogger(RootController.class);

	@GetMapping("/login")
    public String login(Model model) {
        return "/Forms/login";
    }

    @GetMapping("/register")
    public String register(Model model){return "/Forms/register"; }

    @GetMapping("/profile")
    public String profile(Model model){return "profile";}

    @GetMapping("/checkout")
    public String checkout(Model model){
        int cant = 4;
        List<OrderRecipe> recipes = new ArrayList<>();
        List<RecipeIngredient> ingredients = new ArrayList<>();
        ingredients.add(new RecipeIngredient(new Ingredient("harina")));
        ingredients.add(new RecipeIngredient(new Ingredient("huevo")));
        ingredients.add(new RecipeIngredient(new Ingredient("maiz")));
        ingredients.add(new RecipeIngredient(new Ingredient("patata")));

        recipes.add(new OrderRecipe(new Recipe("receta", new BigDecimal("4.99")),2));
        recipes.add(new OrderRecipe(new Recipe("receta1", new BigDecimal("7.99")),3));
        recipes.add(new OrderRecipe(new Recipe("receta2", new BigDecimal("2.50")),1));
        recipes.add(new OrderRecipe(new Recipe("receta3", new BigDecimal("3")),5));

        for(OrderRecipe recipe :  recipes){
            recipe.getRecipe().setIngredients(ingredients);
        }
        model.addAttribute("cantidad", cant);
        model.addAttribute("articles", recipes);
        return "checkout";
    }

    @GetMapping("/payment")
    public String payment(Model model){return "/Forms/payment";}

    @GetMapping("/settings")
    public String settings(Model model){return "settings";}

    @GetMapping("/weekplan")
    public String weekplan(Model model){return "weekPlan";}

    @GetMapping("/addRecipe")
    public String newRecipe(Model model){return "/Forms/recipeForm";}

    @GetMapping("/contact")
    public String contact(Model model){return "contact";}

	@GetMapping("/")
    public String index(Model model) {
        return "index";
    }

}
