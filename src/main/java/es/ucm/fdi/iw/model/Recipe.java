package es.ucm.fdi.iw.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@NamedQueries({
    @NamedQuery(
        name="findRecipeWithName",
        query="SELECT r FROM Recipe r WHERE LOWER(r.name) LIKE CONCAT('%', LOWER(:name), '%')")
})

@Table(name="Recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private long id;

    @ManyToOne
    private User author;

    @NonNull private String name;
    @NonNull private String src;

    @NonNull
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="RecipeIngredient_id")
    private List<RecipeIngredient> ingredients = new ArrayList<>();

    @OneToMany
    @JoinColumn(name="recipe_id")
    private List<Comment> comments = new ArrayList<>();

    private String description;
    @NonNull private BigDecimal price;
    private String dateRegistered;
    private Integer averageRating;


    public Recipe(Recipe anotherRecipe){
        this.name = anotherRecipe.name;
        this.author = anotherRecipe.author;
        this.src = anotherRecipe.src;
        for (RecipeIngredient ingredient : anotherRecipe.ingredients) {
            this.ingredients.add(ingredient);
        }
        this.price = anotherRecipe.price;
        this.description = anotherRecipe.description;
    }


    public void setDateRegistered(LocalDateTime date) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.dateRegistered = date.format(format);
    }
}
