package com.billing.yota.model;

import java.time.LocalDateTime;


public class Transfer {
    private Long id;
    private LocalDateTime transfer_at;
    private long user_id;
    private double amount;
    private String type;

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

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id( long user_id ) {
        this.user_id = user_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount( double amount ) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }

    @Override
    public boolean equals( Object o ) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transfer transfer = (Transfer) o;

        if (user_id != transfer.user_id) return false;
        if (Double.compare(transfer.amount, amount) != 0) return false;
        if (id != null ? !id.equals(transfer.id) : transfer.id != null) return false;
        if (transfer_at != null ? !transfer_at.equals(transfer.transfer_at) : transfer.transfer_at != null)
            return false;
        return type != null ? type.equals(transfer.type) : transfer.type == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (transfer_at != null ? transfer_at.hashCode() : 0);
        result = 31 * result + (int) (user_id ^ (user_id >>> 32));
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
