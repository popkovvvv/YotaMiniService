package com.billing.yota.controller;

import com.billing.yota.exception.TransactionException;
import com.billing.yota.model.entity.Transaction;
import com.billing.yota.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class TransactionController {

    private final TransactionServiceImpl transactionServiceImpl;

    @Autowired
    public TransactionController(TransactionServiceImpl transactionServiceImpl) {
        this.transactionServiceImpl = transactionServiceImpl;
    }

    @GetMapping("/history/{number}")
    public List<Transaction> getHistoryByNumber(@PathVariable Integer number ) {
        return transactionServiceImpl.getHistoryByNumber(number);
    }

    @GetMapping("/account/transaction/rollback/{number}")
    public ResponseEntity<String> getRollBackTransaction(@PathVariable Integer number) throws TransactionException {
        return transactionServiceImpl.rollbackTransaction(number);
    }

}
