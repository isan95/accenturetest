package io.github.isan95.accenturetest.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import io.github.isan95.accenturetest.entity.UserApp;

public interface UserDetailsService {
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	Optional<UserApp> findUserByUsername  (String username);
}
