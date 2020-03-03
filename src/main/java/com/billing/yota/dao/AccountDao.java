package com.billing.yota.dao;

import com.billing.yota.model.entity.Account;

public interface AccountDao {

    void create( Account account);
    void update(long number, boolean isCan);
    boolean checkTransfer(long number);
    Account findByNumber(long number);
    void changeBalance(long number, double balance);
}
