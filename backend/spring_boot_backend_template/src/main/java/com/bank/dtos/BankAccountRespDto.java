package com.bank.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BankAccountRespDto {

	private String customerName;
	private String bankName;
	private Long accountId;
	private String ifscCode;
	private String accountType;
	private String status;
	private String customerEmail;
	private BigDecimal balance;
	private LocalDateTime createdOn;
	
	public BankAccountRespDto(String customerName, String bankName, Long accountId, String ifscCode, String accountType,
			String status, String customerEmail, LocalDateTime createdOn) {
		super();
		this.customerName = customerName;
		this.bankName = bankName;
		this.accountId = accountId;
		this.ifscCode = ifscCode;
		this.accountType = accountType;
		this.status = status;
		this.customerEmail = customerEmail;
		this.createdOn = createdOn;
	}
	
	public BankAccountRespDto(String customerName, String bankName, Long accountId, String ifscCode, String accountType,
			String status, String customerEmail, BigDecimal balance, LocalDateTime createdOn) {
		super();
		this.customerName = customerName;
		this.bankName = bankName;
		this.accountId = accountId;
		this.ifscCode = ifscCode;
		this.accountType = accountType;
		this.status = status;
		this.customerEmail = customerEmail;
		this.balance = balance;
		this.createdOn = createdOn;
	}
	
	
}
