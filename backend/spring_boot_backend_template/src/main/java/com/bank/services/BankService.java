package com.bank.services;

import java.util.List;

import com.bank.dtos.AllCustomersRespDto;
import com.bank.dtos.ApiResponse;
import com.bank.dtos.BankReqDto;
import com.bank.dtos.TransactionResponseDto;

public interface BankService {
	
	ApiResponse addNewBank(BankReqDto bankDto);
	
	List<AllCustomersRespDto> viewAllBankCustomers(Long bankManagerId);

	ApiResponse makeInActive(Long userId);
	
	ApiResponse makeActive(Long userId);
	
	List<TransactionResponseDto> getAllTransactionsForBank(Long managerId);
}
