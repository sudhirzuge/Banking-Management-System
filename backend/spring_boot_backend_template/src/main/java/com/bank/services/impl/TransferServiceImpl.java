package com.bank.services.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bank.dtos.ApiResponse;
import com.bank.entities.BankAccount;
import com.bank.entities.Transaction;
import com.bank.entities.TransactionType;
import com.bank.entities.TransferDetail;
import com.bank.exceptions.InSufficientBalanceException;
import com.bank.exceptions.InvalidIfscException;
import com.bank.exceptions.ResourceNotFoundException;
import com.bank.exceptions.UserLockedException;
import com.bank.repositories.BankAccountRepository;
import com.bank.repositories.TransactionRepository;
import com.bank.repositories.TransferRepository;
import com.bank.services.TransferService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TransferServiceImpl implements TransferService {

	@Autowired
	private TransferRepository transferRepository;
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Override
	public ResponseEntity<?> transferMoney(Long fromAccountId, Long toAccountId, BigDecimal amount, String Description,
			String Ifsc) {
		// TODO Auto-generated method stub
		
		BankAccount fromAccount = bankAccountRepository.findById(fromAccountId).orElseThrow(()-> new ResourceNotFoundException("Sender Account not found"));
		boolean isLocked = fromAccount.getIsLocked();
		if(isLocked) throw new UserLockedException("user account is locked");
		
		BankAccount toAccount = bankAccountRepository.findById(toAccountId).orElseThrow(()-> new ResourceNotFoundException("receiver Account not found"));
		String originalReceiverIfsc = toAccount.getBank().getBankIfsc();
		
		if(!originalReceiverIfsc.equals(Ifsc)) throw new InvalidIfscException("ifsc code is invalid please retry");
		
		BigDecimal existingBalanceFrom = fromAccount.getBalance();
		BigDecimal existingBalanceTo = toAccount.getBalance();
		
		if(existingBalanceFrom.compareTo(amount)<0) throw new InSufficientBalanceException("money transfer failed insufficient balance");
		
		//deduct the amount from sender's account
		fromAccount.setBalance(existingBalanceFrom.subtract(amount));
		
		// send the amount to receiver account
		toAccount.setBalance(existingBalanceTo.add(amount));
		
		BankAccount persistFromAccount = bankAccountRepository.save(fromAccount);
		BankAccount persistToAccount = bankAccountRepository.save(toAccount);
		
		Transaction creditTrx = new Transaction();
		creditTrx.setAccount(persistToAccount);
		creditTrx.setAmount(amount);
        creditTrx.setBalanceBeforeTrx(existingBalanceTo);
        creditTrx.setBalanceAfterTrx(persistToAccount.getBalance());
        creditTrx.setDescription("CREDIT");
        creditTrx.setTransactionType(TransactionType.DEPOSIT);
 		Transaction persistenCreditTrx=transactionRepository.save(creditTrx);
				
		Transaction transferMoney = new Transaction();
		transferMoney.setAccount(persistFromAccount);
		transferMoney.setAmount(amount);
		transferMoney.setBalanceBeforeTrx(existingBalanceFrom);
		transferMoney.setBalanceAfterTrx(persistFromAccount.getBalance());
		transferMoney.setDescription(Description);
		transferMoney.setTransactionType(TransactionType.TRANSFER);		
		
		TransferDetail transferDetail = new TransferDetail();
        transferDetail.setAmount(amount);
        transferDetail.setFromAccount(fromAccount);
        transferDetail.setToAccount(toAccount);
        
        transferMoney.setTransfer(transferDetail);
        
		Transaction persistentTransferTrx=transactionRepository.save(transferMoney);
		TransferDetail persistentTransfer = transferRepository.save(transferDetail);
		
		return ResponseEntity.ok(new ApiResponse("Money transfer succesfull. TransferDetails id: "+persistentTransfer.getId()));
	}

}











