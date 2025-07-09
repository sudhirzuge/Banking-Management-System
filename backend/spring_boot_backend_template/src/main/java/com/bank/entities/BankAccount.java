package com.bank.entities;

import java.math.BigDecimal;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "BankAccounts")
@AttributeOverride(name = "id", column = @Column(name = "account_no"))  
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BankAccount extends BaseEntity {  

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "account_type")
    private AccountType accountType;

    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "is_locked")
    private Boolean isLocked = false;
}