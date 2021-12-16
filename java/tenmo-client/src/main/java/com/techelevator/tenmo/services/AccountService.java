package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountService {

    RestTemplate restTemplate = new RestTemplate();
    private String url = "http://localhost:8080/accounts";

    public Account getBalance(String token) {

        HttpEntity entity = entityMaker(token);

        ResponseEntity<Account> response = restTemplate.exchange(url, HttpMethod.GET, entity, Account.class);
        Account account = response.getBody();
        return account;
    }

    public List<User> getAllAccounts(String token) {

        HttpEntity entity = entityMaker(token);

        ResponseEntity<User[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, User[].class);
        User[] users = response.getBody();
        return new ArrayList<>(Arrays.asList(users));
    }

    public boolean sendTransfer(String token, TransferDTO transferDTO) {
        boolean success = false;
        HttpEntity entity = entityMaker(token, transferDTO);

        ResponseEntity<TransferDTO> transfer = restTemplate.exchange(url, HttpMethod.POST, entity, TransferDTO.class);
        if (transfer != null) {
            success = true;
        }
        return success;
    }


    private HttpEntity entityMaker(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }

    private HttpEntity entityMaker(String token, TransferDTO transferDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TransferDTO> entity = new HttpEntity<>(transferDTO, headers);
        return entity;
    }

}
