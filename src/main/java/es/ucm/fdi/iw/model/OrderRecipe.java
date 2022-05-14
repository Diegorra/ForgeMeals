package es.ucm.fdi.iw.model;

import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.Order;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private long id;

    @ManyToOne
    private Orders order;

    @ManyToOne
    private Recipe recipe;

    private Integer quantity;

}
