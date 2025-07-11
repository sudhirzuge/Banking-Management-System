package com.bank.services.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bank.entities.BankAccount;
import com.bank.entities.Customer;
import com.bank.entities.Role;
import com.bank.entities.User;
import com.bank.exceptions.ResourceNotFoundException;
import com.bank.repositories.BankAccountRepository;
import com.bank.repositories.BankRepository;
import com.bank.repositories.CustomerRepository;
import com.bank.repositories.UserRepository;
import com.bank.services.UserService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private BankAccountRepository bankAccRepository;
	
	@Autowired
	private BankRepository bankRepo;
	
	@Override
	public User authenticate(String email, String password) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email).filter(u->u.getPassword().equals(password)).orElseThrow(()-> new ResourceNotFoundException("user not found with email: "+email));
	}

	@Override
	public User registerUser(User user) {
		// TODO Auto-generated method stub
		User persistentUser = userRepository.save(user);
		if(persistentUser.getRole()==Role.CUSTOMER) {
			Customer c = new Customer(user);
			customerRepository.save(c);
			
			BankAccount newBankAccount = new BankAccount();
			newBankAccount.setCustomer(c);
			newBankAccount.setAccountType(user.getAccountType());
			newBankAccount.setBank(bankRepo.findById(user.getBankId()).get());
			
			bankAccRepository.save(newBankAccount);
		}
		return persistentUser;
	}

	@Override
	public void uploadProfileImage(Long userId, MultipartFile profileImage) throws IOException {
		// TODO Auto-generated method stub
		
		User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("user not found with id :"+userId));
		byte[] imageBytes = profileImage.getBytes();
		user.setProfileImage(imageBytes);
		userRepository.save(user);

	}

}
