package es.ucm.fdi.iw.model;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
public class WeekPlanning{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private long id;
    private long user;

    @OneToMany
    @JoinColumn(name="RecipeIngredient_id")
    private List<RecipeIngredient> recipes = new ArrayList<>();

}