package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class AccountController {

    @Autowired
    AccountDao accountDao;

    @GetMapping("/accounts")
    public BigDecimal getBalance(@RequestBody int userId) {
        BigDecimal balance = accountDao.getAccountBalance(userId).getAccountBalance();
        return balance;
    }



}
