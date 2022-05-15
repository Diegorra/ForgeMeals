package es.ucm.fdi.iw.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@RequiredArgsConstructor

@NamedQueries({
    @NamedQuery(name="Comment.byAuthor",
            query="SELECT i FROM Comment i "
                    + "WHERE i.author.id = :iId")
})

public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private long id;

    @ManyToOne
    @NonNull
    private User author;

    @ManyToOne
    @NonNull
    private Recipe recipe;

    @NonNull
    private String text;

    @NonNull
    private Integer assessment;

    public Comment() {
    }
}
