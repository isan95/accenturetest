package io.github.isan95.accenturetest.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.isan95.accenturetest.entity.ERole;
import io.github.isan95.accenturetest.entity.Role;
import io.github.isan95.accenturetest.entity.UserApp;
import io.github.isan95.accenturetest.payload.request.LoginRequest;
import io.github.isan95.accenturetest.payload.request.RegisterRequest;
import io.github.isan95.accenturetest.payload.response.JwtResponse;
import io.github.isan95.accenturetest.payload.response.MessageResponse;
import io.github.isan95.accenturetest.repository.RoleRepository;
import io.github.isan95.accenturetest.repository.UserRepository;
import io.github.isan95.accenturetest.service.UserDetailsImpl;
import io.github.isan95.accenturetest.util.JwtUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		System.out.println(loginRequest.getPassword());
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getNDoc(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtil.generateJwtToken(authentication);
		
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getNDoc(), userDetails.getAddress()));
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getNDoc())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Documento ya existe"));
		}

		// Create new user's account
		UserApp user = new UserApp(signUpRequest.getNDoc(),signUpRequest.getAddress() ,encoder.encode(signUpRequest.getPassword()));

		Set<Role> roles = new HashSet<>();

		Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
				.orElseThrow(() -> new RuntimeException("Error: Rol no fue encontrado."));
		roles.add(userRole);

		user.setRoles(roles);
		userRepository.save(user);
		System.out.println("Usuario reciente: "+user);
		List<UserApp> users = (List<UserApp>) userRepository.findAll();
		users.forEach(i->{
			System.out.println(i);
		});
		return ResponseEntity.ok(new MessageResponse("Usuario registrado con exito"));
	}

}
