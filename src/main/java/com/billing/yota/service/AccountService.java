package com.billing.yota.service;

import com.billing.yota.dao.AccountDAO;
import com.billing.yota.exception.TransactionException;
import com.billing.yota.model.entity.Account;
import com.billing.yota.model.entity.Transaction;
import com.billing.yota.model.pojo.TransferPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    TransactionService transactionService;

    public void create(Account account) {
        accountDAO.create(account);
    }

    public Account findByNumber(long number) {
      return accountDAO.findByNumber(number);
    }

    public void transaction(long from, long to, double amount) throws TransactionException {
        accountDAO.transaction(from, to, amount);
    }

    public void updateAccountTransfer(long number, boolean isCan) {
        accountDAO.update(number, isCan);
    }

    public boolean checkTransaction(long number) {
        return accountDAO.checkTransaction(number);
    }

    public void rollbackTransaction(long number) throws TransactionException {
       Transaction transaction = transactionService.getLastTransactionByNumber(number);
       transaction(transaction.getReceiver(), transaction.getOrigin(), transaction.getAmount());
    }


}
