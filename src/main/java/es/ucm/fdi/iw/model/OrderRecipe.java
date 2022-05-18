package es.ucm.fdi.iw.model;

import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.Order;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRecipe implements Transferable<OrderRecipe.Transfer> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private long id;

    @ManyToOne
    private Orders order;

    @ManyToOne
    private Recipe recipe;

    private Integer quantity;

    @Getter
    @AllArgsConstructor
    public static class Transfer {
        private int quantity;
        private Recipe.Transfer recipe;
    }

    @Override
    public Transfer toTransfer() {
        return new OrderRecipe.Transfer(quantity, recipe.toTransfer());
    }

}
