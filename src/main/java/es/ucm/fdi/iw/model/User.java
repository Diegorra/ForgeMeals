package es.ucm.fdi.iw.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import es.ucm.fdi.iw.model.WeekPlanMeal.DayTime;
import es.ucm.fdi.iw.model.WeekPlanMeal.WeekDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An authorized user of the system.
 */
@Entity
@Data
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name="User.byUsername",
                query="SELECT u FROM User u "
                        + "WHERE u.username = :username AND u.enabled = TRUE"),
        @NamedQuery(name="User.hasUsername",
                query="SELECT COUNT(u) "
                        + "FROM User u "
                        + "WHERE u.username = :username"),
        @NamedQuery(name="User.excludeUser",
                query = "SELECT u FROM User u"
                        + " WHERE u.id != :id")
})
@Table(name="IWUser")
public class User implements Transferable<User.Transfer> {

    public enum Role {
        USER,			// normal users 
        ADMIN,          // admin users
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
	private long id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    private String src;
    private String email;
    private String address;
    
    private boolean enabled;
    private String roles; // split by ',' to separate roles

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name="author_id")
    private List<Recipe> recipes = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<WeekPlanMeal> meals = new ArrayList<>();

    @JoinColumn(name="author_id")
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    
 

	@OneToMany
	@JoinColumn(name = "sender_id")
	private List<Message> sent = new ArrayList<>();
	@OneToMany
	@JoinColumn(name = "recipient_id")	
	private List<Message> received = new ArrayList<>();		
    

    // se llama al clickar botón + en una tabla weekplan.
    public void assignMeal(Recipe receta, WeekDay dia, DayTime hora, EntityManager em){ 
      
        WeekPlanMeal nuevo = new WeekPlanMeal(receta, dia, hora);
        removeMeal(dia,hora, em);
        meals.add(nuevo);
        em.persist(nuevo);
    }

    public void removeMeal(WeekDay dia, DayTime hora, EntityManager em) {
        for(WeekPlanMeal m : meals){
            if(m.getTime() == hora && m.getWeekday() == dia){
                meals.remove(m);
                em.remove(m);
                return;
            }
        }
            
    }


    public WeekPlanMeal getMealByCell(WeekDay dia, DayTime hora){
        for(WeekPlanMeal m : meals){
            if(m.getWeekday() == dia && m.getTime() == hora) // antes if(m.inCell(dia, hora))
                return m;
        }
        return new WeekPlanMeal(); // vacío
    }

    

    /**
     * Checks whether this user has a given role.
     * @param role to check
     * @return true iff this user has that role.
     */
    public boolean hasRole(Role role) {
        String roleName = role.name();
        return Arrays.asList(roles.split(",")).contains(roleName);
    }

    @Getter
    @AllArgsConstructor
    public static class Transfer {
		private long id;
        private String username;
		private int totalReceived;
		private int totalSent;
    }

	@Override
    public Transfer toTransfer() {
		return new Transfer(id,	username, received.size(), sent.size());
	}
	
	@Override
	public String toString() {
		return toTransfer().toString();
	}

    public Boolean isAdmin(){
        return hasRole(Role.ADMIN);
    }
}

