package io.github.isan95.accenturetest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.isan95.accenturetest.entity.Order;
import io.github.isan95.accenturetest.entity.OrderProduct;
import io.github.isan95.accenturetest.entity.Product;
import io.github.isan95.accenturetest.payload.request.OrderRequest;
import io.github.isan95.accenturetest.service.OrderProductServiceImpl;
import io.github.isan95.accenturetest.service.OrderServiceImpl;
import io.github.isan95.accenturetest.service.ProductServiceImpl;
import io.github.isan95.accenturetest.service.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	@Autowired
	private OrderServiceImpl orderService;
	
	@Autowired
	private OrderProductServiceImpl orderProductService;
	
	@Autowired
	private ProductServiceImpl productService;
	
	@PostMapping("/create")
	public ResponseEntity<?> createOrder(@RequestBody List<OrderRequest> productList ){
		/*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();*/
		Order order = new Order();
		this.orderService.create(order);
		List<OrderProduct> orderProducts = new ArrayList<>();
	    for(OrderRequest product : productList) {
	    	orderProducts.add(orderProductService.create(new OrderProduct(order, productService.getProduct(product.getIdProduct()), product.getQuantity())));
	    }

	    order.setOrderProducts(orderProducts);
	    this.orderService.update(order);
		
	    return new ResponseEntity<>(order,HttpStatus.CREATED);
		
	}
}
