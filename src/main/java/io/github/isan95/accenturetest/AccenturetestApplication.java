package io.github.isan95.accenturetest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import io.github.isan95.accenturetest.entity.ERole;
import io.github.isan95.accenturetest.entity.Role;
import io.github.isan95.accenturetest.entity.UserApp;
import io.github.isan95.accenturetest.repository.UserRepository;
import io.github.isan95.accenturetest.repository.RoleRepository;

@SpringBootApplication
public class AccenturetestApplication {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private RoleRepository roleRepository;

	@PostConstruct
	private void initData() {
		
		Role rol = new Role(ERole.ROLE_CUSTOMER);
		
		List<UserApp> users = Stream.of(
				new UserApp("12345","carrera 11#14-08" ,encoder.encode("1234567"), rol),
				new UserApp("1234567","otra direccion" ,encoder.encode("1234567"), rol)
				).collect(Collectors.toList());
		
		repository.saveAll(users);


	}

	public static void main(String[] args) {
		SpringApplication.run(AccenturetestApplication.class, args);
	}

}
