package com.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bank.entities.Gender;
import com.bank.entities.Role;
import com.bank.entities.User;
import com.bank.repositories.UserRepository;

@Configuration
public class AdminInitializer {

	@Autowired
	private UserRepository userRepository;
	
	@Bean
	public ApplicationRunner createFirstAdmin() {
		return args -> {
			if(userRepository.findByRole(Role.ADMIN).isEmpty()) {
				User admin = new User();
				admin.setEmail("admin@gmail.com");
				admin.setPassword(new BCryptPasswordEncoder().encode("Admin@123"));
				admin.setRole(Role.ADMIN);
				admin.setAddress("BlueRidge, Hinjewadi phase 1, Pune");
				admin.setFname("Sudhir");
				admin.setLname("Zuge");
				admin.setGender(Gender.MALE);
				admin.setPhoneNo("1234567890");
				userRepository.save(admin);
				System.out.println("first admin created successfully..!");
			}
		};
	}
}
