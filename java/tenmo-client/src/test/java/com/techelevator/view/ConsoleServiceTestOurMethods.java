package com.techelevator.view;

import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ConsoleServiceTestOurMethods {
    //**********************
    private ByteArrayOutputStream output;

    @Before
    public void setup() {
        output = new ByteArrayOutputStream();
    }
    //**********************

    @Test
    public void presentBalance() {
    }

    @Test
    public void presentUserList() {
    }

    @Test
    public void presentTransferList() {
    }

    @Test
    public void presentChosenTransfer() {
    }

    @Test
    public void messageToUser() {
    }

    @Test
    public void Returns_User_Input_BigDecimal() {
        BigDecimal expected = new BigDecimal(30.15);
        ConsoleService console = getServiceForTestingWithUserInput(expected.toString());
        BigDecimal result = console.getUserInputBigDecimal("30.15");
        Assert.assertEquals(expected, result);
    }

    @Test
    public void Returns_User_Input_BigDecimal_Zero_Fails() {
        BigDecimal expected = new BigDecimal(30.15);
        ConsoleService console = getServiceForTestingWithUserInput(expected.toString());
        BigDecimal result = console.getUserInputBigDecimal("30.15");
        Assert.assertEquals(expected, result);
    }

//    @Test
//    public void returns_user_input_for_integer() {
//        Integer expected = 27;
//        ConsoleService console = getServiceForTestingWithUserInput(expected.toString());
//        Integer result = console.getUserInputInteger("Enter a number");
//        Assert.assertEquals(expected, result);
//    }

    @Test
    public void Get_Amount_Returns_Correct_Amount_ID_Is_Found() {
        User testUser = new User();
        testUser.setUsername("bob");
        testUser.setId(3);

        User secondTestUser = new User();
        secondTestUser.setUsername("joe");
        secondTestUser.setId(4);

        User[] userArray = new User[]{testUser, secondTestUser};
        ConsoleService console = getServiceForTestingWithUserInput("4");

        Integer expected = 4;
        Integer result = console.getAmount(userArray, testUser.getId());

        Assert.assertEquals(expected, result);
    }

    @Test
    public void Get_Amount_Returns_Correct_Amount_ID_Is_NOT_Found() {
        User testUser = new User();
        testUser.setUsername("bob");
        testUser.setId(3);

        User secondTestUser = new User();
        secondTestUser.setUsername("joe");
        secondTestUser.setId(4);

        User[] userArray = new User[]{testUser, secondTestUser};
        ConsoleService console = getServiceForTestingWithUserInput("1");

        String actual = "";
        Integer result;
        try {
             result = console.getAmount(userArray, testUser.getId());
        } catch (NoSuchElementException e) {
             actual = "failed";
        }
 Assert.assertEquals("failed", actual);


//        Integer expectedOne = testUser.getId();
//        Integer expectedTwo = secondTestUser.getId();
//        //Assert.assertEquals("", result);
//        // Assert.fail();
//        Assert.assertNotSame(expectedOne, result);
//        Assert.assertNotSame(expectedTwo, result);
    }

    @Test
    public void checkTransferAmount() {
    }

    @Test
    public void checkTransferId() {
    }

    private ConsoleService getServiceForTestingWithUserInput(String userInput) {
        ByteArrayInputStream input = new ByteArrayInputStream(String.valueOf(userInput).getBytes());
        return new ConsoleService(input, output);
    }

    private ConsoleService getServiceForTesting() {
        return getServiceForTestingWithUserInput("1" + System.lineSeparator());
    }
}