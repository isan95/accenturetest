package io.github.isan95.accenturetest.entity;


import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class OrderProduct implements Serializable{

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
		this.quantity = quantity;
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
