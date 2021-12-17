package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
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
    public List<User> listAllUsers() {
        List<User> userList = null;

        String sql = "SELECT user_id, username, password_hash, FROM users ";
        SqlRowSet results = jdbctemplate.queryForRowSet(sql);
        while (results.next()) {
            userList.add(mapRowToUser(results));

        }
        return userList;
    }

    @Override // added this potentially unnecessarily
    public void processTransfer(TransferDTO transferDTO) {

        String sqlBegin = "BEGIN TRANSACTION; ";
        String sqlEnd = "COMMIT;";

        String sqlBalIncrease = "UPDATE accounts SET balance = balance + ? WHERE user_id = ?; ";
        String sqlBalDecrease = "UPDATE accounts SET balance = balance - ? WHERE user_id = ?; ";

        jdbctemplate.update(sqlBegin + sqlBalDecrease + sqlBalIncrease + sqlEnd,
                transferDTO.getAmount(), transferDTO.getUserFrom(), transferDTO.getAmount(), transferDTO.getUserTo());


        String transferRecordSql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
                " VALUES (2, 2, (SELECT account_id FROM accounts WHERE user_id = ?), (SELECT account_id FROM accounts WHERE user_id = ?), ?)";
        jdbctemplate.update(transferRecordSql, transferDTO.getUserFrom(), transferDTO.getUserTo(), transferDTO.getAmount());
    }

    @Override
    public TransferDTO[] listTransfers(long userId) {
        List<TransferDTO> transferList = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from,\n" +
                "                account_to, amount FROM transfers\n" +
                "                JOIN accounts ON transfers.account_from = accounts.account_id OR\n" +
                "                transfers.account_to = accounts.account_id WHERE user_id = ?;";

        SqlRowSet results = jdbctemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            TransferDTO transferDTO = mapRowToTransferDTO(results);
                transferList.add(transferDTO);
        }
        TransferDTO[] transfers = new TransferDTO[transferList.size()];
        transfers = transferList.toArray(transfers);
        return transfers;
    }

    private Account mapRowToAccount(SqlRowSet results) {
        int accountId = results.getInt("account_id");
        int userId = results.getInt("user_id");
        BigDecimal accountBalance = results.getBigDecimal("balance");
        Account account = new Account(accountId, userId, accountBalance);
        return account;
    }

    private TransferDTO mapRowToTransferDTO(SqlRowSet results) {

        String userFromName = "";
        String userToName = "";
        int userTo = 0;
        int userFrom = 0;

        int transferId = results.getInt("transfer_id");
        int transferTypeId = results.getInt("transfer_type_id");
        String status = results.getString("transfer_status_id");
        int accountFrom = results.getInt("account_from");
        int accountTo = results.getInt("account_to");
        BigDecimal amount = results.getBigDecimal("amount");

        SqlRowSet secondResult = jdbctemplate.queryForRowSet("SELECT u.username, u.user_id FROM users u JOIN accounts a ON u.user_id = a.user_id WHERE a.account_id = ?", accountFrom);
        while (secondResult.next()) {
            userFromName = secondResult.getString("username");
            userFrom = secondResult.getInt("user_id");
        }

        SqlRowSet thirdResult = jdbctemplate.queryForRowSet("SELECT u.username, u.user_id FROM users u JOIN accounts a ON u.user_id = a.user_id WHERE a.account_id = ?", accountTo);
        while (thirdResult.next()) {
            userToName = thirdResult.getString("username");
            userTo = thirdResult.getInt("user_id");
        }

        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setTransferId(transferId);
        transferDTO.setType("" + transferTypeId);
        transferDTO.setStatus(status);
        transferDTO.setUserFrom(userFrom);
        transferDTO.setUserTo(userTo);
        transferDTO.setUserToName(userToName);
        transferDTO.setUserFromName(userFromName);
        transferDTO.setAmount(amount);

        return transferDTO;
    }

    private User mapRowToUser(SqlRowSet results) {
        long id = results.getLong("user_id");
        String username = results.getString("username");
        //String password = results.getString("password_hash");

        User user = new User();
        user.setId(id);
        user.setUsername(username);
        //user.setPassword(password);
        return user;
    }

}
