package com.bank.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {

	@NotBlank(message = "Email must not be null and blank.!")
	@Email(message = "invalid email format")
	private String email;
	
//	@Pattern(regexp = "((?=.\\d)(?=.[a-z])(?=.[#@$]).{5,20})", message = "Invalid Password format!!!!")
	private String password;
}
