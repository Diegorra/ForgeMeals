package es.ucm.fdi.iw.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/contact")
    public String contact(Model model){return "contact";}

    @GetMapping("/addRecipe")
    public String newRecipe(Model model){return "/Forms/recipeForm";}

    @GetMapping("/checkout")
    public String checkout(Model model){return "checkout";}

	@GetMapping("/")
    public String index(Model model) {
        return "index";
    }

}
