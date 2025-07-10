package com.bank.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.bank.entities.User;

public interface UserService {

	public User authenticate(String email, String password);
	
	public User registerUser(User user);
	
	public void uploadProfileImage(Long userId, MultipartFile profileImage) throws IOException;
}
