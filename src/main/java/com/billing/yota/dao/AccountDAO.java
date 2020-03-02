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

    public void create(Account account) {
        manager.persist(account);
    }

    public void update(long number, boolean isCan) {
        Query query = manager.createNativeQuery("UPDATE account SET is_can_transfer= :p where number = :n");
        query.setParameter("p", isCan);
        query.setParameter("n", number);
        query.executeUpdate();
    }

    public boolean checkTransfer(long number) {
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

    public void changeBalance(long number, double balance) {
        manager.createQuery(
                "Update Account account set account.balance = :p where account.number = :i")
                .setParameter("p", balance)
                .setParameter("i", number)
                .executeUpdate();
    }


}
