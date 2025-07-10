package com.bank.dtos;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;

public class UserImageDto {
	
	@NotNull(message = "User ID must not be null.!")
	private Integer userId;
	
	@NotNull(message = "profile image must not be null.!")
	private MultipartFile profileImage;
	
	//getter
	public Integer getUserId() {
		return userId;
	}
	
	//setter
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	//getter
	public MultipartFile getProfileImage() {
		return profileImage;
	}
	
	//setter
	public void setProfileImage(MultipartFile profileImage) {
		this.profileImage = profileImage;
	}

}
