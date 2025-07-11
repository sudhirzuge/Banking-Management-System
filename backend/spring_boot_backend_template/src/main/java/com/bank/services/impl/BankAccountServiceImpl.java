package com.bank.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.dtos.ApiResponse;
import com.bank.dtos.BankAccountReqDto;
import com.bank.dtos.BankAccountRespDto;
import com.bank.entities.Bank;
import com.bank.entities.BankAccount;
import com.bank.entities.BankManager;
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
		
		BankManager bankManager = bankManagerRepository.findById(managerId).orElseThrow(()-> new ResourceNotFoundException("can't find manager with id: "+managerId));
		
		Bank associatedBank = bankManager.getBank();
		List<BankAccount> allBankAccount = bankAccountRepository.findByBankId(associatedBank.getId());
		
		return allBankAccount.stream().map(acc -> new BankAccountRespDto(
				 acc.getCustomer().getUser().getFname() + " " + acc.getCustomer().getUser().getLname(), // Customer Name
		            acc.getBank().getBankName(), 
		            acc.getId(), 
		            acc.getBank().getBankIfsc(), 
		            acc.getAccountType().name(), 
		            acc.getIsLocked() ? "LOCKED" : "ACTIVE", 
		            acc.getCustomer().getUser().getEmail(),
		            acc.getCreatedOn()
		            )
				).toList();
	}

	@Override
	public BankAccountRespDto viewSpecificBankAccount(Long accountId) {
		// TODO Auto-generated method stub
		
		BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(()-> new ResourceNotFoundException("no bank account with account id : "+accountId));
		
		return new BankAccountRespDto(
			    bankAccount.getCustomer().getUser().getFname() + " " + bankAccount.getCustomer().getUser().getLname(),
			    bankAccount.getBank().getBankName(),
			    bankAccount.getId(),
			    bankAccount.getBank().getBankIfsc(),
			    bankAccount.getAccountType().name(),
			    bankAccount.getIsLocked() ? "LOCKED" : "ACTIVE",
			    bankAccount.getCustomer().getUser().getEmail(),
			    bankAccount.getBalance(),
			    bankAccount.getCreatedOn()
			);
	}

	@Override
	public ApiResponse lockAccount(Long accountId) {
		// TODO Auto-generated method stub
		
		BankAccount bankAccount  = bankAccountRepository.findById(accountId).orElseThrow(()-> new ResourceNotFoundException("No bank account with id: "+accountId));
		bankAccount.setIsLocked(true);
		bankAccountRepository.save(bankAccount);
		
		return new ApiResponse("Account Locked ");
	}

	@Override
	public ApiResponse unlockAccount(Long accountId) {
		// TODO Auto-generated method stub
		
		BankAccount bankAccount  = bankAccountRepository.findById(accountId).orElseThrow(()-> new ResourceNotFoundException("No bank account with id: "+accountId));
		bankAccount.setIsLocked(false);
		bankAccountRepository.save(bankAccount);
		
		return new ApiResponse("Account Unlocked ");
	}

}
