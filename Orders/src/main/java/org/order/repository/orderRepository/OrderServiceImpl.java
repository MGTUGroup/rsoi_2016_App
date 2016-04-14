package org.order.repository.orderRepository;

/**
 * Created by user on 10.04.16.
 */

import com.google.common.collect.Lists;
import org.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;

    @Override
    @Transactional(readOnly=true)
    public List<Order> findAll() {
        return Lists.newArrayList(orderRepository.findAll());
    }

    @Override
    @Transactional(readOnly=true)
    public Order findById(Long id) {
        return orderRepository.findOne(id);
    }

    @Override
    public Order save(Order passenger) {
        return orderRepository.save(passenger);
    }


    @Autowired
    public void setPassengerRepository(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

}
