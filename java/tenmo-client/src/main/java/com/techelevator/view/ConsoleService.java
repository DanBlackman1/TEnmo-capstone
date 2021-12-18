package com.techelevator.view;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Scanner;

public class ConsoleService {

    private PrintWriter out;
    private Scanner in;

    public ConsoleService(InputStream input, OutputStream output) {
        this.out = new PrintWriter(output, true);
        this.in = new Scanner(input);
    }

    public Object getChoiceFromOptions(Object[] options) {
        Object choice = null;
        while (choice == null) {
            displayMenuOptions(options);
            choice = getChoiceFromUserInput(options);
        }
        out.println();
        return choice;
    }

    private Object getChoiceFromUserInput(Object[] options) {
        Object choice = null;
        String userInput = in.nextLine();
        try {
            int selectedOption = Integer.valueOf(userInput);
            if (selectedOption > 0 && selectedOption <= options.length) {
                choice = options[selectedOption - 1];
            }
        } catch (NumberFormatException e) {
            // eat the exception, an error message will be displayed below since choice will be null
        }
        if (choice == null) {
            out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
        }
        return choice;
    }

    private void displayMenuOptions(Object[] options) {
        out.println();
        for (int i = 0; i < options.length; i++) {
            int optionNum = i + 1;
            out.println(optionNum + ") " + options[i]);
        }
        out.print(System.lineSeparator() + "Please choose an option >>> ");
        out.flush();
    }

    public String getUserInput(String prompt) {
        out.print(prompt + ": ");
        out.flush();
        return in.nextLine();
    }

    public Integer getUserInputInteger(String prompt) {
        Integer result = null;
        do {
            out.print(prompt + ": ");
            out.flush();
            String userInput = in.nextLine();
            try {
                result = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                out.println(System.lineSeparator() + "*** " + userInput + " is not valid ***" + System.lineSeparator());
            }
        } while (result == null);
        return result;
    }

    public void presentBalance(BigDecimal balance) {
        out.println("```");
        out.println("Your current account balance is: $" + balance);
        out.println("```");
    }

    public void presentUserList(User[] users, int id) {
        out.println("```");
        out.println("-------------------------------------------");
        out.println("Users                                      ");
        out.println("ID              Name                       ");
        out.println("-------------------------------------------");
        for (User user : users) {
            if (!user.getId().equals(id)) {
                out.printf("%-12s %-20s\n", user.getId(), user.getUsername());
            }
        }
        out.println("---------");
        out.println();
        out.println("Enter ID of user you are sending to (0 to cancel):");
        out.println();
    }

    public void presentTransferList(TransferDTO[] transfers, int id) {

        out.println("```");
        out.println("-------------------------------------------");
        out.println("Transfers                                  ");
        out.println("ID             From/To              Amount ");
        out.println("-------------------------------------------");
        for (TransferDTO transferDTO : transfers) {
            if (id == transferDTO.getUserFrom()) {
                out.printf("%-12s %-6s %-17s %-2s %1.2f\n",transferDTO.getTransferId(), "To:",
                        transferDTO.getUserToName(), "$", transferDTO.getAmount());
            } else if (id == transferDTO.getUserTo()) {
                out.printf("%-12s %-6s %-17s %-2s %1.2f\n",transferDTO.getTransferId(), "From:",
                        transferDTO.getUserFromName(), "$", transferDTO.getAmount());
            }
        }
        out.println("---------");
        out.println();
        out.println("Please enter transfer ID to view details (0 to cancel):");
        out.println();
    }

    public void presentChosenTransfer(TransferDTO transferDTO) {
        out.println("--------------------------------------------");
        out.println("Transfer Details");
        out.println("--------------------------------------------");
        out.println("Id: " + transferDTO.getTransferId());
        out.println("From: " + transferDTO.getUserFromName());
        out.println("To: " + transferDTO.getUserToName());
        if (transferDTO.getType().equals("2")) {
            out.println("Type: Send");
        } else {
            out.println("Type: Request");
        }
        if (transferDTO.getStatus().equals("1")) {
            out.println("Status: Pending");
        } else if (transferDTO.getStatus().equals("2")) {
            out.println("Status: Approved");
        } else if (transferDTO.getStatus().equals("3")) {
            out.println("Status: Rejected");
        }
        out.println("Amount: $" + transferDTO.getAmount());
    }

    public void messageToUser(String prompt) {
        System.out.println(prompt);
    }

    public BigDecimal getUserInputBigDecimal(String prompt) {
        BigDecimal result = null;
        do {
            out.print(prompt + ": ");
            out.flush();
            String userInput = in.nextLine();
            try {
                result = new BigDecimal(userInput);
            } catch (NumberFormatException e) {
                out.println(System.lineSeparator() + "*** " + userInput + " is not valid ***" + System.lineSeparator());
            }
        } while (result == null);
        return result;
    }

    public int getAmount(User[] userList, int id) {
        boolean doneSelectingUser = false;
        int destination = 0;
        while (!doneSelectingUser) {
            presentUserList(userList, id);
            Integer destinationId = getUserInputInteger("ID to send to");
            if (destinationId == 0) {
                doneSelectingUser = true;
            } else if (id == destinationId) {
                messageToUser("\nPlease don't send money to yourself. Please just don't.");
            } else {
                for (User user : userList) {
                    if (user.getId().equals(destinationId)) {
                        destination = destinationId;
                        doneSelectingUser = true;
                    }
                }
            }
        }
        return destination;
    }

    public BigDecimal checkTransferAmount(Account account) {
        boolean doneSelectingAmount = false;
        BigDecimal moneyToSend = new BigDecimal("0");
        while (!doneSelectingAmount) {
            boolean notStealing = false;
            while (!notStealing) {
                moneyToSend = getUserInputBigDecimal("Enter amount");
                if (moneyToSend.compareTo(new BigDecimal("0")) > 0) {
                    notStealing = true;
                } else {
                    messageToUser("You have elected to not transfer funds. Goodbye.");
                    notStealing = true;
                    doneSelectingAmount = true;
                }
            }
            if (moneyToSend.compareTo(account.getAccountBalance()) <= 0.0) {
                doneSelectingAmount = true;
            } else {
                presentBalance(account.getAccountBalance());
                messageToUser("Current transfer amount greater than available balance.  Please try again!");
            }
        }
        return moneyToSend;
    }

    public Integer checkTransferId(TransferDTO[] transfers, int id) {
       boolean doneSelectingTransfer = false;
        Integer chosenTransferId = 0;
        while (!doneSelectingTransfer) {
            presentTransferList(transfers, id);
            chosenTransferId = getUserInputInteger("Please enter a transfer ID that appears in the list: ");
           if (chosenTransferId == 0) {
             doneSelectingTransfer = true;
            } else {
               for (TransferDTO transferDTO : transfers) {
                   if (transferDTO.getTransferId() == chosenTransferId) {
                       doneSelectingTransfer = true;
                   }
               }
           }
       }
        return chosenTransferId;
    }

}
