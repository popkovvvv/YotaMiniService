package com.billing.yota.dao;

import com.billing.yota.model.entity.Account;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountDao {

    void create( Account account);
    void update(int number, boolean isCan);
    boolean checkTransfer(int number);
    Optional<Account> findByNumber(int number);
    void changeBalance(int number, BigDecimal balance);
}
