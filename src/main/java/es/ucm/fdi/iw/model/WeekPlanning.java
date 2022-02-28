package es.ucm.fdi.iw.model;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;

@Data
@Entity
@NoArgsConstructor
public class WeekPlanning{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private long id;
    private long user;
    private ArrayList<RecipeIngredient> recipes;

}