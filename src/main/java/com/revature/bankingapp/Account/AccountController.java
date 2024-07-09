package com.revature.bankingapp.Account;

import com.revature.bankingapp.User.User;
import com.revature.bankingapp.util.ScannerValidator;
import com.revature.bankingapp.util.exceptions.InvalidInputException;

import java.util.Scanner;

public class AccountController {
    public Scanner scanner;
    private AccountService accountService;

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

    public AccountController(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    public Account createAccount(User user) {
        Account.AccountType accountType = Account.AccountType.valueOf("CHECKING");

        System.out.println("Select account type");
        System.out.println("1. Checking\n2. Savings");

        if(scanner.nextInt() == 2)
            accountType = Account.AccountType.valueOf("SAVINGS");

        System.out.print("\nEnter initial deposit amount: $");
        double accountBalance = scanner.nextDouble();

        return new Account(accountType, user.getUserId(), accountBalance);
    }

    public void deposit(Account account) {
        double amt = 0.0;
        boolean validAmt = false;

        System.out.println();
        //TODO: Add a way to escape if user changes their mind
        do {
            System.out.print("Enter amount to deposit ($##.##): $");

            if(!anyDouble.isValid(scanner, "\nInvalid data type, please enter a dollar amount"))
                continue;

            amt = scanner.nextDouble();

            if (amt <= 0) {
                System.out.println("\nAmount should be greater than zero");
                continue;
            }
            if (amt > 10000) {
                System.out.println("\nDeposit limit is $10,000");
                continue;
            }

            validAmt = true;
        } while (!validAmt);

        amt += account.getAccountBalance();
        account.setAccountBalance(amt);
    }

    public void withdraw(Account account) {
        //TODO: Add a way to escape if user changes their mind

        double amt = 0.0;
        double currentBalance = account.getAccountBalance();
        boolean validAmt = false;

        System.out.println();

        do {
            System.out.print("Enter amount to withdraw ($##.##): $");

            if(!anyDouble.isValid(scanner, "\nInvalid data type, please enter a dollar amount"))
                continue;

            amt = scanner.nextDouble();

            if (amt <= 0) {
                System.out.println("\nAmount should be greater than zero");
                continue;
            }
            if (amt > currentBalance) {
                System.out.println("\nCannot withdraw more than your current account balance");
                continue;
            }

            validAmt = true;
        } while (!validAmt);

        currentBalance -= amt;
        account.setAccountBalance(currentBalance);
    }

    public void viewBalance(Account account) {
            System.out.println(account.toString());
    }
}
