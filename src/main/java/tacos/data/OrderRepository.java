package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.Order;
import tacos.Taco;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order,Long> {

    List<Order> findByZip(String zip);
}
