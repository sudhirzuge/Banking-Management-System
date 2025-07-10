package com.bank.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransferMoneyDto {
	
	private Long fromAccountNo;
	
	private Long toAccountNo;
	
	private String Ifsc;
	
	private BigDecimal Amount;
	
	private String Description;
	
}
