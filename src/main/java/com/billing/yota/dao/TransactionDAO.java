package com.billing.yota.dao;

import com.billing.yota.model.entity.Account;
import com.billing.yota.model.entity.Transaction;
import com.billing.yota.model.pojo.TransferPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TransactionDAO {

    @Autowired
    AccountDAO accountDAO;

    @PersistenceContext
    private EntityManager manager;

    public void create(TransferPOJO transferPOJO) {
        manager.createNativeQuery(
                "INSERT INTO transaction (amount, transfer_at, origin, receiver) VALUES (?,?,?,?)")
                .setParameter(1, transferPOJO.getAmount())
                .setParameter(2, LocalDateTime.now())
                .setParameter(3, transferPOJO.getFromAccountNumber())
                .setParameter(4, transferPOJO.getToAccountNumber())
                .executeUpdate();
    }

    public List<Transaction> getListByNumber(long number) {
        return manager.createQuery(
                "select tr FROM Transaction tr WHERE tr.origin = :o ", Transaction.class)
                .setParameter("o", number)
                .getResultList();
    }

    public Transaction getLastTransactionByNumber(long number) {
        return manager.createQuery(
                "SELECT tr FROM Transaction tr where tr.origin = :o ORDER BY tr.transfer_at DESC", Transaction.class)
                .setParameter("o", number)
                .setMaxResults(1)
                .getSingleResult();
    }

    public void updateRefund(Transaction transaction) {
        manager.createQuery("update Transaction tr set Transaction.isWasRefund = :r where tr.id = :id")
                .setParameter("r", transaction.isWasRefund())
                .setParameter("id", transaction.getId())
                .executeUpdate();
    }

}
