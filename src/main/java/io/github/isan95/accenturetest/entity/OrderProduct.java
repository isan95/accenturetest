package io.github.isan95.accenturetest.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.github.isan95.accenture.exception.NotStockException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class OrderProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2553012190125268475L;

	@EmbeddedId
	@JsonIgnore
	private OrderProductPK pk;

	@Column(nullable = false)
	private Integer quantity;

	// default constructor

	public OrderProduct(Order order, Product product, Integer quantity) {
		pk = new OrderProductPK();
		pk.setOrder(order);
		pk.setProduct(product);
		
		if(quantity <= product.getStock()) {
			this.quantity = quantity;
			product.setStock(product.getStock()-this.quantity);
		}
		else {
			throw new NotStockException("La cantidad es superior a las existencias");
		}
	}

	@Transient
	public Product getProduct() {
		return this.pk.getProduct();
	}

	@Transient
	public Double getTotalPrice() {
		return getProduct().getPrice() * getQuantity();
	}

}
