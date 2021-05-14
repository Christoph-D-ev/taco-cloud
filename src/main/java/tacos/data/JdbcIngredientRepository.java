package tacos.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tacos.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcIngredientRepository implements IngredientRepository{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbcTemplate.query(
          "select id,name,type from Ingredient",
          this::mapRowToIngredient);
    }

    @Override
    public Optional<Ingredient> findById(String id) {
        List<Ingredient> queryResult = jdbcTemplate.query(
                "select id,name,type from Ingredient where id=?",
                this::mapRowToIngredient, id);
        //return the first element if there is one
        return queryResult.size()==0 ?
                Optional.empty() : Optional.of(queryResult.get(0));
    }

    private Ingredient mapRowToIngredient(ResultSet rset,int rowNumber) throws SQLException {
        return new Ingredient(rset.getString("id"),
                rset.getString("name"),
                Ingredient.Type.valueOf(rset.getString("type")));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbcTemplate.update(
                "insert into Ingredient (id, name, type) values (?, ?, ?, ?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString()
        );
        return ingredient;
    }
}
