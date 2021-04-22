package io.github.isan95.accenturetest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.isan95.accenture.exception.ResourceNotFoundException;
import io.github.isan95.accenturetest.entity.UserApp;
import io.github.isan95.accenturetest.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;

	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String nDoc) throws UsernameNotFoundException {
		UserApp user = userRepository.findByUsername(nDoc)
				.orElseThrow(() -> new UsernameNotFoundException("Cliente no encontrado con documento: "+ nDoc));
		
		return UserDetailsImpl.build(user);
	}
	
	public UserApp findUserByNDoc(String nDoc) throws UsernameNotFoundException {
		
		UserApp user = userRepository.findByUsername(nDoc)
				.orElseThrow(()-> new UsernameNotFoundException("Cliento no encontrado con documento: "+ nDoc));
		return user;
	}
	
	public UserApp findById(Long id) {
		
		return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException ("Usuario no encontrado")); 
	}
	
}
