package com.billing.yota.service;

import com.billing.yota.exception.TransactionException;
import com.billing.yota.model.entity.Account;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface AccountService {

    ResponseEntity<String> create( Account account);
    Account findByNumber(int number);
    void transaction(int fromAccountNumber, int toAccountNumber, BigDecimal amount)  throws TransactionException;
    void changeBalance(int number, BigDecimal amount)  throws TransactionException;
    ResponseEntity<String> updateAccountTransfer(int number, boolean isCan);
    ResponseEntity<String> checkTransfer(int number);
}
