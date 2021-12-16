package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferDTO;
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
    public Account getBalance(Principal principal) {
        Account account = accountDao.getAccountBalance(principal.getName());
        return account;
    }

    @PostMapping("")
    public TransferDTO processTransfer(@RequestBody TransferDTO transferDTO) {
        TransferDTO transfer =
    }



}
