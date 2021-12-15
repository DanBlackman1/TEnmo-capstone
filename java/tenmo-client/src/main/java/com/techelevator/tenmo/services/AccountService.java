package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class AccountService {

    RestTemplate restTemplate = new RestTemplate();
    private String url = "http://localhost:8080/accounts";

    public Account getBalance(String token ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity<>(headers);
        ResponseEntity<Account> response = restTemplate.exchange(url, HttpMethod.GET, entity, Account.class);
        Account account = response.getBody();
        return account;
    }



}
