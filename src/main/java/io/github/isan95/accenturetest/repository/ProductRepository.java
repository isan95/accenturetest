package io.github.isan95.accenturetest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.github.isan95.accenturetest.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{

}
