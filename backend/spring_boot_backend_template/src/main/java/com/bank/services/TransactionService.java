package com.bank.services;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;

public interface TransactionService {

	ResponseEntity<?> depositMoney(Long accountNo, BigDecimal amount);
	
	ResponseEntity<?> withdrawMoney(Long accountNo, BigDecimal amount);
}
