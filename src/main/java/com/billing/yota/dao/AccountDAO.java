package com.billing.yota.dao;

import com.billing.yota.exception.TransactionException;
import com.billing.yota.model.entity.Account;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class AccountDAO {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    public void create(Account account) {
        manager.persist(account);
    }

    @Transactional
    public void update(long number, boolean isCan) {
        manager.createQuery("UPDATE Account a SET a.isCanTransfer= :p where a.number = :n")
                .setParameter("p", isCan)
                .setParameter("n", number);
    }

    public Account findByNumber(long number) {
        return manager.createQuery(
                "SELECT account from Account account where account.number = :p", Account.class)
                .setParameter("p", number)
                .getSingleResult();
    }

    @Transactional
    public void changeBalance(long number, double amount) throws TransactionException {
        Account account = this.findByNumber(number);
        if (account == null) {
            throw new TransactionException("Account with number: " + number + " not found");
        }
        double newBalance = account.getBalance() + amount;
        if (account.getBalance() + amount < 0) {
            throw new TransactionException(
                    "The money in the account '" + number + "' is not enough (" + account.getBalance() + ")");
        }
        account.setBalance(newBalance);
        // Update to DB
        String sqlUpdate = "Update Account set balance = :p where id = :i";
        manager.createQuery(sqlUpdate).setParameter("p", account.getBalance())
                .setParameter("i", account.getId());

    }

    @Transactional(rollbackOn = TransactionException.class)
    public void transaction(Long fromAccountNumber, Long toAccountNumber, double amount) throws TransactionException {
        changeBalance(toAccountNumber, amount);
        changeBalance(fromAccountNumber, -amount);
    }


}
