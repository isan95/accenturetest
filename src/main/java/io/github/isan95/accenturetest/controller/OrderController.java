package io.github.isan95.accenturetest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.isan95.accenturetest.entity.Order;
import io.github.isan95.accenturetest.entity.OrderProduct;
import io.github.isan95.accenturetest.entity.UserApp;
import io.github.isan95.accenturetest.filter.UserSesion;
import io.github.isan95.accenturetest.payload.request.OrderRequest;
import io.github.isan95.accenturetest.payload.response.MessageResponse;
import io.github.isan95.accenturetest.service.OrderServiceImpl;
import io.github.isan95.accenturetest.service.ProductServiceImpl;
import io.github.isan95.accenturetest.service.UserDetailsServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private OrderServiceImpl orderService;


	@Autowired
	private ProductServiceImpl productService;

	@Autowired
	private UserDetailsServiceImpl userService;


	@PostMapping("/create")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<?> createOrder(@RequestBody List<OrderRequest> productList) {

		String username = UserSesion.getCurrentUsername();
		UserApp user = userService.findUserByNDoc(username);

		Order order = new Order();
		this.orderService.create(order);
		order.setUser(user);

		List<OrderProduct> orderProducts = orderService.convertOrderRequestToOrderProduct(productList, order);

		orderService.settingOrder(order, orderProducts);

		if (orderService.updateOrder(order) != null) {
			return new ResponseEntity<>(order, HttpStatus.CREATED);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new MessageResponse("Error: Compra por menos de $70.000"));
		}

	}

	@PutMapping("/update/{idOrder}")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<?> updateOrder(@RequestBody List<OrderRequest> productList, @PathVariable Long idOrder) {

		Order order = orderService.findOrderById(idOrder);
		double subtotalOld = order.getSubTotalOrderPrice();
		if(orderService.equalsUser(order) && orderService.isUpdateTime(order)) {
			
			List<OrderProduct> orderProducts =orderService.convertOrderRequestToOrderProduct(productList, order);
			
			order.setOrderProducts(orderProducts);			
			
			if(subtotalOld <= order.getSubTotalOrderPrice()) {
				orderService.updateOrder(order);
				return new ResponseEntity<>(order, HttpStatus.OK);
			}
			
			
		}	
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new MessageResponse("Error: Se ha encontrado una incosistencia en la nueva orden"));
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<?> deleteOrder(@PathVariable long id){
		Order order = orderService.findOrderById(id);
		if(orderService.equalsUser(order)) {
			 if (orderService.isDeleteTime(order)) {
				 orderService.delete(order);
				 productService.getAllProducts().forEach(i->{
					 System.out.println(i);
				 });;
				 
				 return ResponseEntity.ok(new MessageResponse("Orden eliminada con exito"));
			 }
			 else {
				 orderService.deleteOrderAfterTime(order);
				 return ResponseEntity.ok(new MessageResponse("Orden eliminada con exito, con 10% de penalizacion"));
			 }	 
		}
				
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new MessageResponse("Error: No tiene autorizacion para eliminar esta orden"));
	}
	
}
