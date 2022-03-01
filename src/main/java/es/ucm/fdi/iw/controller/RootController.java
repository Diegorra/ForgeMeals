package es.ucm.fdi.iw.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public String checkout(Model model){return "checkout";}

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
