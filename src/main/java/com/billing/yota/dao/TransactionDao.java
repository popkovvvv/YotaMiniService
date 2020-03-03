package com.billing.yota.dao;

import com.billing.yota.model.entity.Transaction;
import com.billing.yota.model.pojo.TransferPOJO;

import java.util.List;

public interface TransactionDao {

    void create( TransferPOJO transferPOJO);
    List<Transaction> getListByNumber( long number);
    Transaction getLastTransactionByNumber(long number);
    void updateRefund(Transaction transaction);

}
