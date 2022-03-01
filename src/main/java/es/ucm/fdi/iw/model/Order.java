package es.ucm.fdi.iw.model;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
public class Order{

    public enum State{CHECKOUT, RECEIVED, PROCESSED,SEND}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private long id;

    @ManyToOne
    private User user;
    private State state;
    private String direction;

    @OneToMany
    private List<OrderRecipe> recipes = new ArrayList<>();

}