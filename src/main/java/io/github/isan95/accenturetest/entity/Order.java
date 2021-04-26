package io.github.isan95.accenturetest.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "orders")
public class Order implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3494422565526433810L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime dateCreated = LocalDateTime.now();

	@ManyToOne
	@JoinColumn(name="status_id")
	private  OrderStatus status;

	@JsonManagedReference
	@OneToMany(mappedBy = "pk.order")
	//@Valid
	private List<OrderProduct> orderProducts = new ArrayList<>();
	
	private final double ivaPercent = 19.0/100.0;
	
	private double iva;
	
	private double shipping = 8000.0;
	
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserApp user;
	
	
	@Transient
	public Double getSubTotalOrderPrice() {
		 double subtotal = 0D;
		List<OrderProduct> orderProducts = getOrderProducts();
		for (OrderProduct op : orderProducts) {
			subtotal += op.getTotalPrice();
		}
		this.setSubtotal(subtotal);
		return subtotal;
	}
	
	private double subtotal;
	
	private double total;

	@Transient
	public int getNumberOfProducts() {
		return this.orderProducts.size();
	}
	
}