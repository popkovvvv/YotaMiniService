package com.billing.yota.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String fullName;

    private double balance;

    private boolean isCanTransfer;

    @Column(unique = true)
    private Long number;

    @JsonIgnore
    @OneToMany(mappedBy = "origin")
    private List<Transaction> transactions;

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName( String fullName ) {
        this.fullName = fullName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance( double balance ) {
        this.balance = balance;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber( Long number ) {
        this.number = number;
    }

    public boolean isCanTransfer() {
        return isCanTransfer;
    }

    public void setCanTransfer( boolean canTransfer ) {
        isCanTransfer = canTransfer;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions( List<Transaction> transactions ) {
        this.transactions = transactions;
    }
}
