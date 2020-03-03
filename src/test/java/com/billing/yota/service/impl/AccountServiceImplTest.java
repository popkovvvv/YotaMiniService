package com.billing.yota.service.impl;

import com.billing.yota.dao.impl.AccountDaoImpl;
import com.billing.yota.exception.TransactionException;
import com.billing.yota.model.entity.Account;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountServiceImplTest {

    @Autowired
    private AccountServiceImpl accountService;

    @MockBean
    private AccountDaoImpl accountDao;

    @Test
    public void create() {
        Account account = new Account();
        ResponseEntity<String> stringResponseEntity = accountService.create(account);
        assertEquals("Create completed", stringResponseEntity.getBody());
        Mockito.verify(accountDao, Mockito.times(1)).create(account);
    }

    @Test
    public void findByNumber() {
        Account account = new Account();
        account.setFullName("Alex");
        account.setNumber(1234);

        Mockito.doReturn(account)
                .when(accountDao)
                .findByNumber(1234);

        Account find = accountDao.findByNumber(1234);
        assertEquals(account.getNumber(), find.getNumber());
    }

    @Test
    public void findByNumberFailure() {
        Account account = new Account();
        account.setFullName("Alex");
        account.setNumber(1234);

        Mockito.doReturn(account)
                .when(accountDao)
                .findByNumber(1234);

        assertNull(accountDao.findByNumber(123));
    }

    @Test
    public void changeBalance() {
        Account account = new Account();
        account.setFullName("Alex");
        account.setNumber(12345);
        account.setBalance(BigDecimal.valueOf(2000));
        account.setCanTransfer(true);

        Mockito.doReturn(account)
                .when(accountDao)
                .findByNumber(12345);

        Account find = accountDao.findByNumber(12345);
        BigDecimal amount = BigDecimal.valueOf(1000);
        BigDecimal newBalance = find.getBalance().add(amount);

        assertEquals(1, newBalance.compareTo(BigDecimal.valueOf(0)));
        assertTrue(find.isCanTransfer());

        Mockito.doNothing().when(accountDao).changeBalance(find.getNumber(), amount);
    }



    @Test
    public void updateAccountTransfer() {
        Account account = new Account();
        account.setNumber(12345678);
        account.setCanTransfer(false);

        ResponseEntity<String> stringResponseEntity = accountService.updateAccountTransfer(account.getNumber(), true);
        assertEquals("Updated", stringResponseEntity.getBody());
        Mockito.verify(accountDao, Mockito.times(1)).update(account.getNumber(), true);
    }

    @Test
    public void checkTransfer() {
        Account account = new Account();
        account.setNumber(12345678);
        account.setCanTransfer(true);

        Mockito.doReturn(account.isCanTransfer())
                .when(accountDao)
                .checkTransfer(12345678);

        boolean check = accountDao.checkTransfer(account.getNumber());
        assertTrue(check);
    }

}