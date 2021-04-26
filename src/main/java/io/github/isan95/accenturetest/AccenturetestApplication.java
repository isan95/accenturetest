package io.github.isan95.accenturetest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.github.isan95.accenturetest.entity.ERole;
import io.github.isan95.accenturetest.entity.EStatus;
import io.github.isan95.accenturetest.entity.OrderStatus;
import io.github.isan95.accenturetest.entity.Product;
import io.github.isan95.accenturetest.entity.Role;
import io.github.isan95.accenturetest.entity.UserApp;
import io.github.isan95.accenturetest.repository.UserRepository;
import io.github.isan95.accenturetest.repository.OrderStatusRepository;
import io.github.isan95.accenturetest.repository.ProductRepository;

@SpringBootApplication
public class AccenturetestApplication {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private OrderStatusRepository orderStatusRepository; 

	@PostConstruct
	private void initData() {
		
		Role rol = new Role(ERole.ROLE_CUSTOMER);
		
		List<UserApp> users = Stream.of(
				new UserApp("12345","carrera 11#14-08" ,encoder.encode("1234567"), rol),
				new UserApp("1234567","otra direccion" ,encoder.encode("1234567"), rol)
				).collect(Collectors.toList());
		
		repository.saveAll(users);
		
		List<Product> products = Stream.of(
				new Product("Product 1", 50, 2000.0),
				new Product("Product 2", 10, 3000.0),
				new Product("Product 3", 80, 2500.0)
				).collect(Collectors.toList());
		
		productRepository.saveAll(products);
		
		List<OrderStatus> status = Stream.of(
				new OrderStatus(EStatus.STATUS_PENDING),
				new OrderStatus(EStatus.STATUS_CANCELED),
				new OrderStatus(EStatus.STATUS_FINISHED)
				).collect(Collectors.toList());
		
		orderStatusRepository.saveAll(status);
	}

	public static void main(String[] args) {
		SpringApplication.run(AccenturetestApplication.class, args);
	}

}
