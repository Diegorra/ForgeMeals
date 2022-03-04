package es.ucm.fdi.iw.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private long id;

    @ManyToOne
    private User author;

    private String name;
    private String src;

    @OneToMany
    private List<RecipeIngredient> ingredients = new ArrayList<>();

    private String description;
    private BigDecimal price;
    public Recipe(String name, BigDecimal price, List<RecipeIngredient> ingredients){
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
    }
}
