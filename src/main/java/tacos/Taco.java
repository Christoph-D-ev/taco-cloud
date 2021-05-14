package tacos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class Taco {
    @NotNull
    @Size(min=5,message = "Name must be longer than 5 characters")
    private String name;

    @NotNull
    @Size(min=1,message = "Add one Ingredient")
    private List<Ingredient> ingredients;

    private Date createdAt = new Date();

    private Long id;

}
