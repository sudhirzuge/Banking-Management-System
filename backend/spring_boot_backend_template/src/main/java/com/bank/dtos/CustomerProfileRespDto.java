package com.bank.dtos;

import com.bank.entities.Gender;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerProfileRespDto {

	private String name;
	private String email;
	private Gender gender;
	private String contactno;
	private String address;
	private Long customerId;
	private Long userId;
	
	public CustomerProfileRespDto(String name, String email, Gender gender, String contactno, String address,
			Long customerId, Long userId) {
		super();
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.contactno = contactno;
		this.address = address;
		this.customerId = customerId;
		this.userId = userId;
	}
	
	
	
}
