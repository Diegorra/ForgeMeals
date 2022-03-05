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
    public String index(Model model) {
        return "index";
    }

}
