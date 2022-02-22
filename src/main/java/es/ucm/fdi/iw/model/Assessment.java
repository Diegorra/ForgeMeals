package es.ucm.fdi.iw.model;


@Data
@Entity
@NoArgsConstructor
public class Assessment{
    private long id;
    private long user;
    private long receta;
    private int assessment;


}