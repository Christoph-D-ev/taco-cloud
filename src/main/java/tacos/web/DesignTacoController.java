package tacos.web;

import java.util.Arrays;
import java.util.List;

import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;

import javax.validation.Valid;


@Slf4j
@Controller
@RequestMapping("/design")

public class DesignTacoController {

    @ModelAttribute
    public void addIngredientsToModel(Model model){
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("WHTO","Wheat Tortilla",Type.WRAP),
                new Ingredient("COTO","Corn Tortilla",Type.WRAP),
                new Ingredient("GRBF","Beef",Type.PROTEIN),
                new Ingredient("SOYB","Soy beans",Type.PROTEIN),
                new Ingredient("TMTO","Tomato(diced)",Type.VEGETABLE),
                new Ingredient("LETC","Lettcue",Type.VEGETABLE),
                new Ingredient("CHDS","Cheddar(sharp)",Type.CHEESE),
                new Ingredient("CHDM","Cheddar(mild)",Type.CHEESE),
                new Ingredient("SUCR","Sour cream",Type.SAUCE),
                new Ingredient("GUAC","Guacamole",Type.SAUCE)
        );
        Type[] types = Ingredient.Type.values();
        for (Type t: types) {
            model.addAttribute(t.toString().toLowerCase(),
                    filterByType(ingredients,t));
        }
    }


    @GetMapping
    public String showDesignForm(Model model){
        model.addAttribute("design",new Taco());
        return "design";
    }

    @PostMapping
    public String processTaco(@Valid @ModelAttribute("taco") Taco taco, Errors errors){
        if(errors.hasErrors()){
            return "redirect:/design";
        }
        log.info("Processing: "+ taco);
        return "redirect:/orders/current";
    }


    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients,Type type){
        return ingredients.stream()
                .filter(x->x.getType().equals(type))
                .collect(Collectors.toList());
    }



}
