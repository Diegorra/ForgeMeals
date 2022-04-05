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

    
    //@ManyToOne
    //@NonNull private OrderRecipe recipe; // TODO en verdad seria un Recipe. Para obtener el nombre recipe.getRecipe().getName();
    @NonNull private String name;
    @NonNull private WeekDay weekday;
    @NonNull private DayTime time;


   
    public static WeekDay strToWeekDay(String dia){
        
        switch(dia){
        case "Lunes": return WeekDay.Lunes;
        case "Martes": return WeekDay.Martes;
        case "Miércoles": return WeekDay.Miércoles;                                                                             
        case "Jueves": return WeekDay.Jueves;    
        case "Viernes": return WeekDay.Viernes;
        case "Sábado": return WeekDay.Sábado;
        case "Domingo": return WeekDay.Domingo;
        }

        return WeekDay.Lunes;
}
public static DayTime strToDayTime(String time){
       
        switch(time){
        case "Desayuno": return DayTime.Desayuno;
        case "Comida": return DayTime.Comida;
        case "Cena": return DayTime.Cena;                                                                             
        case "Snack": return DayTime.Snack;    
        }

        return DayTime.Desayuno;
}

     
    public Boolean inCell(int day, int time){
        if(weekday.ordinal() == day && this.time.ordinal() == time){
            return true; 
        }
        return false;
    }
   
}