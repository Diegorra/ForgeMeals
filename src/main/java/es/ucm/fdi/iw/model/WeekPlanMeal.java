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


    // deprecated methods
    public Boolean intToWeekDay(int dia){
        // en vd es un setter
        if(dia >= 0 && dia < 7){
            switch(dia){
                case 0: weekday = WeekDay.Lunes;
                        break;
                case 1: weekday = WeekDay.Martes;
                        break;
                case 2: weekday = WeekDay.Miércoles;                                                                       
                        break;
                case 3: weekday = WeekDay.Jueves;
                        break;
                case 4: weekday = WeekDay.Viernes;
                        break;
                case 5: weekday = WeekDay.Sábado;
                        break;
                case 6: weekday = WeekDay.Domingo;
                        break;
            }
            return true;
        }
        else return false; //estaria mejor lanzar exception
    }
    public Boolean intToTime(int hora){
        // en vd es un setter
        if(hora >= 0 && hora < 4){
            switch(hora){
                case 0: time = DayTime.Desayuno;
                        break;
                case 1: time = DayTime.Comida;
                        break;
                case 2: time = DayTime.Cena;                                                                       
                        break;
                case 3: time = DayTime.Snack;
                        break;
            }
            return true;
        }
        else return false; //estaria mejor lanzar exception
    }

     
    public Boolean inCell(int day, int time){
        if(weekday.ordinal() == day && this.time.ordinal() == time){
            return true; 
        }
        return false;
    }
   
}