package com.billing.yota.model.pojo;

import java.math.BigDecimal;

public class TransferPOJO {
    private Integer fromAccountNumber;
    private Integer toAccountNumber;
    private BigDecimal amount;

    public Integer getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber( Integer fromAccountNumber ) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public Integer getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber( Integer toAccountNumber ) {
        this.toAccountNumber = toAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount( BigDecimal amount ) {
        this.amount = amount;
    }
}
