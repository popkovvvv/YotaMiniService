package com.billing.yota.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String fullName;

    private BigDecimal balance;

    private boolean isCanTransfer;

    @Column(unique = true, nullable = false)
    private Integer number;

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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance( BigDecimal balance ) {
        this.balance = balance;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber( Integer number ) {
        this.number = number;
    }

    public boolean isCanTransfer() {
        return isCanTransfer;
    }

    public void setCanTransfer( boolean canTransfer ) {
        isCanTransfer = canTransfer;
    }

}
