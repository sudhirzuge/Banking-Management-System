package com.bank.services;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;

public interface TransferService {

	public ResponseEntity<?> transferMoney(Long fromAccountId, Long toAccountId, BigDecimal amount, String Description, String Ifsc);
}
