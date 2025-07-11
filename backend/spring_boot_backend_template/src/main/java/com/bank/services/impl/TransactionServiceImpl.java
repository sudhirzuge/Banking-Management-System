package com.bank.services.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bank.dtos.ApiResponse;
import com.bank.entities.BankAccount;
import com.bank.entities.Transaction;
import com.bank.entities.TransactionType;
import com.bank.exceptions.InSufficientBalanceException;
import com.bank.exceptions.ResourceNotFoundException;
import com.bank.exceptions.UserLockedException;
import com.bank.repositories.BankAccountRepository;
import com.bank.repositories.TransactionRepository;
import com.bank.services.TransactionService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private  TransactionRepository transactionRepository;
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	
	@Override
	public ResponseEntity<?> depositMoney(Long accountNo, BigDecimal amount) {
		// TODO Auto-generated method stub
		BankAccount bankAc = bankAccountRepository.findById(accountNo).orElseThrow(()->new ResourceNotFoundException("Invalid account no : "+accountNo));
		boolean isLocked = bankAc.getIsLocked();
		if(isLocked) throw new UserLockedException("user account is locked");
		
		BigDecimal existingBalance = bankAc.getBalance();
		bankAc.setBalance(existingBalance.add(amount));
		
		BankAccount persistentBankAccount = bankAccountRepository.save(bankAc);
		
		Transaction trx = new Transaction();
		trx.setAccount(bankAc);
		trx.setBalanceBeforeTrx(existingBalance);
		trx.setBalanceAfterTrx(persistentBankAccount.getBalance());
		trx.setAmount(amount);
		trx.setDescription("DEPOSIT");
		Transaction persistentTrx = transactionRepository.save(trx);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Money Deposited Transaction Id :"+persistentTrx.getId()));
	}

	@Override
	public ResponseEntity<?> withdrawMoney(Long accountNo, BigDecimal amount) {
		// TODO Auto-generated method stub
		
		BankAccount bankAc = bankAccountRepository.findById(accountNo).orElseThrow(()-> new ResourceNotFoundException("Invalid account no : "+accountNo));
		boolean isLocked = bankAc.getIsLocked();
		if(isLocked) throw new UserLockedException("user account is locked");
		
		BigDecimal existingBalance = bankAc.getBalance();
		if(existingBalance.compareTo(amount)<0) throw new InSufficientBalanceException("InSufficient Balance");
		
		bankAc.setBalance(existingBalance.subtract(amount));
		BankAccount persistentBankAc = bankAccountRepository.save(bankAc);
		
		Transaction trx = new Transaction();
		trx.setAccount(bankAc);
		
		trx.setAmount(persistentBankAc.getBalance());
		trx.setBalanceBeforeTrx(existingBalance);
		trx.setBalanceAfterTrx(persistentBankAc.getBalance());
		trx.setDescription("WITHDRAW");
		trx.setTransactionType(TransactionType.WITHDRAWAL);
		Transaction persistentTrx = transactionRepository.save(trx);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Money withdrawl successfull. Remaininng Balanace: "+persistentTrx.getAmount()+"Transaction Id: "+persistentTrx.getId()));
	}

}
