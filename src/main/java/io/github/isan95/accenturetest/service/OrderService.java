package io.github.isan95.accenturetest.service;

import io.github.isan95.accenturetest.entity.Order;
import io.github.isan95.accenturetest.payload.request.OrderRequest;

public interface OrderService {
	
    Iterable<Order> getAllOrders();

    Order create(Order order);

    void update(Order order);
    
    void delete(Order order);
    
    Order updateOrder(Order order);
}
