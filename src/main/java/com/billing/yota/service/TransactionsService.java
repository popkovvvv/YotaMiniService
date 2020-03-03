package com.billing.yota.service;

import com.billing.yota.exception.TransactionException;
import com.billing.yota.model.entity.Transaction;
import com.billing.yota.model.pojo.TransferPOJO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransactionsService {

    ResponseEntity<String> create( TransferPOJO transferPOJO);
    List<Transaction> getHistoryByNumber(int number);
    Transaction getLastTransactionByNumber(int number);
    ResponseEntity<String> rollbackTransaction(int number) throws TransactionException;
    ResponseEntity<String> refundMoney(Transaction transaction);
}
