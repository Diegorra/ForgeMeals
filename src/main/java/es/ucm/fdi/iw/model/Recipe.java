package es.ucm.fdi.iw.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@NamedQueries({
    @NamedQuery(
        name="findRecipeWithName",
        query="SELECT r FROM Recipe r WHERE LOWER(r.name) LIKE CONCAT('%', LOWER(:name), '%')")
    
})
public class Recipe implements Transferable<Recipe.Transfer> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private long id;

    @ManyToOne
    private User author;

    @NonNull private String name;

    @NonNull
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="RecipeIngredient_id")
    private List<RecipeIngredient> ingredients = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name="recipe_id")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany
    @JoinColumn(name="recipe_id")
    private List<WeekPlanMeal> weekPlans = new ArrayList<>();

    @OneToMany
    @JoinColumn(name="recipe_id")
    private List<OrderRecipe> orderRecipes = new ArrayList<>();

    @ElementCollection
    private List<String> noOfficialIngredients = new ArrayList<>();


    private String description;
    @NonNull private BigDecimal price;
    private String dateRegistered;
    private Integer averageRating;
    


    public Recipe(Recipe anotherRecipe){
        this.name = anotherRecipe.name;
        this.author = anotherRecipe.author;
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

    public void setAverageRating(Integer rating){
        this.averageRating = (this.averageRating + rating)/2;
    }

    @Getter
    @AllArgsConstructor
    public static class Transfer {
        private long id;
        private String name;
        private List<RecipeIngredient.Transfer> ingredients;
    }

    @Override
    public Transfer toTransfer() {
        return new Transfer(id, name, ingredients.stream().map(Transferable::toTransfer).collect(Collectors.toList()));
    }

    public void actPrecio(){
        BigDecimal precio = BigDecimal.valueOf(0);
        for(RecipeIngredient r:ingredients){
            precio = precio.add(r.getIngredient().getPrice().multiply(BigDecimal.valueOf(r.getQuantity())));
        }
        this.price = precio;
    }

}
