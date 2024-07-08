package com.revature.bankingapp.Account;

import com.revature.bankingapp.User.User;

import java.util.Scanner;

public class AccountController {
    public Scanner scanner;
    private AccountService accountService;

    public AccountController(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    public Account createAccount(User user) {
        Account.AccountType accountType = Account.AccountType.valueOf("CHECKING");

        System.out.println("Select account type");
        System.out.println("1. Checking\n2. Savings");
        if(scanner.nextInt() == 2) accountType = Account.AccountType.valueOf("SAVINGS");

        System.out.print("\nEnter initial deposit amount: $");
        double accountBalance = scanner.nextDouble();

        return new Account(accountType, user.getUserId(), accountBalance);
    }

    public void deposit(Account account) {
        //TODO: Ensure amt > $0 && <= $10,000
        System.out.print("\nEnter amount to deposit including cents ($##.##): $");
        double amt = scanner.nextDouble();

        amt += account.getAccountBalance();
        account.setAccountBalance(amt);
    }

    public void withdraw(Account account) {
        //TODO: Ensure amount doesn't exceed balance
        //TODO: Ensure amount is greater than zero
        System.out.print("\nEnter amount to withdraw including cents ($##.##): $");
        double amt = scanner.nextDouble();

        amt -= account.getAccountBalance();
        account.setAccountBalance(amt);
    }

    public void viewBalance(Account account) {
        // TODO: If no account found, print nothing
        System.out.println(account.toString());
    }
}
