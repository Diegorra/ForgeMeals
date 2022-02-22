package es.ucm.fdi.iw.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;

@Data
@Entity
@NoArgsConstructor
public class WeekPlanning{
    private long id;
    private long user;
    private ArrayList<Long> recipes;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}