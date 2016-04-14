package org.order.repository.orderRepository;

/**
 * Created by user on 08.04.16.
 */
import org.order.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

//@Repository
//@Configuration
//@EnableAutoConfiguration
//@EntityScan(basePackages = {"org.psg.model"})
public interface OrderRepository extends CrudRepository<Order,Long> {
}
