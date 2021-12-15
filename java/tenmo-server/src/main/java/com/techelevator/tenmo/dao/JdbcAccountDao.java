package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.Principal;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbctemplate;

    public JdbcAccountDao(JdbcTemplate jdbctemplate) {
        this.jdbctemplate = jdbctemplate;
    }

    @Override
    public Account getAccountBalance(int userId) {
        String sql = "SELECT a.balance, a.user_id, a.account_id FROM users u JOIN accounts a " +
                "ON a.user_id = u.user_id WHERE u.user_id = ?";
        Account account = jdbctemplate.queryForObject(sql, Account.class, userId);
        return account;
    }

}
