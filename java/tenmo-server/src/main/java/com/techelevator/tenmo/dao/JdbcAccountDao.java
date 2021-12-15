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
    public Account getAccountBalance(String username) {
        String sql = "SELECT a.account_id, a.user_id, a.balance FROM users u JOIN accounts a " +
                "ON a.user_id = u.user_id WHERE u.username = ?";
        SqlRowSet results = jdbctemplate.queryForRowSet(sql, username);
        Account account = null;
        while (results.next()) {
            account = mapRowToAccount(results);
        }
        return account;
    }

    private Account mapRowToAccount(SqlRowSet results) {
        int accountId = results.getInt("account_id");
        int userId = results.getInt("user_id");
        BigDecimal accountBalance = results.getBigDecimal("balance");
        Account account = new Account(accountId, userId, accountBalance);
        return account;
    }

}
