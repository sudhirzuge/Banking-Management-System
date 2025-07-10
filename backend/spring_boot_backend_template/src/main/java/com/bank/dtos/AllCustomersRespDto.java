package com.bank.dtos;

import java.util.Objects;

import com.bank.entities.Gender;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AllCustomersRespDto {
	private String customerName;
	private String bankName;
	private String customerEmail;
	private Gender gender;
	private String customerContact;
	private String customerAddress;
	private Long accountId;
	private boolean customerStatus;
	private Long customerId;
	private Long userId;
	
	public AllCustomersRespDto(String customerName, String bankName, String customerEmail, Gender gender,
			String customerContact, String customerAddress, Long accountId, boolean customerStatus, Long customerId,
			Long userId) {
		super();
		this.customerName = customerName;
		this.bankName = bankName;
		this.customerEmail = customerEmail;
		this.gender = gender;
		this.customerContact = customerContact;
		this.customerAddress = customerAddress;
		this.accountId = accountId;
		this.customerStatus = customerStatus;
		this.customerId = customerId;
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerEmail);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AllCustomersRespDto other = (AllCustomersRespDto) obj;
		return Objects.equals(customerEmail, other.customerEmail);
	}
	
	

}
