package es.ucm.fdi.iw.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Data

@NoArgsConstructor
@RequiredArgsConstructor
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
    private List<Comment> comments = new ArrayList<>();

    private String description;
    @NonNull private BigDecimal price;

}
