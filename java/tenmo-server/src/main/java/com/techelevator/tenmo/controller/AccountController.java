package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



import java.security.Principal;


@RestController
@RequestMapping("/accounts")
@PreAuthorize("isAuthenticated()")
public class AccountController {

    @Autowired
    AccountDao accountDao;
    @Autowired
    UserDao userDao;

    @GetMapping("")
    public Account getBalance(Principal principal) {
        Account account = accountDao.getAccountBalance(principal.getName());
        return account;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public User[] getUserList() {
        return userDao.findAll().toArray(new User[0]);
    }

    //@ResponseStatus
    @PostMapping("")
    public TransferDTO processTransfer(@RequestBody TransferDTO transferDTO) {
        accountDao.processTransfer(transferDTO);
        return transferDTO;
    }

    @RequestMapping(path = "/transfers", method = RequestMethod.GET)
    public TransferDTO[] transferList(Principal principal) {
       long userId = userDao.findIdByUsername(principal.getName());
       return accountDao.listTransfers(userId);
       // TransferDTO[] transfers;
       // transfers = new TransferDTO[]{};
        //return transfers;
    }



}
