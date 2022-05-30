package es.ucm.fdi.iw.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@NamedQueries({
    @NamedQuery(name="Ingredient.byName",
            query="SELECT i FROM Ingredient i "
                    + "WHERE i.name = :iname")
})
@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Ingredient implements Transferable<Ingredient.Transfer> {

    public enum Allergen{
        Null,
        CEREALES,
        CRUSTACEOS,
        HUEVOS,
        PESCADO,
        CACAHUETES,
        SOJA,
        LECHE,
        FRUTOS_DE_CASCARA,
        APIO,
        MOSTAZA,
        SESAMO,
        DIOXIDO_DE_AZUFRE_SULFITOS,
        ALTRAMUCES,
        MOLUSCOS
    };

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private long id;

    @NonNull private String name;

    private String info;

    @NonNull private Integer units;

    private String unitsMeasure;

    private BigDecimal price;

    private Allergen allergen;

    public Ingredient(String name, int quantity, String measure, BigDecimal price){
        this.name = name;
        this.units = quantity;
        this.unitsMeasure = measure;
        this.price = price;
    }

    @Getter
    @AllArgsConstructor
    public static class Transfer {
        private String name;
    }

    @Override
    public Transfer toTransfer() {
        return new Transfer(name);
    }

    @Override
    public String toString() {
        return toTransfer().toString();
    }


}
