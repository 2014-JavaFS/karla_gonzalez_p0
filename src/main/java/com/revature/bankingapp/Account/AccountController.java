package com.revature.bankingapp.Account;

import com.revature.bankingapp.User.User;
import com.revature.bankingapp.util.ScannerValidator;

import java.util.Scanner;

public class AccountController {
    public Scanner scanner;
    private AccountService accountService;

    /**
     * An input validation method which ensures the user enters a numerical value wherever an integer is required.
     * If the user input anything that isn't an integer, an error message is displayed
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

    /**
     * An input validation method which ensures the user enters a numerical value wherever a double is required.
     * If the user inputs anything that isn't a double, an error message is displayed.
     *
     * Returns true if the user inputs a number, otherwise it returns false.
     */
    ScannerValidator anyDouble = (scanner, errorMsg) -> {
        if(!scanner.hasNextDouble()) {
            System.out.println(errorMsg);
            scanner.next();
            return false;
        }
        return true;
    };

    /**
     * constructor that requires dependencies to create an instance of this class
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
     * @param   user the user associated with the new account
     * @return  the newly created account
     */
    public Account createAccount(User user) {
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

        return new Account(accountType, user.getUserId(), 0.0);
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
        double currentBalance = account.getAccountBalance();
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
                if (amt > currentBalance) {
                    System.out.println("\nCannot withdraw more than your current account balance");
                    continue;
                }

                validAmt = true;
            }
        } while (!validAmt);

        if (validAmt) {
            currentBalance -= amt;
            account.setAccountBalance(currentBalance);
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
}
