package es.ucm.fdi.iw.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Ingredient {

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

    private Integer units;

    private String unitsMeasure;

    private BigDecimal price;

    private Allergen allergen;

}
