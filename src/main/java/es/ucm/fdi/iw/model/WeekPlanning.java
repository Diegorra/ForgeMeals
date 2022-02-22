package es.ucm.fdi.iw.model;

@Data
@Entity
@NoArgsConstructor
public class WeekPlanning{
    private long id;
    private long user;
    private ArrayList<long> recipes;

}