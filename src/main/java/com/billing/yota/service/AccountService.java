package com.billing.yota.service;

import com.billing.yota.dao.AccountDAO;
import com.billing.yota.exception.TransactionException;
import com.billing.yota.model.entity.Account;
import com.billing.yota.model.entity.Transaction;
import com.billing.yota.model.pojo.TransferPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AccountService {

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    TransactionService transactionService;

    @Transactional
    public void create(Account account) {
        accountDAO.create(account);
    }

    @Transactional
    public Account findByNumber(long number) {
      return accountDAO.findByNumber(number);
    }

    @Transactional(rollbackOn = TransactionException.class)
    public void transaction(Long fromAccountNumber, Long toAccountNumber, double amount) throws TransactionException {
        changeBalance(toAccountNumber, amount);
        changeBalance(fromAccountNumber, -amount);
    }

    //TODO: перенести в бизнес логику
    @Transactional
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
        accountDAO.changeBalance(number, newBalance);
    }

    @Transactional
    public void updateAccountTransfer(long number, boolean isCan) {
        accountDAO.update(number, isCan);
    }

    @Transactional
    public boolean checkTransfer(long number) {
        return accountDAO.checkTransfer(number);
    }


}
