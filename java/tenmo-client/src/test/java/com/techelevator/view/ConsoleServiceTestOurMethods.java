package com.techelevator.view;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import io.cucumber.java.bs.A;
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
    public void Returns_User_Input_BigDecimal_Success_When_Valid_Input() {
        BigDecimal expected = new BigDecimal(30.15);
        ConsoleService console = getServiceForTestingWithUserInput(expected.toString());
        BigDecimal result = console.getUserInputBigDecimal("30.15");
        Assert.assertEquals(expected, result);
    }

    @Test
    public void Returns_User_Input_BigDecimal_Fails_When_INVALID_Input() {
        ConsoleService console = getServiceForTestingWithUserInput("Noway");

        String actual = "";
        BigDecimal result;
        try {
            result = console.getUserInputBigDecimal("20.35");
        } catch (NoSuchElementException e) {
            actual = "failed";
        }
        Assert.assertEquals("failed", actual);
    }

    @Test
    public void Returns_User_Input_BigDecimal_Zero_Fails() {
        BigDecimal expected = new BigDecimal(30.15);
        ConsoleService console = getServiceForTestingWithUserInput(expected.toString());
        BigDecimal result = console.getUserInputBigDecimal("30.15");
        Assert.assertEquals(expected, result);
    }


    @Test
    public void Get_Amount_Returns_Correct_ID_When_ID_Is_Found() {
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
    public void Get_Amount_Returns_Nothing_ID_Is_NOT_Found() {
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


    }

    @Test
    public void Check_Transfer_Amount_Send_Amount_Is_Less_Than_Balance_So_Can_Send() {
        Account account = new Account();
        account.setAccountBalance(new BigDecimal("20.50"));

        BigDecimal expected = new BigDecimal("20.00");

        ConsoleService console = getServiceForTestingWithUserInput("20.00");
        BigDecimal actual = (console.checkTransferAmount(account));

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void Check_Transfer_Amount_Send_Amount_Is_More_Than_Balance_So_CANNOT_Send() {
        Account account = new Account();
        account.setAccountBalance(new BigDecimal("18.50"));

        ConsoleService console = getServiceForTestingWithUserInput("20.00");

        BigDecimal result = new BigDecimal("0");
        String actual = "";
        try {
            result = console.checkTransferAmount(account);
        } catch (NoSuchElementException e) {
            actual = "failed";
        }
        Assert.assertEquals("failed", actual);
    }

    @Test
    public void Check_Transfer_Id_Given_Correct_Id_Returns_Transfer_Id() {

        TransferDTO transOne = new TransferDTO();
        transOne.setTransferId(1);
        TransferDTO transTwo = new TransferDTO();
        transTwo.setTransferId(2);
        TransferDTO transThree = new TransferDTO();
        transThree.setTransferId(3);

        TransferDTO[] transfers = {transOne, transTwo, transThree};

        ConsoleService console = getServiceForTestingWithUserInput("2");
        Integer result = console.checkTransferId(transfers, 1);
        Integer expected = 2;

        Assert.assertEquals(expected, result);

    }

    @Test
    public void Check_Transfer_Id_Given_Non_Existent_Id_Returns_No_Element_Exception() {

        TransferDTO transOne = new TransferDTO();
        transOne.setTransferId(1);
        TransferDTO transTwo = new TransferDTO();
        transTwo.setTransferId(2);
        TransferDTO transThree = new TransferDTO();
        transThree.setTransferId(3);

        TransferDTO[] transfers = {transOne, transTwo, transThree};

        ConsoleService console = getServiceForTestingWithUserInput("404");

        String actual = "";
        try {
            Integer result = console.checkTransferId(transfers, 1);
        } catch (NoSuchElementException e) {
            actual = "failed";
        }
        Assert.assertEquals("failed", actual);

    }

    //******************************************************************************
    private ConsoleService getServiceForTestingWithUserInput(String userInput) {
        ByteArrayInputStream input = new ByteArrayInputStream(String.valueOf(userInput).getBytes());
        return new ConsoleService(input, output);
    }

    private ConsoleService getServiceForTesting() {
        return getServiceForTestingWithUserInput("1" + System.lineSeparator());
    }
}