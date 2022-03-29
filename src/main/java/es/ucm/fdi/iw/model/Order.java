package es.ucm.fdi.iw.model;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.math.BigDecimal;
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
    private BigDecimal price;

    @OneToMany
    @JoinColumn(name="OrderRecipe_id")
    private List<OrderRecipe> recipes = new ArrayList<>();

    public void actPrecio(){
        
        BigDecimal precio = BigDecimal.valueOf(0);
        for(OrderRecipe r:recipes){
            precio = precio.add(r.getRecipe().getPrice().multiply(BigDecimal.valueOf(r.getQuantity())));
        }

        this.price = precio;
    }
    public void addRecipe(OrderRecipe recipe){
        this.recipes.add(recipe);
    }
}