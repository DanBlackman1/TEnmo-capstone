package com.techelevator;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

public class AccountTests extends BaseDaoTests{

    private static final Account ACCOUNT_ONE = new Account(10,1, BigDecimal.valueOf(140.00));
    private static final Account ACCOUNT_TWO = new Account(20,2, BigDecimal.valueOf(25.50));
    private static final Account ACCOUNT_THREE = new Account(30,3, BigDecimal.valueOf(3000.42));
   // private static final User USER_ONE = new User(1L, "rob", "rob", "true");





    private Account testAccount;
    private JdbcAccountDao sut;

    @Before
    public void setup() {
        sut = new JdbcAccountDao(dataSource);
        testAccount = new Account(50, 5, BigDecimal.valueOf(80.00));
    }
    //************************************************************************************


    @Test
    public void Get_Account_Balance_Returns_Balance_From_Correct_Username() {
        Account createdAccount =sut.getAccountBalance("rob");

        Account expected = ACCOUNT_ONE;

        assertAccountsMatch(expected, createdAccount);
    }

    @Test
    public void Get_Account_Balance_DOESNT_Return_Balance_From_Incorrect_Username() {
    }

    @Test
    public void Get_Account_Balance_DOESNT_Return_Balance_From_Number_Input() {
    }

    //************************************************************************************
    private void assertAccountsMatch(Account expected, Account actual) {
        Assert.assertEquals(expected.getAccountBalance(), actual.getAccountBalance());
        Assert.assertEquals(expected.getAccountId(), actual.getAccountId());
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
    }

}
