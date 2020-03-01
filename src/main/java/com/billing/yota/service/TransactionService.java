package com.billing.yota.service;

import com.billing.yota.dao.TransactionDAO;
import com.billing.yota.model.entity.Transaction;
import com.billing.yota.model.pojo.TransferPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionDAO transactionDAO;

    public void create(TransferPOJO transferPOJO) {
        transactionDAO.create(transferPOJO);
    }

    public List<Transaction> getHistoryByNumber(long number) {
        return transactionDAO.getListByNumber(number);
    }

    public Transaction getLastTransactionByNumber(long number) {
        return transactionDAO.getLastTransactionByNumber(number);
    }



}
