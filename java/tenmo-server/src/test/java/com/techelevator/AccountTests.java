//package com.techelevator;
//
//import com.techelevator.tenmo.dao.JdbcAccountDao;
//import com.techelevator.tenmo.model.Account;
//import com.techelevator.tenmo.model.TransferDTO;
//import com.techelevator.tenmo.model.User;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//public class AccountTests extends BaseDaoTests{
//
//    private static final Account ACCOUNT_ONE = new Account(10,1, BigDecimal.valueOf(140.00));
//    private static final Account ACCOUNT_TWO = new Account(20,2, BigDecimal.valueOf(25.50));
//    private static final Account ACCOUNT_THREE = new Account(30,3, BigDecimal.valueOf(3000.42));
//   // private static final User USER_ONE = new User(1L, "rob", "rob", "true");
//
//
//
//
//
//    private Account testAccount;
//    private JdbcAccountDao sut;
//
//    @Before
//    public void setup() {
//        sut = new JdbcAccountDao(dataSource);
//        testAccount = new Account(50, 5, BigDecimal.valueOf(80.00));
//    }
//    //************************************************************************************
//
//
//    @Test
//    public void Get_Account_Balance_Returns_Balance_From_Correct_Username() {
//        Account createdAccount = sut.getAccountBalance("rob");
//
//        Account expected = ACCOUNT_ONE;
//
//        assertAccountsMatch(expected, createdAccount);
//    }
//
//    @Test
//    public void Get_Account_Balance_DOESNT_Return_Balance_From_Incorrect_Username() {
//        Account createdAccount = sut.getAccountBalance("Charmander");
//
//        Assert.assertNull(createdAccount);
//
//    }
//
//    @Test
//    public void Get_Account_Balance_DOESNT_Return_Balance_From_Number_Input() {
//        Account createdAccount = sut.getAccountBalance("2");
//        Assert.assertNull(createdAccount);
//
//    }
//    @Test
//    public void process_Transfer_Updates_Accounts() {
//        TransferDTO transfer = new TransferDTO();
//        transfer.setTransferId(10);
//        transfer.setUserTo(1);
//        transfer.setUserFrom(2);
//        transfer.setUserToName("rob");
//        transfer.setUserFromName("Pikachu");
//        transfer.setAmount(new BigDecimal("20.00"));
//
//        BigDecimal robBalanceExpected = new BigDecimal("160.00");
//        BigDecimal pikachuBalanceExpected = new BigDecimal("5.50");
//
//        sut.processTransfer(transfer);
//
//        Assert.assertEquals(robBalanceExpected, sut.getAccountBalance("rob").getAccountBalance());
//        Assert.assertEquals(pikachuBalanceExpected, sut.getAccountBalance("Pikachu").getAccountBalance());
//
//    }
//    @Test
//    public void process_Tranfers_Updates_Transfers_Table() {
//        TransferDTO transfer = new TransferDTO();
//        transfer.setTransferId(11);
//        transfer.setUserTo(2);
//        transfer.setUserFrom(1);
//        transfer.setUserToName("Pikachu");
//        transfer.setUserFromName("rob");
//        transfer.setAmount(new BigDecimal("20.00"));
//
//        sut.processTransfer(transfer);
//        boolean isInArray = false;
//        TransferDTO[] transferDTOList = sut.listTransfers(2);
//        for(int i = 0; i < transferDTOList.length && !isInArray; i++) {
//            if(transfer.getTransferId() == transferDTOList[i].getTransferId()) {
//                isInArray = true;
//            }
//        }
//        Assert.assertTrue(isInArray);
//
//    }
//    @Test
//    public void fails_to_update_rows_if_Null() {
//        TransferDTO transfer = null;
//        try {
//            sut.processTransfer(transfer);
//        } catch (NullPointerException ex) {
//            Assert.assertTrue(true);
//        }
//
//    }
//
//    @Test
//    public void list_transfers_returns_array_from_valid_user_id() {
//        TransferDTO transfer = new TransferDTO();
//        transfer.setTransferId(3);
//        transfer.setAmount(new BigDecimal("20.00"));
//
//        TransferDTO transferTwo = new TransferDTO();
//        transferTwo.setTransferId(2);
//        transferTwo.setAmount(new BigDecimal("20.00"));
//
//        boolean isTransferOneInList = false;
//        boolean isTransferTwoInList = false;
//
//        TransferDTO[] transfersList = sut.listTransfers(3);
//        for (TransferDTO temp : transfersList) {
//            if (temp.getTransferId() == transfer.getTransferId() &&
//                    temp.getAmount().compareTo(transfer.getAmount()) == 0) {
//                isTransferOneInList = true;
//            }
//            if (temp.getTransferId() == transferTwo.getTransferId() &&
//                    temp.getAmount().compareTo(transferTwo.getAmount()) == 0) {
//                isTransferTwoInList = true;
//            }
//        }
//        Assert.assertTrue(isTransferOneInList);
//        Assert.assertTrue(isTransferTwoInList);
//
//    }
//
//    @Test
//    public void list_transfers_returns_NO_array_from_INVALID_user_id() {
//        TransferDTO[] transferDTOList = sut.listTransfers(50);
//
//        Assert.assertNull(transferDTOList);
//
//    }
//
//    //************************************************************************************
//    private void assertAccountsMatch(Account expected, Account actual) {
//        Assert.assertEquals(expected.getAccountBalance(), actual.getAccountBalance());
//        Assert.assertEquals(expected.getAccountId(), actual.getAccountId());
//        Assert.assertEquals(expected.getUserId(), actual.getUserId());
//    }
//
//}
