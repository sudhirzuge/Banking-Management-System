package com.bank.dtos;

public class UserDto {

	private String name;
	
	//counstructor
	public UserDto(String name) {
		this.name = name;
	}
	
	//getter
	public String getName() {
		return name;
	}
	
	//setter
	public void setName(String name) {
		this.name = name;
	}
	
}
