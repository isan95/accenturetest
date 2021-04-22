package io.github.isan95.accenturetest.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.isan95.accenture.exception.ResourceNotFoundException;
import io.github.isan95.accenturetest.entity.Order;
import io.github.isan95.accenturetest.entity.Product;
import io.github.isan95.accenturetest.payload.request.OrderRequest;
import io.github.isan95.accenturetest.repository.OrderRepository;
import io.github.isan95.accenturetest.repository.ProductRepository;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

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

}
