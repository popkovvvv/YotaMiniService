package com.billing.yota.dao;

import com.billing.yota.model.entity.Transaction;
import com.billing.yota.model.pojo.TransferPOJO;

import java.util.List;
import java.util.Optional;

public interface TransactionDao {

    void create( TransferPOJO transferPOJO);
    List<Transaction> getListByNumber(int number);
    Optional<Transaction> getLastTransactionByNumber(int number);
    void updateRefund(Transaction transaction);

}
