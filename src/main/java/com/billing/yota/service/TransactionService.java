package com.billing.yota.service;

import com.billing.yota.dao.AccountDAO;
import com.billing.yota.dao.TransactionDAO;
import com.billing.yota.exception.TransactionException;
import com.billing.yota.model.entity.Account;
import com.billing.yota.model.entity.Transaction;
import com.billing.yota.model.pojo.TransferPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionDAO transactionDAO;

    @Autowired
    AccountDAO accountDAO;

    @Transactional
    public void create(TransferPOJO transferPOJO) {
        transactionDAO.create(transferPOJO);
    }

    @Transactional
    public List<Transaction> getHistoryByNumber(long number) {
        return transactionDAO.getListByNumber(number);
    }

    @Transactional
    public Transaction getLastTransactionByNumber(long number) {
        return transactionDAO.getLastTransactionByNumber(number);
    }

    @Transactional
    public void rollbackTransaction(long number) throws TransactionException {
        Transaction transaction = getLastTransactionByNumber(number);
        if (transaction.isWasRefund()) {
            throw new TransactionException("This transaction was completed previously!");
        }
        refundMoney(transaction);
    }

    @Transactional
    public void refundMoney(Transaction transaction) {
        Account to = accountDAO.findByNumber(transaction.getOrigin());
        Account from = accountDAO.findByNumber(transaction.getReceiver());

        accountDAO.changeBalance(to.getNumber(), to.getBalance() + transaction.getAmount());
        accountDAO.changeBalance(from.getNumber(), from.getBalance() - transaction.getAmount());

        transaction.setWasRefund(true);
        transactionDAO.updateRefund(transaction);
    }



}
