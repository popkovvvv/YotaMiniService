package com.billing.yota.controller;

import com.billing.yota.exception.TransactionException;
import com.billing.yota.model.entity.Transaction;
import com.billing.yota.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/history/{number}")
    public List<Transaction> getHistoryByNumber( @PathVariable Long number ) {
        return transactionService.getHistoryByNumber(number);
    }

    @GetMapping("/account/transaction/rollback/{number}")
    public void getRollBackTransaction(@PathVariable Long number) throws TransactionException {
        transactionService.rollbackTransaction(number);
    }

}
