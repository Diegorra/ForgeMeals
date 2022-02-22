package es.ucm.fdi.iw.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class recipeIngredient {
    private Long id;
    private Long ingredient;
    private Float cuantity;
    private List<Allergen> allergen;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
