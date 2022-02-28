package es.ucm.fdi.iw.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private Long id;

    private User user;
    private String name;
    private String src;
    private ArrayList<RecipeIngredient> ingredients = new ArrayList<>();
    private String description;
    private BigDecimal price;
}
