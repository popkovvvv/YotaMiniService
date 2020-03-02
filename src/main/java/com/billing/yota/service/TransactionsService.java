package com.billing.yota.service;

import com.billing.yota.exception.TransactionException;
import com.billing.yota.model.entity.Transaction;
import com.billing.yota.model.pojo.TransferPOJO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransactionsService {

    void create( TransferPOJO transferPOJO);
    List<Transaction> getHistoryByNumber( long number);
    Transaction getLastTransactionByNumber(long number);
    ResponseEntity<String> rollbackTransaction( long number) throws TransactionException;
    ResponseEntity<String> refundMoney( Transaction transaction);
}
