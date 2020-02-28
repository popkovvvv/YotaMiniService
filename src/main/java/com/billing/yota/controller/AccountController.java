package com.billing.yota.controller;

import com.billing.yota.exception.TransactionException;
import com.billing.yota.model.entity.Account;
import com.billing.yota.model.pojo.TransferPOJO;
import com.billing.yota.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping("api/v1")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController( AccountService accountService ) {
        this.accountService = accountService;
    }

    @PutMapping("/account/add")
    public void addUser(@ModelAttribute("user") Account account){
        accountService.create(account);
    }

    @GetMapping("/account/{number}")
    public Account getAccount(@PathVariable Long number){
        return accountService.findByNumber(number);
    }

    @PostMapping("/account/update")
    public void updateBlocked( @RequestParam Long number, @RequestParam Boolean transfer ){
        accountService.updateAccountTransfer(number, transfer);
    }

    @PostMapping("/account/transaction")
    public void transfer(@ModelAttribute("transfer") TransferPOJO transferPOJO ) throws TransactionException {
        accountService.transaction(transferPOJO);
    }

}
