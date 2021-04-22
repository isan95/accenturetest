package io.github.isan95.accenturetest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.github.isan95.accenturetest.entity.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long>{
	
}
