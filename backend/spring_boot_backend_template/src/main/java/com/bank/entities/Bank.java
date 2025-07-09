package com.bank.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Banks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Bank extends BaseEntity{

	@Column(nullable = false, name="bank_name")
	private String bankName;
	
	@Column(nullable = false)
	private String phone;
	
	private String address;
	
	@Column(nullable = false, unique = true, name ="bank_ifsc")
	private String bankIfsc;
	
	@Column(nullable = false, unique = true,name="bank_website" )
	private String bankWebsite;
	
    @Column(nullable = false, unique = true,name="bank_email")
    private String bankEmail;
    
    @Column(nullable=false,name="bank_country")
    private String bankCountry;
	
}
