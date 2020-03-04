package com.billing.yota.dao.impl;

import com.billing.yota.dao.AccountDao;
import com.billing.yota.model.entity.Account;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Optional;

@Repository
public class AccountDaoImpl implements AccountDao {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void create(Account account) {
        manager.persist(account);
    }

    @Override
    public void update(int number, boolean isCan) {
        Query query = manager.createNativeQuery("UPDATE account SET is_can_transfer= :p where number = :n");
        query.setParameter("p", isCan);
        query.setParameter("n", number);
        query.executeUpdate();
    }

    @Override
    public Optional<Boolean> checkTransfer(int number) {
        try {
            return Optional.ofNullable(manager.createQuery(
                    "select account.isCanTransfer from Account account where account.number = :n", Boolean.class)
                    .setParameter("n", number)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();

        }

    }

    @Override
    public Optional<Account> findByNumber(int number) {
        try {
            return Optional.ofNullable(manager.createQuery(
                    "SELECT account from Account account where account.number = :p", Account.class)
                    .setParameter("p", number)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }

    @Override
    public void changeBalance(int number, BigDecimal balance) {
        manager.createQuery(
                "Update Account account set account.balance = :p where account.number = :i")
                .setParameter("p", balance)
                .setParameter("i", number)
                .executeUpdate();
    }


}
