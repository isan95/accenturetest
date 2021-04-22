package io.github.isan95.accenturetest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.isan95.accenturetest.entity.OrderProduct;
import io.github.isan95.accenturetest.repository.OrderProductRepository;

@Service
public class OrderProductServiceImpl implements OrderProductService{
	
	@Autowired
	OrderProductRepository orderProductRepository;
	
	@Override
	public OrderProduct create(OrderProduct orderProduct) {
		
		return orderProductRepository.save(orderProduct);
	}

}
