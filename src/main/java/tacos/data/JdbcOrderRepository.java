package tacos.data;


import org.springframework.asm.Type;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;


import tacos.Order;
import tacos.Taco;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcOrderRepository implements OrderRepository{

    private JdbcOperations jdbcOperations;

    public JdbcOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Order save(Order order) {
        //what is this
        PreparedStatementCreatorFactory preparedStatementCreatorFactory=
                new PreparedStatementCreatorFactory(
                        "insert into Taco_Order"
                        + "(name, street, city, state, zip,"
                        + "cc_number, cc_expiration, cc_cvv, placed_at)"
                        + "values (?,?,?,?,?,?,?,?,?)",
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,
                        Types.VARCHAR,Types.VARCHAR,Types.TIMESTAMP
                        );
        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);
        order.setPlacedAt(new Date());

        PreparedStatementCreator preparedStatementCreator =
                preparedStatementCreatorFactory.newPreparedStatementCreator(
                        Arrays.asList(
                                order.getName(),
                                order.getStreet(),
                                order.getCity(),
                                order.getState(),
                                order.getZip(),
                                order.getCcNumber(),
                                order.getCcExpiration(),
                                order.getCcCVV(),
                                order.getPlacedAt()
                        )
                );

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(preparedStatementCreator,keyHolder);
        long orderID = keyHolder.getKey().longValue();
        order.setId(orderID);

        List<Taco> tacos = order.getTacos();
        int i=0;
        for (Taco t: tacos) {
            saveTaco(orderID,i++,Taco);
        }

        return order;
    }

    private long saveTaco(long orderID,int i, Taco taco){
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory preparedStatementCreatorFactory=
                new PreparedStatementCreatorFactory(
                        "insert into Taco"
                        +"(name, created_at, taco_order, taco_order_key)"
                        +"values (?,?,?,?)",
                        Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG
                );
        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);

        PreparedStatementCreator preparedStatementCreator =
                preparedStatementCreatorFactory.newPreparedStatementCreator(
                        Arrays.asList(
                                taco.getName(),
                                taco.getCreatedAt(),
                                orderID,
                                i
                        )
                );
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcOperations.update(preparedStatementCreator,keyHolder);
        long tacoID = keyHolder.getKey().longValue();
        taco .setId(tacoID);
        saveIngredientRef(tacoID, taco.getIngredients());
        return tacoID;
    }

    private void saveIngredientRef(long tacoID,List<IngredientRef> ingredientRefs){
        int key =0;
        for (IngredientRef ref: ingredientRefs) {
            jdbcOperations.update(
                    "insert into Ingredient_Ref (ingredient, taco ,taco_key)"
                    + "values (?,?,?)",
                    ref.getIngredient(),tacoID,key++
            );

        }
    }

}
