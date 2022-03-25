package es.ucm.fdi.iw.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.*;

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
    
    public enum WeekDay{L, M, X, J, V, S, D};
    public enum Time{ B, L, D, S}; // Breakfast, Lunch, Dinner, Snack

    
    //@OneToOne
    @NonNull private String name; // TODO en verdad seria un Recipe. Para obtener el nombre recipe.getRecipe().getName();
    @NonNull private WeekDay weekday;
    @NonNull private Time time;

    public Boolean intToWeekDay(int dia){
        // en vd es un setter
        if(dia >= 0 && dia < 7){
            switch(dia){
                case 0: weekday = WeekDay.L;
                        break;
                case 1: weekday = WeekDay.M;
                        break;
                case 2: weekday = WeekDay.X;                                                                       
                        break;
                case 3: weekday = WeekDay.J;
                        break;
                case 4: weekday = WeekDay.V;
                        break;
                case 5: weekday = WeekDay.S;
                        break;
                case 6: weekday = WeekDay.D;
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
                case 0: time = Time.B;
                        break;
                case 1: time = Time.L;
                        break;
                case 2: time = Time.D;                                                                       
                        break;
                case 3: time = Time.S;
                        break;
            }
            return true;
        }
        else return false; //estaria mejor lanzar exception
    }


    // las celdas se identifican por día y comida del día. Ej: celda 2,3 = merienda del miércoles 
    public Boolean inCell(int day, int time){
        if(weekday.ordinal() == day && this.time.ordinal() == time){
            return true; 
        }
        return false;
    }
   
}