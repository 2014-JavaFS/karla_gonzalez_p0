package com.revature.bankingapp;

import com.revature.bankingapp.Account.Account;
import com.revature.bankingapp.Account.AccountController;
import com.revature.bankingapp.User.User;
import com.revature.bankingapp.User.UserController;

import java.util.Scanner;

public class BankingApplication {
    /*
    Must-Have Functionalities:
        1. Register new user account
            - Password
            - Username
            - First Name
            - Last Name
        2. Login
            - Password
            - Username
        3. Create at least one account
            - Account Type
                - Checking
                - Savings
            - Generate account number/id
        4. View account balances
            - Show account type
            - Show account balance
        5. Deposit funds
            - Double amount > 0
            - Account to deposit to
        6. Withdraw Funds
            - Double amount > 0 && <= amount in account
            - Account to withdraw from

    Suggested Bonus User Stories:
        1. view the transaction history for an account
        2. create multiple accounts per user (checking, savings, etc.)
        3. share a joint account with another user
        4. transfer money between accounts

    Minimum Features:
        1. Basic validation of user input (e.g. no registration for classes outside of registration window, no negative deposits/withdrawals, no overdrafting, etc.)
        2. Unit tests for all business-logic classes
        3. All exceptions are properly caught and handled
        4. Proper use of OOP principles
        5. Database is 3rd Normal Form Compliant
        6. Referential integrity (e.g. if a class is removed from the catalog, no students should be registered for it)
        7. Logging messages and exceptions to a file using a logger
        8. Generation of basic design documents (e.g. relational diagram, class diagram, flows, etc.)
    */


    public static void main(String[] args) {
        UserController userController = new UserController();

        Scanner scanner = new Scanner(System.in);
        int opt;

        boolean loggedIn = false;

        do {
            System.out.println("Welcome to this bank app (Name TBD)");
            System.out.println("Please login or sign up to continue:");
            System.out.println("1. Log In \n2. Sign Up");
            System.out.println("3. Exit");

            opt = scanner.nextInt();

            switch (opt) {
                case 1:
                    System.out.println("Logging In");
                    if (userController.login()) accessAccount();
                    break;
                case 2:
                    System.out.println("Signing Up");
                    userController.createUser();
                    break;
                case 3:
                    System.out.println("Exiting Application");
                    break;
                default:
                    System.out.println("Invalid input. Please enter number 1 or 2");
            }
        } while (opt != 3);


        //TODO: Implement method to check for successful login/signup before accessing next menu

        User user = new User();

    }

    private static void accessAccount() {
        Scanner scanner = new Scanner(System.in);
        AccountController accountController = new AccountController();
        int opt;

        do {
            System.out.println("Welcome!");
            System.out.println("1. View Your Account Balance");
            System.out.println("2. Make a Deposit");
            System.out.println("3. Make a Withdrawal");
            System.out.println("4. Create a Checking/Savings Account");
            System.out.println("5. Exit Application");
            System.out.println("Please enter a numeric choice: ");
            opt = scanner.nextInt();

            switch (opt) {
                case 1:
                    System.out.println("Viewing Account Balance(s)");
                    accountController.viewBalance();
                    break;
                case 2:
                    System.out.println("Making a Deposit");
                    accountController.deposit();
                    break;
                case 3:
                    System.out.println("Making a Withdrawal");
                    accountController.withdraw();
                    break;
                case 4:
                    System.out.println("Creating an Account");
                    accountController.createAccount();
                    break;
                case 5:
                    System.out.println("Exiting Application.");
                    break;
                default:
                    System.out.println("Invalid input. Please enter a number 1-4");
            }
        } while (opt != 5);
    }
}
