package io.github.isan95.accenturetest.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="status")
public class OrderStatus implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6634147637481927315L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private EStatus status;
	
	public OrderStatus(EStatus status) {
		
		this.status = status;
	}
}
