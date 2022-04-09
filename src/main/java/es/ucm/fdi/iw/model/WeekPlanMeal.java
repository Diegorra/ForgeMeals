package es.ucm.fdi.iw.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data

@NoArgsConstructor
@RequiredArgsConstructor
@Table(name="WeekPlanMeal")
public class WeekPlanMeal{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private long id;
    
    // las celdas se identifican por día y comida del día. Ej: celda 2,3 = merienda del miércoles
    public enum WeekDay{Lunes, Martes, Miércoles, Jueves, Viernes, Sábado, Domingo};
    public enum DayTime{ Desayuno, Comida, Cena, Snack};

    
    @ManyToOne
    @NonNull private Recipe recipe; // TODO para obtener el nombre recipe.getName();
   // @NonNull private String name;
    @NonNull private WeekDay weekday;
    @NonNull private DayTime time;

    public String getName(){
        if(recipe != null) return recipe.getName();
        return "-";
    }

     
    public Boolean inCell(int day, int time){
        if(weekday.ordinal() == day && this.time.ordinal() == time){
            return true; 
        }
        return false;
    }
   
}