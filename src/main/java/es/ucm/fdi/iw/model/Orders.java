package es.ucm.fdi.iw.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Orders{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private long id;


    public enum State{CHECKOUT, RECEIVED, PROCESSED,SEND}

    @ManyToOne
    @NonNull
    private User user;

    @NonNull
    private State state;

    private String direction;

    @NonNull
    private BigDecimal price;

    private LocalDateTime dateRegistered;

    @OneToMany
    @JoinColumn(name="orders_id")
    @NonNull
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
    public void removeRecipe(long id){
        this.recipes.removeIf(x -> x.getId() == id);
    }
    public void changeQuant(long id, int quant){
        recipes.forEach(e -> {
            if( e.getId() == id){
                e.setQuantity(quant);
            }
        });
    }
}