package com.billing.yota.model.entity;

import com.billing.yota.model.entity.Account;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private LocalDateTime transfer_at;

    @Column(nullable = false)
    private Long origin;

    @Column(nullable = false)
    private Long receiver;

    @Column(nullable = false)
    private Double amount;

    private boolean isWasRefund;


    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public LocalDateTime getTransfer_at() {
        return transfer_at;
    }

    public void setTransfer_at( LocalDateTime transfer_at ) {
        this.transfer_at = transfer_at;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount( Double amount ) {
        this.amount = amount;
    }

    public Long getOrigin() {
        return origin;
    }

    public void setOrigin( Long origin ) {
        this.origin = origin;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver( Long receiver ) {
        this.receiver = receiver;
    }

    public boolean isWasRefund() {
        return isWasRefund;
    }

    public void setWasRefund( boolean wasRefund ) {
        isWasRefund = wasRefund;
    }
}
