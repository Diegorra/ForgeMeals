package es.ucm.fdi.iw.model;

@Entity
@Data
@NoArgsConstructor
public class Order{
    private long id;
    private long user;
    private String state;
    private String direction;
    private ArrayList<long> recipes;


}