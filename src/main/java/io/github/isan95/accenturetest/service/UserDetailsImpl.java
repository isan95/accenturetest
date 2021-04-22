package io.github.isan95.accenturetest.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;


import io.github.isan95.accenturetest.entity.UserApp;

public class UserDetailsImpl implements UserDetails{
	
	private Long id;
	
	private String nDoc;
	
	private String address;
	
	@JsonIgnore
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	private static final long serialVersionUID = 1L;
	
	public UserDetailsImpl(Long id, String nDoc, String address, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.nDoc = nDoc;
		this.address = address;
		this.password = password;
		this.authorities = authorities;
	}
	
	public static UserDetailsImpl build(UserApp user) {
		
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());
		return new UserDetailsImpl(
				user.getId(),
				user.getUsername(),
				user.getAddress(),
				user.getPassword(),
				authorities);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return this.authorities;
	}

	@Override
	public String getPassword() {
		
		return this.password;
	}

	@Override
	public String getUsername() {
		
		return this.nDoc;
	}
	
	public String getNDoc() {
		
		return this.nDoc;
	}
	
	public String getAddress() {
		
		return this.address;
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl customer = (UserDetailsImpl) o;
		return Objects.equals(id, customer.id);
	}

}
