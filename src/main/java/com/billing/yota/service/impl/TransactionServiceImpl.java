package com.billing.yota.service.impl;

import com.billing.yota.dao.impl.AccountDaoImpl;
import com.billing.yota.dao.impl.TransactionDaoImpl;
import com.billing.yota.exception.TransactionException;
import com.billing.yota.model.entity.Account;
import com.billing.yota.model.entity.Transaction;
import com.billing.yota.model.pojo.TransferPOJO;
import com.billing.yota.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionsService {

    private final AccountDaoImpl accountDaoImpl;

    private final TransactionDaoImpl transactionDaoImpl;

    @Autowired
    public TransactionServiceImpl(AccountDaoImpl accountDaoImpl, TransactionDaoImpl transactionDaoImpl) {
        this.accountDaoImpl = accountDaoImpl;
        this.transactionDaoImpl = transactionDaoImpl;
    }

    @Transactional
    @Override
    public ResponseEntity<String> create(TransferPOJO transferPOJO) {
        try {
            transactionDaoImpl.create(transferPOJO);
            return new ResponseEntity<>("Create completed", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>("Create error", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Transaction> getHistoryByNumber(int number) {
        return transactionDaoImpl.getListByNumber(number);
    }

    @Transactional(readOnly = true)
    @Override
    public Transaction getLastTransactionByNumber(int number) {
        return transactionDaoImpl.getLastTransactionByNumber(number);
    }

    @Transactional
    @Override
    public ResponseEntity<String> rollbackTransaction(int number) throws TransactionException {
        Transaction transaction = getLastTransactionByNumber(number);
        if (transaction.isWasRefund()) {
            throw new TransactionException("This transaction was completed previously!");
        }
        return refundMoney(transaction);
    }

    @Transactional
    @Override
    public ResponseEntity<String> refundMoney(Transaction transaction) {
        try {
            Account to = accountDaoImpl.findByNumber(transaction.getOrigin());
            Account from = accountDaoImpl.findByNumber(transaction.getReceiver());

            accountDaoImpl.changeBalance(to.getNumber(), to.getBalance().add(transaction.getAmount()));
            accountDaoImpl.changeBalance(from.getNumber(), from.getBalance().subtract(transaction.getAmount()));

            transaction.setWasRefund(true);
            transactionDaoImpl.updateRefund(transaction);
        } catch (Exception e) {
            return new ResponseEntity<>("Didnt refund", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Refund money", HttpStatus.OK);
    }



}
