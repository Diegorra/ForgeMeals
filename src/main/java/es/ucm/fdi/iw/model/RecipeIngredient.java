package es.ucm.fdi.iw.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RecipeIngredient implements Transferable<RecipeIngredient.Transfer> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private Long id;

    @ManyToOne
    private Ingredient ingredient;
    private Integer quantity;

    @Getter
    @AllArgsConstructor
    public static class Transfer {
        private Ingredient.Transfer ingredient;
        private Integer quantity;
    }

    @Override
    public RecipeIngredient.Transfer toTransfer() {
        return new Transfer(ingredient.toTransfer(), quantity);
    }

    @Override
    public String toString() {
        return toTransfer().toString();
    }

}
