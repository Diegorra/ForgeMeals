package es.ucm.fdi.iw.model;
import lombok.Data;
import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class WeekPlanning{
    
    public enum WeekDay{L, M, X, J, V, S, D};
    public enum Time{ D, A, C, M}; // Desayuno, A = comida, Cena, Merienda

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private long id;
    private User user;
   
    @Data
    private class Meal{
        private OrderRecipe recipe;
        private WeekDay weekday;
        private Time time; 

        public Meal(){
            weekday = WeekDay.L;
            time = Time.D;
            recipe = new OrderRecipe(new Recipe("comidita ñam", " ", null), null);
        }
    }
    // TODO se obtiene del controller creo
    private List<Meal> meals = new ArrayList<>();

    // las celdas se identifican por día y comida del día. Ej: celda 2,3 = merienda del miércoles 
    public String getMealByCell(int day, int time){
        for(Meal meal: meals){
            if(meal.getWeekday().ordinal() == day && meal.getTime().ordinal() == time){
                return meal.getRecipe().getRecipe().getName(); // order -> recipe -> string
            }
        }

        return "none";
    }
   
}