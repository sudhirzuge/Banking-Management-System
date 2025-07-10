package com.bank.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.dtos.ApiResponse;
import com.bank.dtos.BankAccountReqDto;
import com.bank.dtos.BankAccountRespDto;
import com.bank.entities.Bank;
import com.bank.entities.BankAccount;
import com.bank.entities.Customer;
import com.bank.exceptions.ResourceNotFoundException;
import com.bank.repositories.BankAccountRepository;
import com.bank.repositories.BankManagerRepository;
import com.bank.repositories.BankRepository;
import com.bank.repositories.CustomerRepository;
import com.bank.services.BankAccountService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private BankManagerRepository bankManagerRepository;

	@Override
	public ApiResponse addNewBankAccount(BankAccountReqDto bankaccdto) {
		// TODO Auto-generated method stub
		
		Customer customer = customerRepository.findById(bankaccdto.getCustomerId()).orElseThrow(()->new ResourceNotFoundException("Can't find customer with id :"+bankaccdto.getCustomerId()));
		Bank bank = bankRepository.findById(bankaccdto.getBankId()).orElseThrow(()->new ResourceNotFoundException("Can't find customer with id :"+bankaccdto.getBankId()));
		
		BankAccount bankAccount = new BankAccount();
		bankAccount.setCustomer(customer);
		bankAccount.setBank(bank);
		bankAccount.setAccountType(bankaccdto.getAccountType());
		
		BankAccount createdBankAccount = bankAccountRepository.save(bankAccount);
		
		return new ApiResponse("Bank Account Created Successfully with ID : "+createdBankAccount.getId());
	}

	@Override
	public List<BankAccountRespDto> viewAllBankAccounts(Long managerId) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public BankAccountRespDto viewSpecificBankAccount(Long accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiResponse lockAccount(Long accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiResponse unlockAccount(Long accountId) {
		// TODO Auto-generated method stub
		return null;
	}

}
