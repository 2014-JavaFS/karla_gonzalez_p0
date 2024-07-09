package com.revature.bankingapp;

import com.revature.bankingapp.Account.Account;
import com.revature.bankingapp.Account.AccountController;
import com.revature.bankingapp.Account.AccountService;
import com.revature.bankingapp.User.User;
import com.revature.bankingapp.User.UserController;
import com.revature.bankingapp.User.UserService;
import com.revature.bankingapp.util.ScannerValidator;

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
        int opt;

        Scanner scanner = new Scanner(System.in);

        User user;
        UserService userService = new UserService();
        UserController userController = new UserController(scanner, userService);

        do {
            System.out.println("Welcome to this bank app (Name TBD)");
            System.out.println("Please login or sign up to continue:");
            System.out.println("1. Log In \n2. Sign Up");
            System.out.println("3. Exit");

            opt = scanner.nextInt();

            switch (opt) {
                case 1:
                    // TODO: Implement login method once database is created
                    // TODO: Throw exception if email not found
                    System.out.println("Logging In");
                    //if (userController.login()) accessAccount(userId);
                    break;
                case 2:
                    System.out.println("Signing Up");
                    user = userController.createUser();
                    if (user != null)
                        createAccount(scanner, user, userController);
                    break;
                case 3:
                    System.out.println("Exiting Application");
                    break;
                default:
                    System.out.println("Invalid input. Please enter number 1 or 2");
            }
        } while (opt != 3);
    }

    private static void createAccount(Scanner scanner, User user, UserController userController) {
        AccountService accountService = new AccountService();
        AccountController accountController = new AccountController(scanner, accountService);

        System.out.println("\nCreate a Savings or Checking account to continue");
        Account account = accountController.createAccount(user);

        if (account != null)
            accessAccount(scanner, user, userController, account);
    }

    private static void accessAccount(Scanner scanner, User user, UserController userController, Account account) {
        //TODO: Once logged in, use userID to find existing account and info
        int opt;
        AccountService accountService = new AccountService();
        AccountController accountController = new AccountController(scanner, accountService);

        do {
            System.out.println("\nWelcome " + user.getFirstName());
            System.out.println("1. View Your Account Information");
            System.out.println("2. Make a Deposit");
            System.out.println("3. Make a Withdrawal");
            System.out.println("4. Log Out");
            System.out.print("Please enter a numeric choice: ");
            opt = scanner.nextInt();

            System.out.println();

            switch (opt) {
                case 1:
                    System.out.println("Viewing Account Information");
                    userController.viewUserInfo(user);
                    accountController.viewBalance(account);
                    break;
                case 2:
                    System.out.println("Making a Deposit");
                    accountController.deposit(account);
                    break;
                case 3:
                    if(account.getAccountBalance() <= 0) {
                        System.out.println("Insufficient funds. Unable to make a withdrawal at this time");
                        break;
                    }

                    System.out.println("Making a Withdrawal");
                    accountController.withdraw(account);
                    break;
                case 4:
                    System.out.println("Exiting Application.");
                    break;
                default:
                    System.out.println("Invalid input. Please enter a number 1-4");
            }
        } while (opt != 4);
    }
}
