package org.order.repository.orderRepository;

/**
 * Created by user on 10.04.16.
 */

import org.order.model.Order;
import java.util.List;

public interface OrderService {
    List<Order> findAll();
    Order findById(Long Id);
    Order save(Order order);
}
