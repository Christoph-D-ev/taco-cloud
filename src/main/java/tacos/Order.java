package tacos;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Order implements Serializable {
     private  static final long serialVersionUID=1L;

     private Long id ;
     private Date placedAt;

    @NotBlank
    private String name;
    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String zip;
    @CreditCardNumber
    private String ccNumber;
    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$")
    private String ccExpiration;
    @Digits(integer = 3,fraction = 0,message = "Invaild CVV")
    private String ccCVV;

    private List<Taco> tacos= new ArrayList<>();

    public void addTaco(Taco taco){
        this.tacos.add(taco);
    }


}
