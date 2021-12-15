package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@RequestMapping("/accounts")
@PreAuthorize("isAuthenticated()")
public class AccountController {

    @Autowired
    AccountDao accountDao;



    @GetMapping("")
    @PreAuthorize("permitAll")
    public BigDecimal getBalance(Principal principal) {
       // BigDecimal balance = accountDao.getAccountBalance(id).getAccountBalance();
        BigDecimal balance = accountDao.getAccountBalance(principal.getName());
        return balance;
    }



}
