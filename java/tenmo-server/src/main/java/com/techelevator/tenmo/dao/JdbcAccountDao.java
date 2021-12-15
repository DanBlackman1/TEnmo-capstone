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
    public BigDecimal getAccountBalance(String username) {
        String sql = "SELECT a.balance, a.user_id, a.account_id FROM users u JOIN accounts a " +
                "ON a.user_id = u.user_id WHERE u.username = ?";
        BigDecimal account = jdbctemplate.queryForObject(sql, BigDecimal.class, username);
        return account;
    }

}
