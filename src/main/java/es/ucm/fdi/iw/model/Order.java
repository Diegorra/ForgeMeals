package es.ucm.fdi.iw.model;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Data
@NoArgsConstructor
public class Order{

    public enum State{CHECKOUT, RECEIVED, PROCESSED,SEND}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private long id;

    private User user;
    private State state;
    private String direction;
    private ArrayList<OrderRecipe> recipes = new ArrayList<>();

}