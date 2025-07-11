package com.bank.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.bank.dtos.BankAccountDto;
import com.bank.dtos.BankAccountRespDto;
import com.bank.dtos.BankDto;
import com.bank.dtos.BankRespDto;
import com.bank.dtos.CustomerProfileRespDto;
import com.bank.dtos.TransactionResponseDto;
import com.bank.dtos.UserDto;
import com.bank.entities.BankAccount;
import com.bank.entities.Customer;
import com.bank.entities.Gender;
import com.bank.entities.Transaction;
import com.bank.entities.TransactionType;
import com.bank.exceptions.ResourceNotFoundException;
import com.bank.repositories.BankAccountRepository;
import com.bank.repositories.CustomerRepository;
import com.bank.repositories.TransactionRepository;
import com.bank.services.CustomerService;

public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@Autowired
    private TransactionRepository transactionRepository;

	@Override
	public CustomerProfileRespDto getCustomerDetails(Long customerId) {
		// TODO Auto-generated method stub
		
		Customer customer = customerRepository.findById(customerId).orElseThrow(()->new ResourceNotFoundException("Can't Find Customer with id: "+customerId));
		String customerName = customer.getUser().getFname()+" "+customer.getUser().getLname();
		String email = customer.getUser().getEmail();
		Long userId=customer.getUser().getId();
		String PhoneNo = customer.getUser().getPhoneNo();
		Gender gender = customer.getUser().getGender();
		String Address = customer.getUser().getAddress();
		Long customerId1=customer.getId();//for testing
		
		//get account type for a customer's account
		//userId = ID from the users table
        //customerId1 = ID from the customers table
		
		// Even if IDs are same right now, they may diverge later if Customer and User are modified separately.

		return new CustomerProfileRespDto(customerName,email,gender,PhoneNo,Address,customerId1,userId);
	}

	@Override
	public List<TransactionResponseDto> getAllTransactions(Long accountId) {
		// TODO Auto-generated method stub
		  return transactionRepository.findAllByAccountId(accountId)
		            .stream()
		            .map(this::convertToDto) // Convert each entity to DTO
		            .collect(Collectors.toList());
	}
	
	 private TransactionResponseDto convertToDto(Transaction transaction) {
	        TransactionResponseDto dto = new TransactionResponseDto();
	        dto.setTransactionId(transaction.getId().toString());
	        dto.setBank(new BankDto(transaction.getAccount().getBank().getBankName()));
	        
	        dto.setDestinationBank(transaction.getTransactionType() == TransactionType.TRANSFER 
	                ? new BankDto(transaction.getTransfer().getToAccount().getBank().getBankName()):null);
	        
	        dto.setUser(new UserDto(transaction.getAccount().getCustomer().getUser().getFname()));
	        dto.setBankAccount(new BankAccountDto(transaction.getAccount().getId()));
	        dto.setType(transaction.getTransactionType().name());
	        dto.setAmount(transaction.getAmount().doubleValue());
	        dto.setDestinationBankAccount(
	                transaction.getTransactionType() == TransactionType.TRANSFER 
	                        ? new BankAccountDto(transaction.getTransfer().getToAccount().getId())
	                        : null
	        );
	        dto.setNarration(transaction.getDescription());
	        dto.setTransactionTime(transaction.getCreatedOn().atZone(java.time.ZoneOffset.UTC).toInstant().toEpochMilli());
	        return dto;
	    }
	

	@Override
	public List<TransactionResponseDto> getAllTransactionsForCustomer(Long customerId) {
		// TODO Auto-generated method stub
		
		//Fetch all bank accounts for a customer.
	    List<BankAccount> allAccounts = bankAccountRepository.findByCustomerId(customerId);

	  //Fetch all transactions for all those accounts.
	    return allAccounts.stream()
	            .flatMap(account -> transactionRepository.findAllByAccountId(account.getId()).stream()) //Combine all transactions into one flat list.
	            .map(this::convertToDto)       //Convert to DTOs and return.
	            .collect(Collectors.toList());
	}

	@Override
	public List<BankAccountRespDto> getAllSpecificAccounts(Long customerId) {
	    // Fetch all bank accounts for the given customer
	    List<BankAccount> allAccounts = bankAccountRepository.findByCustomerId(customerId);
	    
	    return allAccounts.stream()
	            .map(account -> {
	                // Convert Bank to BankRespDto
	                BankRespDto bankRespDto = new BankRespDto(
	                        account.getBank().getBankName(),
	                        account.getBank().getBankIfsc() // assuming ifscCode is part of the Bank entity
	                );

	                // Create BankAccountRespDto to avoid directly exposing bank entity
	                BankAccountRespDto respDto = new BankAccountRespDto();
	                respDto.setCustomerName(account.getCustomer().getUser().getFname() + " " + account.getCustomer().getUser().getLname());
	                respDto.setBankName(bankRespDto.getBankName());
	                respDto.setAccountId(account.getId());
	                respDto.setIfscCode(account.getBank().getBankIfsc());
	                respDto.setAccountType(account.getAccountType().name());
	                respDto.setStatus(account.getIsLocked() ? "Locked" : "Active");
	                respDto.setCustomerEmail(account.getCustomer().getUser().getEmail());
	                respDto.setBalance(account.getBalance());
	                respDto.setCreatedOn(account.getCreatedOn());

	                return respDto;
	            })
	            .collect(Collectors.toList());
	}


}
