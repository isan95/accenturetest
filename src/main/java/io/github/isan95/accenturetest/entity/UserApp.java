package io.github.isan95.accenturetest.entity;

import java.io.Serializable;
import java.util.HashSet;
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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.github.isan95.accenturetest.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = "username")
})
public class UserApp implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1014794390732600538L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	
	private String address;
	
	@JsonIgnore
	private String password;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	public UserApp(String username, String address) {
		this.username = username;
		this.password = address;
	}
	public UserApp(String username, String address, String password) {
		this.username = username;
		this.address = address;
		this.password = password;
	}
	public UserApp(String username, String address, String password, Role role) {
		this.username = username;
		this.address = address;
		this.password = password;
		this.roles.add(role);
	}
}
