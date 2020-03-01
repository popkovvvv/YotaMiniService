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

    private Long origin;

    private Long receiver;

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
}
