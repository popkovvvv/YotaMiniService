package com.billing.yota.controller;

import com.billing.yota.exception.TransactionException;
import com.billing.yota.model.entity.Account;
import com.billing.yota.model.pojo.TransferPOJO;
import com.billing.yota.service.impl.AccountServiceImpl;
import com.billing.yota.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class AccountController {

    private final AccountServiceImpl accountServiceImpl;

    private final TransactionServiceImpl transactionServiceImpl;

    @Autowired
    public AccountController( AccountServiceImpl accountServiceImpl, TransactionServiceImpl transactionServiceImpl ) {
        this.accountServiceImpl = accountServiceImpl;
        this.transactionServiceImpl = transactionServiceImpl;
    }

    @PostMapping("/account/add")
    public ResponseEntity<String> addUser( @ModelAttribute("user") Account account){
       return accountServiceImpl.create(account);
    }

    @GetMapping("/account/{number}")
    public Object getAccount(@PathVariable Integer number){
        return accountServiceImpl.findByNumber(number);
    }

    @PutMapping("/account/update")
    public ResponseEntity<String> updateBlocked( @RequestParam Integer number, @RequestParam Boolean transfer ){
        return accountServiceImpl.updateAccountTransfer(number, transfer);
    }

    @PostMapping("/account/transaction")
    public ResponseEntity<String> transfer( @ModelAttribute("transfer") TransferPOJO transferPOJO ) throws TransactionException {
        accountServiceImpl.transaction(transferPOJO.getFromAccountNumber(), transferPOJO.getToAccountNumber(), transferPOJO.getAmount());
        return transactionServiceImpl.create(transferPOJO);
    }

    @GetMapping("/account/transaction/check/{number}")
    public ResponseEntity<String> getCheckTransaction( @PathVariable Integer number){
        return accountServiceImpl.checkTransfer(number);
    }
}
