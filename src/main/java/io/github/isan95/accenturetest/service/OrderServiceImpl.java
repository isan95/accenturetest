package io.github.isan95.accenturetest.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.isan95.accenture.exception.ResourceNotFoundException;
import io.github.isan95.accenturetest.entity.Order;
import io.github.isan95.accenturetest.entity.OrderProduct;
import io.github.isan95.accenturetest.entity.Product;
import io.github.isan95.accenturetest.filter.UserSesion;
import io.github.isan95.accenturetest.payload.request.OrderRequest;
import io.github.isan95.accenturetest.repository.OrderProductRepository;
import io.github.isan95.accenturetest.repository.OrderRepository;
import io.github.isan95.accenturetest.repository.OrderStatusRepository;
import io.github.isan95.accenturetest.repository.ProductRepository;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	
	private final static long HOURSTOUPDATE = 5;
	
	private final static long HOURSTODELETE = 12;

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
		if (order.getSubTotalOrderPrice() > 70000) {
			order.setIva(order.getSubTotalOrderPrice() * order.getIvaPercent());
			order.setTotal(order.getSubTotalOrderPrice() + order.getIva() + order.getShipping());
			this.update(order);
			return order;
		}
		if(order.getSubtotal()>100000) {
			order.setIva(order.getSubtotal() * order.getIvaPercent());
			order.setTotal(order.getSubtotal() + order.getIva());
			order.setShipping(0);
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

	@Override
	public boolean isUpdateTime(Order order) {
		
		return  hoursAgo(order)<=HOURSTOUPDATE;
	}
	
	private long hoursAgo(Order order) {
		
		Long secondsAgo = Duration.between(order.getDateCreated(), LocalDateTime.now()).getSeconds();
		
		return secondToHours(secondsAgo);
	}
	
	private long secondToHours(Long seconds) {
		
		return seconds/3600;
	}

	@Override
	public boolean equalsUser(Order order) {
		
		return order.getUser().getUsername().equals(UserSesion.getCurrentUsername());
	}

	@Override
	public boolean isEqualOrGreaterTotal(Order order, List<OrderRequest> productList) {
		double subtotalOld = order.getSubTotalOrderPrice();
		List<OrderProduct> listProduct = convertOrderRequestToOrderProduct(productList, order);
		order.setOrderProducts(listProduct);
		
		double subtotalNew = order.getSubTotalOrderPrice();
		
		return subtotalNew >= subtotalOld;
	}

	@Override
	public boolean isDeleteTime(Order order) {
		
		return hoursAgo(order) <= HOURSTODELETE;
	}

	@Override
	public void deleteOrderAfterTime(Order order) {
		double PercentOrder10 = order.getSubTotalOrderPrice()*10/100;
		order.setIva(0);
		order.setOrderProducts(null);
		order.setShipping(0);
		order.setStatus(orderStatusRepository.findById(3).orElseThrow(()->
	    new ResourceNotFoundException("Error: Estado no encontrado")) );
		order.setSubtotal(PercentOrder10);
		order.setTotal(PercentOrder10);
		orderRepository.save(order);
		
	}

}
