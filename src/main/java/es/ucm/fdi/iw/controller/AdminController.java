package es.ucm.fdi.iw.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.JsonNode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import es.ucm.fdi.iw.model.Comment;
import es.ucm.fdi.iw.model.Recipe;
import es.ucm.fdi.iw.model.User;

/**
 *  Site administration.
 *
 *  Access to this end-point is authenticated.
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
	private EntityManager entityManager;
    
	private static final Logger log = LogManager.getLogger(AdminController.class);

	@GetMapping("/")
    public String index(Model model) {
        return "/Default/admin";
    }

    /*----------------------------------------------------------------------Manejo admin------------------------------------------------------------------------------*/

	
@GetMapping("/ad")
public String admin(Model model, HttpSession session){
    User requester = (User)session.getAttribute("u");
    List<User> users = entityManager.createNamedQuery("User.excludeUser", User.class).setParameter("id", requester.getId()).getResultList();
    model.addAttribute("users",users);
    return "adminUserList";
}

@Transactional
@ResponseBody
@PostMapping("/removeUser")
public String removeUser(Model model,  @RequestBody JsonNode data){
    Long id = data.get("user").asLong();
    User user = entityManager.find(User.class, id);
    
    List<Comment> authorComments =  entityManager.createNamedQuery("Comment.byAuthor", Comment.class).setParameter("iId", user.getId()).getResultList();
    for(Comment c: authorComments){
        entityManager.remove(c);
    }
    entityManager.flush();
    entityManager.remove(user);
    entityManager.flush();
    return "{}";
}

@Transactional
@ResponseBody
@PostMapping("/convertAdmin")
public String convertAdmin(Model model,  @RequestBody JsonNode data){
    Long id = data.get("user").asLong();
    User user = entityManager.find(User.class, id);
    if(!user.hasRole(User.Role.ADMIN)){
        user.setRoles("ADMIN," + user.getRoles());
    }
    entityManager.flush();
    return "{}";
}

}

