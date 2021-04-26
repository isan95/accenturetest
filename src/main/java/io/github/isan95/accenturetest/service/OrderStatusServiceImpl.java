package io.github.isan95.accenturetest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.isan95.accenture.exception.ResourceNotFoundException;
import io.github.isan95.accenturetest.entity.OrderStatus;
import io.github.isan95.accenturetest.repository.OrderStatusRepository;

@Service
public class OrderStatusServiceImpl implements OrderStatusService{
	
	@Autowired
	private OrderStatusRepository orderStatusRepository;
	
	@Override
	public OrderStatus findById(Integer id) {
		
		return orderStatusRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Estado no ha sido encontrado"));
				
	}

}
