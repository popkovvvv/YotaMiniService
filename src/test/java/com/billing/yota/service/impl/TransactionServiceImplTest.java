package com.billing.yota.service.impl;

import com.billing.yota.dao.AccountDao;
import com.billing.yota.dao.impl.AccountDaoImpl;
import com.billing.yota.dao.impl.TransactionDaoImpl;
import com.billing.yota.exception.TransactionException;
import com.billing.yota.model.entity.Account;
import com.billing.yota.model.entity.Transaction;
import com.billing.yota.model.pojo.TransferPOJO;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
class TransactionServiceImplTest {

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private TransactionServiceImpl transactionService;

    @MockBean
    private TransactionDaoImpl transactionDao;

    @MockBean
    private AccountDaoImpl accountDao;

    @Test
    void create() {
        TransferPOJO transferPOJO = new TransferPOJO();
        transferPOJO.setAmount(BigDecimal.valueOf(20000));
        transferPOJO.setFromAccountNumber(123);
        transferPOJO.setToAccountNumber(12345);
        ResponseEntity<String> stringResponseEntity = transactionService.create(transferPOJO);
        assertEquals("Create completed", stringResponseEntity.getBody());
        Mockito.verify(transactionDao, Mockito.times(1)).create(transferPOJO);
    }

    @Test
    void getHistoryByNumber() {
        Transaction transaction = new Transaction();
        transaction.setWasRefund(true);
        transaction.setAmount(BigDecimal.valueOf(2000));
        transaction.setTransfer_at(LocalDateTime.now());
        transaction.setOrigin(123);
        transaction.setReceiver(1234);

        List<Transaction> list = new ArrayList<>();
        list.add(transaction);

        Mockito.doReturn(list)
                .when(transactionDao)
                .getListByNumber(123);

        List<Transaction> find = transactionService.getHistoryByNumber(123);
        assertFalse(find.isEmpty());
        Mockito.verify(transactionDao, Mockito.times(1)).getListByNumber(123);


    }

    @Test
    void getLastTransactionByNumber() {
        Transaction transaction = new Transaction();
        transaction.setWasRefund(true);
        transaction.setAmount(BigDecimal.valueOf(2000));
        transaction.setTransfer_at(LocalDateTime.now());
        transaction.setOrigin(123);
        transaction.setReceiver(1234);

        Mockito.doReturn(transaction)
                .when(transactionDao)
                .getLastTransactionByNumber(123);

        Transaction find = transactionService.getLastTransactionByNumber(123);
        assertEquals(transaction.getOrigin(), find.getOrigin());
        Mockito.verify(transactionDao, Mockito.times(1)).getLastTransactionByNumber(123);

    }

    @Test
    void rollbackTransaction() throws TransactionException {
        Transaction transaction = new Transaction();
        transaction.setWasRefund(false);
        transaction.setAmount(BigDecimal.valueOf(2000));
        transaction.setTransfer_at(LocalDateTime.now());
        transaction.setOrigin(123);
        transaction.setReceiver(1234);

        Mockito.doReturn(transaction)
                .when(transactionDao)
                .getLastTransactionByNumber(123);

        transactionService.rollbackTransaction(123);
        Mockito.verify(transactionDao, Mockito.times(1)).getLastTransactionByNumber(123);
    }

    //TODO: НАПИСАТЬ ТЕСТ ДЛЯ ВОЗВРАТА ДЕНЕГ
    void refundMoney() {
        Transaction transaction = new Transaction();
        transaction.setWasRefund(true);
        transaction.setAmount(BigDecimal.valueOf(2000));
        transaction.setTransfer_at(LocalDateTime.now());
        transaction.setOrigin(123);
        transaction.setReceiver(1234);

        ResponseEntity<String> stringResponseEntity = transactionService.refundMoney(transaction);

        Mockito.verify(accountDao, Mockito.times(2)).findByNumber(ArgumentMatchers.anyInt());
        Mockito.verify(accountDao, Mockito.times(2)).changeBalance(
                123,
                BigDecimal.valueOf(2000));
        transaction.setWasRefund(true);
        Mockito.verify(transactionDao, Mockito.times(1)).updateRefund(transaction);

        assertEquals("Refund money", stringResponseEntity.getBody());


    }
}