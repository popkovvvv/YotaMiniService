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

    private LocalDateTime transfer_at;

    @ManyToOne(optional = false)
    private Account origin;

    @ManyToOne(optional = false)
    private Account receiver;

    private Double amount;

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

    public Account getOrigin() {
        return origin;
    }

    public void setOrigin( Account origin ) {
        this.origin = origin;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver( Account receiver ) {
        this.receiver = receiver;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount( Double amount ) {
        this.amount = amount;
    }
}
