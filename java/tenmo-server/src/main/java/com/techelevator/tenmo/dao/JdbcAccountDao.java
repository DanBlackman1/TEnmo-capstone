package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

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

    @Override
    public List<User> listAllUsers(){
        List<User> userList = null;

        String sql = "SELECT user_id, username, password_hash, FROM users ";
        SqlRowSet results = jdbctemplate.queryForRowSet(sql);
        while (results.next()) {
            userList.add(mapRowToUser(results));

        }
        return  userList;
    }

    public void processTransfer(TransferDTO transferDTO){

        String sqlBegin = "BEGIN TRANSACTION;";
        String sqlEnd = "COMMIT;";

        String sqlBalIncrease = "UPDATE accounts SET balance = balance + ? WHERE user_id = ?";
        String sqlBalDecrease = "UPDATE accounts SET balance = balance - ? WHERE user_id = ?";

        SqlRowSet results = jdbctemplate.queryForRowSet(sqlBegin + sqlBalDecrease + sqlBalIncrease + sqlEnd,
                transferDTO.getAmount(), transferDTO.getUserFrom(), transferDTO.getAmount(), transferDTO.getUserTo());
        // CREATE A TRANSFER RECORDED METHOD LATER TO HOUSE THE BELOW LOGIC
        
    }

    private Account mapRowToAccount(SqlRowSet results) {
        int accountId = results.getInt("account_id");
        int userId = results.getInt("user_id");
        BigDecimal accountBalance = results.getBigDecimal("balance");
        Account account = new Account(accountId, userId, accountBalance);
        return account;
    }

    private User mapRowToUser(SqlRowSet results) {
        long id = results.getLong("user_id");
        String username = results.getString("username");
        String password = results.getString("password_hash");

        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }

}
