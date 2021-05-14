package tacos;

import lombok.Data;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;


@Data
@Entity
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min=5,message = "Name must be longer than 4 characters")
    private String name;

    @ManyToMany
    @Size(min=1,message = "Add one Ingredient")
    private List<Ingredient> ingredients;

    private Date createdAt = new Date();

    @PrePersist
    void createdAt(){
        this.createdAt=new Date();
    }
}
