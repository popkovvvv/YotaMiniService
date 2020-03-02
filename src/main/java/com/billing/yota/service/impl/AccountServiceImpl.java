package com.billing.yota.service.impl;

import com.billing.yota.dao.impl.AccountDaoImpl;
import com.billing.yota.exception.TransactionException;
import com.billing.yota.model.entity.Account;
import com.billing.yota.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountDaoImpl accountDaoImpl;

    private final TransactionServiceImpl transactionServiceImpl;

    @Autowired
    public AccountServiceImpl(AccountDaoImpl accountDaoImpl, TransactionServiceImpl transactionServiceImpl) {
        this.accountDaoImpl = accountDaoImpl;
        this.transactionServiceImpl = transactionServiceImpl;
    }

    @Transactional
    @Override
    public ResponseEntity<String> create( Account account) {
        try {
            accountDaoImpl.create(account);
            return new ResponseEntity<>("Create completed", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>("Create error", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Account findByNumber(long number) {
      return accountDaoImpl.findByNumber(number);
    }

    @Transactional
    @Override
    public void transaction(Long fromAccountNumber, Long toAccountNumber, double amount) throws TransactionException {
        changeBalance(toAccountNumber, amount);
        changeBalance(fromAccountNumber, -amount);
    }

    @Transactional
    @Override
    public void changeBalance(long number, double amount) throws TransactionException {
        Account accountInfo = findByNumber(number);
        if (accountInfo == null) {
            throw new TransactionException("Account not found " + number);
        }
        double newBalance = accountInfo.getBalance() + amount;
        if (newBalance < 0) {
            throw new TransactionException(
                    "The money in the account '" + number + "' is not enough (" + accountInfo.getBalance() + ")");
        }
        if (!accountInfo.isCanTransfer()) {
            throw new TransactionException("Account " + number + " has blocked");
        }
        // Update to DB
        accountDaoImpl.changeBalance(number, newBalance);
    }

    @Transactional
    @Override
    public ResponseEntity<String> updateAccountTransfer(long number, boolean isCan) {
        try {
            accountDaoImpl.update(number, isCan);
        } catch (Exception e) {
            return new ResponseEntity<>("Update failure", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<String> checkTransfer(long number) {
        boolean check =  accountDaoImpl.checkTransfer(number);
        if (check) {
            return new ResponseEntity<>("Account may transfer", HttpStatus.OK);
        }
        return new ResponseEntity<>("Account didnt may transfer", HttpStatus.NOT_ACCEPTABLE);
    }


}