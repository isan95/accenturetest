package io.github.isan95.accenturetest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.isan95.accenture.exception.ResourceNotFoundException;
import io.github.isan95.accenturetest.entity.Product;
import io.github.isan95.accenturetest.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productRepository;
		
	@Override
	public Iterable<Product> getAllProducts() {
		
		return productRepository.findAll();
	}

	@Override
	public Product getProduct(long id) {
		
		return productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Producto no encontrado"));
	}

	@Override
	public Product save(Product product) {
		return productRepository.save(product);
	}

	@Override
	public void saveAll(List<Product> products) {
		
		productRepository.saveAll(products);
	}

}
