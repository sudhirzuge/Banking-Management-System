package com.bank.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bank.dtos.ApiResponse;
import com.bank.dtos.BankManagerRespDto;
import com.bank.dtos.BankRespDto;
import com.bank.entities.User;

public interface AdminService {

	List<BankManagerRespDto> viewAllBankManager();
	
	List<BankRespDto> viewAllBanks();
	
	List<User> getAllBankManagersFromUser();
	
	ApiResponse toggleManagerStatus(Long managerId);
	
	ResponseEntity<?> getBankData();
}
