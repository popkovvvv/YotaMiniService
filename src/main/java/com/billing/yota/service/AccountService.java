package com.billing.yota.service;

import com.billing.yota.exception.TransactionException;
import com.billing.yota.model.entity.Account;
import org.springframework.http.ResponseEntity;

public interface AccountService {

    ResponseEntity<String> create( Account account);
    Account findByNumber(long number);
    void transaction(Long fromAccountNumber, Long toAccountNumber, double amount)  throws TransactionException;
    void changeBalance(long number, double amount)  throws TransactionException;
    ResponseEntity<String> updateAccountTransfer(long number, boolean isCan);
    ResponseEntity<String> checkTransfer(long number);
}
