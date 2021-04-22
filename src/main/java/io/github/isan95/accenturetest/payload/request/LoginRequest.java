package io.github.isan95.accenturetest.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	
	@NotBlank
	private String nDoc;

	@NotBlank
	private String password;

	
}
