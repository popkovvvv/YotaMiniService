package com.billing.yota.dao.impl;

import com.billing.yota.dao.TransactionDao;
import com.billing.yota.dao.impl.AccountDaoImpl;
import com.billing.yota.model.entity.Transaction;
import com.billing.yota.model.pojo.TransferPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TransactionDaoImpl implements TransactionDao {

    @PersistenceContext
    private final EntityManager manager;

    @Autowired
    public TransactionDaoImpl(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void create(TransferPOJO transferPOJO) {
        manager.createNativeQuery(
                "INSERT INTO transaction (amount, transfer_at, origin, receiver, is_was_refund) VALUES (?,?,?,?,?)")
                .setParameter(1, transferPOJO.getAmount())
                .setParameter(2, LocalDateTime.now())
                .setParameter(3, transferPOJO.getFromAccountNumber())
                .setParameter(4, transferPOJO.getToAccountNumber())
                .setParameter(5, false)
                .executeUpdate();
    }

    @Override
    public List<Transaction> getListByNumber(int number) {
        return manager.createQuery(
                "select tr FROM Transaction tr WHERE tr.origin = :o ", Transaction.class)
                .setParameter("o", number)
                .getResultList();
    }

    @Override
    public Optional<Transaction> getLastTransactionByNumber(int number) {
        try {
            return Optional.ofNullable(manager.createQuery(
                    "SELECT tr FROM Transaction tr where tr.origin = :o ORDER BY tr.transfer_at DESC", Transaction.class)
                    .setParameter("o", number)
                    .setMaxResults(1)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }

    @Override
    public void updateRefund(Transaction transaction) {
        manager.createQuery("UPDATE Transaction set isWasRefund = :r where id = :id")
                .setParameter("r", transaction.isWasRefund())
                .setParameter("id", transaction.getId())
                .executeUpdate();
    }

}
