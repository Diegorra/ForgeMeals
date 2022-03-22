package es.ucm.fdi.iw.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
                        + "WHERE u.username = :username")
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

    private boolean enabled;
    private String roles; // split by ',' to separate roles

    @OneToMany
    private List<Recipe> recipes = new ArrayList<>();
    @OneToMany
    private List<WeekPlanMeal> meals = new ArrayList<>();

	@OneToMany
	@JoinColumn(name = "sender_id")
	private List<Message> sent = new ArrayList<>();
	@OneToMany
	@JoinColumn(name = "recipient_id")	
	private List<Message> received = new ArrayList<>();		
    

    // se llama al clickar botón + en una tabla weekplan.
    public Boolean assignMeal(int dia, int hora, String receta){ // 0-6 = L-D, 0-3 = desayuno, comida, cena, snacks
        WeekPlanMeal nuevo = new WeekPlanMeal();
        if(!nuevo.intToWeekDay(dia) || !nuevo.intToTime(hora))
            return false; // mejor una excepción
        nuevo.setName(receta);    
        // borrar lo que haya en esa celda
        removeMeal(dia,hora);
        meals.add(nuevo);
        return true;
    }

    public void removeMeal(int dia, int hora){
        for(WeekPlanMeal meal : meals){
            if(meal.inCell(dia, hora)){
                meals.remove(meal);
            }
        }
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
}

