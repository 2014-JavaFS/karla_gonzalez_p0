package com.revature.bankingapp.Account;

import com.revature.bankingapp.util.ScannerValidator;
import com.revature.bankingapp.util.exceptions.InvalidInputException;

import java.util.Scanner;

public class AccountController {
    public Scanner scanner;
    private AccountService accountService;

    /**
     * Input validation methods which ensures the user enters a numerical value wherever a number is required.
     * If the user inputs anything that isn't a number, an error message is displayed
     *
     * returns true if the user inputs a number, otherwise it returns false.
     */
    ScannerValidator anyInt = (scanner, errorMsg) -> {
        if(!scanner.hasNextInt()) {
            System.out.println(errorMsg);
            scanner.next();
            return false;
        }
        return true;
    };

    ScannerValidator anyDouble = (scanner, errorMsg) -> {
        if(!scanner.hasNextDouble()) {
            System.out.println(errorMsg);
            scanner.next();
            return false;
        }
        return true;
    };

    /**
     * Constructor that requires dependencies to create an instance of this class
     *
     * @param scanner           used for accepting user input
     * @param accountService    used for validating that input
     */
    public AccountController(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    /**
     * Creates a new checking or savings account
     *
     * @param   userId the id of the user associated with the new account
     * @return  the newly created account
     */
    public void createAccount(int userId) {
        int opt;
        boolean accountCreated = false;
        Account.AccountType accountType = null;

        do {
            System.out.println("Select account type");
            System.out.println("1. Checking\n2. Savings");

            if(anyInt.isValid(scanner, "\nInvalid data type, please enter either 1 or 2")) {
                opt = scanner.nextInt();

                switch (opt) {
                    case 1:
                        accountType = Account.AccountType.valueOf("CHECKING");
                        accountCreated = true;
                        break;
                    case 2:
                        accountType = Account.AccountType.valueOf("SAVINGS");
                        accountCreated = true;
                        break;
                    default:
                        System.out.println("Please enter either 1 or 2");
                }
            }
        } while (!accountCreated);

        Account account = new Account(accountType, userId, 0.0);

        try {
            accountService.create(account);
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }
    }

    /**
     * Increases the amount of money in the account
     *
     * @param account the account to add money to
     */
    public void deposit(Account account) {
        double amt = 0.0;
        boolean validAmt = false;

        System.out.println();

        do {
            System.out.print("Enter amount to deposit or -1 to cancel: $");

            if(anyDouble.isValid(scanner, "\nInvalid data type, please enter a dollar amount")) {
                amt = scanner.nextDouble();

                if (amt == -1)
                    break;
                if (amt <= 0) {
                    System.out.println("\nAmount should be greater than zero");
                    continue;
                }
                if (amt > 10000) {
                    System.out.println("\nDeposit limit is $10,000");
                    continue;
                }

                validAmt = true;
            }
        } while (!validAmt);

        if (validAmt) {
            amt += account.getAccountBalance();
            if (accountService.updateAccountBalance(account.getUserId(), amt))
                account.setAccountBalance(amt);
        }
    }

    /**
     * reduces the amount of money in the account
     *
     * @param account the account to withdraw from
     */
    public void withdraw(Account account) {
        double amt = 0.0;
        double balance = account.getAccountBalance();
        boolean validAmt = false;

        System.out.println();

        do {
            System.out.print("Enter amount to withdraw or '-1' to cancel: $");

            if(anyDouble.isValid(scanner, "\nInvalid data type, please enter a dollar amount")) {
                amt = scanner.nextDouble();

                if (amt == -1)
                    break;
                if (amt <= 0) {
                    System.out.println("\nAmount should be greater than zero");
                    continue;
                }
                if (amt > balance) {
                    System.out.println("\nCannot withdraw more than your current account balance");
                    continue;
                }

                validAmt = true;
            }
        } while (!validAmt);

        if (validAmt) {
            balance -= amt;
            if (accountService.updateAccountBalance(account.getUserId(), balance))
                account.setAccountBalance(balance);
        }
    }

    /**
     * Prints the current amount in the user's account
     *
     * @param account the account information to print out
     */
    public void viewBalance(Account account) {
        System.out.println(account.toString());
    }

    /**
     * Retrieve the account associated with the current user using their id
     *
     * @param userId id of the current user
     * @return account associated with user
     */
    public Account getAccountById(int userId) {
        return accountService.findById(userId);
    }
}
