package com.bank.services.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.dtos.ApiResponse;
import com.bank.dtos.StatementReqDto;
import com.bank.entities.BankAccount;
import com.bank.entities.Statement;
import com.bank.entities.Transaction;
import com.bank.exceptions.ResourceNotFoundException;
import com.bank.repositories.BankAccountRepository;
import com.bank.repositories.StatementRepository;
import com.bank.repositories.TransactionRepository;
import com.bank.services.StatementService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class StatementServiceImpl implements StatementService {

	@Autowired
	private StatementRepository statementRepository;
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;

	
	@Override
	public ApiResponse saveStatementRecord(StatementReqDto statementDto) {
		// TODO Auto-generated method stub
		
		BankAccount bankAccount = bankAccountRepository.findById(statementDto.getAccountId())
				.orElseThrow(() -> new ResourceNotFoundException("Can't find Bank Account with Id: " + statementDto.getAccountId()));
		
		LocalDateTime startOfStartDate = statementDto.getStartDate().atStartOfDay();
		LocalDateTime endOfEndDate = statementDto.getEndDate().atTime(23, 59, 59);
		
		//fetch starting balance on or before start date
		Optional<Transaction> lastTransactionBeforeStartDate = transactionRepository.findLastTransactionBeforeOrOn(bankAccount, startOfStartDate);
		
		//fetch ending balance on or before end date
		Optional<Transaction> lastTransactionBeforeEndDate = transactionRepository.findLastTransactionBeforeOrOn(bankAccount, endOfEndDate);
		
		//determine the starting balance
	    BigDecimal startingBalance = lastTransactionBeforeStartDate
	            .map(Transaction::getBalanceAfterTrx)
	            .orElse(BigDecimal.ZERO);

	 
	    BigDecimal endingBalance = lastTransactionBeforeEndDate
	            .map(Transaction::getBalanceAfterTrx)
	            .orElse(startingBalance); 
	    
	    Statement statement = new Statement();
	    statement.setStartingBalance(startingBalance);
	    statement.setEndingBalance(endingBalance);
	    statement.setAccount(bankAccount);
	    statement.setStartDate(statementDto.getStartDate());
	    statement.setEndDate(statementDto.getEndDate());

	    // Save the statement record
	    Statement savedStatement = statementRepository.save(statement);

	    return new ApiResponse("Statement logged successfully with ID: " + savedStatement.getId());
	}

}
