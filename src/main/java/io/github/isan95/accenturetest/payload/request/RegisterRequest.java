package io.github.isan95.accenturetest.payload.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

	@NotBlank
	@Size(min = 3, max = 20)
	private String nDoc;

	@NotBlank
	@Size(min = 3, max = 100)
	private String password;
	
	@NotBlank
	@Size(min = 3, max = 100)
	private String address;

	
}
