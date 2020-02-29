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

    @Transactional
    public void create(TransferPOJO transferPOJO) {
        Account from = accountDAO.findByNumber(transferPOJO.getFromAccountNumber());
        Account to = accountDAO.findByNumber(transferPOJO.getToAccountNumber());
        Transaction transaction = new Transaction();

        transaction.setAmount(transferPOJO.getAmount());
        transaction.setOrigin(from);
        transaction.setReceiver(to);
        transaction.setTransfer_at(LocalDateTime.now());
        manager.persist(transaction);
    }

    //TODO: пофиксить
    @Transactional
    public List<Transaction> getListByNumber(long number) {
      TypedQuery<Transaction> typedQuery =  manager.createQuery(
        "SELECT number from Transaction t INNER JOIN t.origin.number number where number = :n", Transaction.class)
                .setParameter("n", number);

      return typedQuery.getResultStream().collect(Collectors.toList());
    }
}
