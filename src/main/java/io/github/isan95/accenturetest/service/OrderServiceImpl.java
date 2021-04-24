package io.github.isan95.accenturetest.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.isan95.accenture.exception.ResourceNotFoundException;
import io.github.isan95.accenturetest.entity.Order;
import io.github.isan95.accenturetest.entity.OrderProduct;
import io.github.isan95.accenturetest.entity.Product;
import io.github.isan95.accenturetest.payload.request.OrderRequest;
import io.github.isan95.accenturetest.repository.OrderProductRepository;
import io.github.isan95.accenturetest.repository.OrderRepository;
import io.github.isan95.accenturetest.repository.OrderStatusRepository;
import io.github.isan95.accenturetest.repository.ProductRepository;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderProductRepository orderProductRepository;
	
	@Autowired
	private OrderStatusRepository orderStatusRepository;

	@Override
	public Iterable<Order> getAllOrders() {

		return orderRepository.findAll();
	}

	@Override
	public Order create(Order order) {

		return orderRepository.save(order);
	}

	@Override
	public void update(Order order) {

		orderRepository.save(order);
	}

	@Override
	public void delete(Order order) {

		orderRepository.delete(order);
	}

	@Override
	public Order updateOrder(Order order) {
		if (order.getSubtotal() > 70000) {
			order.setIva(order.getSubtotal() * order.getIvaPercent());
			order.setTotal(order.getSubtotal() + order.getIva() + order.getShipping());
			this.update(order);
			return order;
		}
		if(order.getSubtotal()>100000) {
			order.setIva(order.getSubtotal() * order.getIvaPercent());
			order.setTotal(order.getSubtotal() + order.getIva());
			this.update(order);
			return order;
		}
		else {
			return null;
		}

	}
	
	@Override
	public Order findOrderById(Long id) {
		
		return orderRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Error: Orden no encontrada"));
	}

	@Override
	public void settingOrder(Order order, List<OrderProduct> orderProducts) {
		order.setOrderProducts(orderProducts);
	    order.setStatus(orderStatusRepository.findById(1).orElseThrow(()->
	    new ResourceNotFoundException("Error: Estado no encontrado")));
	    order.setSubtotal(order.getSubTotalOrderPrice());		
	}

	@Override
	public List<OrderProduct> convertOrderRequestToOrderProduct(List<OrderRequest> listProduct, Order order) {
		
		List<OrderProduct> orderProducts = new ArrayList<>();
	    for(OrderRequest product : listProduct) {
	    	OrderProduct orderProduct = new OrderProduct(order, productRepository.findById(product
	    			.getIdProduct()).orElseThrow(()->
	    			new ResourceNotFoundException("Error: Producto no enconntrado")),
	    			product.getQuantity());
	    	orderProducts.add(orderProductRepository.save(orderProduct));
	    }
	    return orderProducts;
	}

}
