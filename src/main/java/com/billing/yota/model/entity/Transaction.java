package com.billing.yota.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private Integer origin;

    @Column(nullable = false)
    private Integer receiver;

    @Column(nullable = false)
    private BigDecimal amount;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount( BigDecimal amount ) {
        this.amount = amount;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin( Integer origin ) {
        this.origin = origin;
    }

    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver( Integer receiver ) {
        this.receiver = receiver;
    }

    public boolean isWasRefund() {
        return isWasRefund;
    }

    public void setWasRefund( boolean wasRefund ) {
        isWasRefund = wasRefund;
    }
}
