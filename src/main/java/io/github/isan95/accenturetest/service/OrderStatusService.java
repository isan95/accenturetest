package io.github.isan95.accenturetest.service;

import java.util.Optional;

import io.github.isan95.accenturetest.entity.OrderStatus;

public interface OrderStatusService {
	
	OrderStatus findById(Integer id);
}
