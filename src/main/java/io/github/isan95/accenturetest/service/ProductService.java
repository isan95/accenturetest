package io.github.isan95.accenturetest.service;

import java.util.List;

import io.github.isan95.accenturetest.entity.Product;

public interface ProductService {

	Iterable<Product> getAllProducts();

	Product getProduct(long id);

	Product save(Product product);
	
	void saveAll(List<Product> products);
}
