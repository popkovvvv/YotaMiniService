package com.billing.yota.dao;

import com.billing.yota.exception.TransactionException;
import com.billing.yota.model.entity.Account;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
        Query query = manager.createNativeQuery("UPDATE account SET is_can_transfer= :p where number = :n");
        query.setParameter("p", isCan);
        query.setParameter("n", number);
        query.executeUpdate();
    }

    public boolean checkTransaction(long number) {
        return manager.createQuery(
                "select account.isCanTransfer from Account account where account.number = :n", Boolean.class)
                .setParameter("n", number)
                .getSingleResult();
    }

    public Account findByNumber(long number) {
        return manager.createQuery(
                "SELECT account from Account account where account.number = :p", Account.class)
                .setParameter("p", number)
                .getSingleResult();
    }

    //TODO: перенести в бизнес логику
    @Transactional
    public void changeBalance(long number, double amount) throws TransactionException {
        Account accountInfo = this.findByNumber(number);
        if (accountInfo == null) {
            throw new TransactionException("Account not found " + number);
        }
        double newBalance = accountInfo.getBalance() + amount;
        if (accountInfo.getBalance() + amount < 0) {
            throw new TransactionException(
                    "The money in the account '" + number + "' is not enough (" + accountInfo.getBalance() + ")");
        }
        if (!accountInfo.isCanTransfer()) {
            throw new TransactionException(
                    "Account " + number + " has blocked");
        }
        accountInfo.setBalance(newBalance);
        // Update to DB
        manager.createQuery(
                "Update Account account set account.balance = :p where account.number = :i")
                .setParameter("p", accountInfo.getBalance())
                .setParameter("i", number)
                .executeUpdate();
    }

    @Transactional(rollbackOn = TransactionException.class)
    public void transaction(Long fromAccountNumber, Long toAccountNumber, double amount) throws TransactionException {
        changeBalance(toAccountNumber, amount);
        changeBalance(fromAccountNumber, -amount);
    }


}
