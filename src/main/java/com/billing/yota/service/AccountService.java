package com.billing.yota.service;

import com.billing.yota.dao.AccountDAO;
import com.billing.yota.exception.TransactionException;
import com.billing.yota.model.entity.Account;
import com.billing.yota.model.pojo.TransferPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class AccountService {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    AccountDAO accountDAO;

    public void create(Account account) {
        accountDAO.create(account);
    }

    public Account findByNumber(long number) {
      return accountDAO.findByNumber(number);
    }

    public void transaction( TransferPOJO transferPOJO ) throws TransactionException {
        accountDAO.transaction(transferPOJO.getFromAccountNumber(),transferPOJO.getToAccountNumber(), transferPOJO.getAmount());
    }

    public void updateAccountTransfer(long number, boolean isCan) {
        accountDAO.update(number, isCan);
    }


}
