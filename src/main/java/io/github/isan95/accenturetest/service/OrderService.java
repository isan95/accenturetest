package io.github.isan95.accenturetest.service;

import java.util.List;

import io.github.isan95.accenturetest.entity.Order;
import io.github.isan95.accenturetest.entity.OrderProduct;
import io.github.isan95.accenturetest.payload.request.OrderRequest;

public interface OrderService {
	
    Iterable<Order> getAllOrders();
    
    Order findOrderById(Long id);

    Order create(Order order);

    void update(Order order);
    
    void delete(Order order);
    
    Order updateOrder(Order order);
    
    void settingOrder(Order order, List<OrderProduct>  orderProduct);
    
    List<OrderProduct>  convertOrderRequestToOrderProduct (List<OrderRequest> listProduct, Order order);
    
    boolean isUpdateTime(Order order);
    
    boolean equalsUser (Order order);
    
    boolean isEqualOrGreaterTotal(Order order, List<OrderRequest> productList);
}
