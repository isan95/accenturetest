package io.github.isan95.accenturetest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.github.isan95.accenturetest.entity.OrderProduct;
import io.github.isan95.accenturetest.entity.OrderProductPK;

@Repository
public interface OrderProductRepository extends CrudRepository<OrderProduct, OrderProductPK>{

}
