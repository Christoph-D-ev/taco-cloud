package tacos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tacos.Ingredient;
import tacos.data.IngredientRepository;

import java.util.HashMap;
import java.util.Map;


@Component
public class IngredientConverter implements Converter<String, Ingredient> {


    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientConverter(IngredientRepository ingredientRepository){
        this.ingredientRepository=ingredientRepository;
    }

    @Override
    public Ingredient convert(String s) {
        return ingredientRepository.findById(s).orElse(null);
    }

    /*
    //old
    private Map<String,Ingredient> ingredientMap=new HashMap<>();

    public IngredientConverter(){
        ingredientMap.put("FLTO",new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
        ingredientMap.put("COTO",new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
        ingredientMap.put("GRBF", new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
        ingredientMap.put("CARN",new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
        ingredientMap.put("TMTO",new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGETABLE));
        ingredientMap.put("LETC",new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGETABLE));
        ingredientMap.put("CHED",new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
        ingredientMap.put("JACK",new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
        ingredientMap.put("SLSA",new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
        ingredientMap.put("SRCR",new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));

    }

    @Override
    public Ingredient convert(String id){
        return ingredientMap.get(id);
    }
    */




}
