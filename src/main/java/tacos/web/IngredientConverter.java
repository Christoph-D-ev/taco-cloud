package tacos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tacos.Ingredient;
import tacos.data.IngredientRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Component
public class IngredientConverter implements Converter<String, Ingredient> {


    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientConverter(IngredientRepository ingredientRepository){
        this.ingredientRepository=ingredientRepository;
    }

    @Override
    public Ingredient convert(String id) {

        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
        return optionalIngredient.isPresent() ?
                optionalIngredient.get() : null;
    }
}
