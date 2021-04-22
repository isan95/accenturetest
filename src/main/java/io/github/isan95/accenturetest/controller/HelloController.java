package io.github.isan95.accenturetest.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins="*", maxAge= 3600)
@RestController
public class HelloController {
	
	@GetMapping("/")
	public String hello() {
		return "Hola";
	}
}
