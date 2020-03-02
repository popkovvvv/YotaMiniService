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

    @Column(unique = true, nullable = false)
    private String fullName;

    private double balance;

    private boolean isCanTransfer;

    @Column(unique = true, nullable = false)
    private Long number;

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

}
