package com.revature.bankingapp;

import com.revature.bankingapp.Account.Account;
import com.revature.bankingapp.Account.AccountController;
import com.revature.bankingapp.Account.AccountRepository;
import com.revature.bankingapp.Account.AccountService;
import com.revature.bankingapp.User.User;
import com.revature.bankingapp.User.UserController;
import com.revature.bankingapp.User.UserRepository;
import com.revature.bankingapp.User.UserService;
import com.revature.bankingapp.util.ScannerValidator;
import com.revature.bankingapp.util.auth.AuthController;
import com.revature.bankingapp.util.auth.AuthService;
import java.util.Scanner;

public class BankingApplication {
    /**
     * An input validation method which ensures the user enters a numerical value wherever an integer is required.
     * If the user input anything that isn't an integer, an error message is displayed
     *
     * returns true if the user inputs a number, otherwise it returns false.
     */
    static ScannerValidator anyInt = (scanner, errorMsg) -> {
        if(!scanner.hasNextInt()) {
            System.out.println(errorMsg);
            scanner.next();
            return false;
        }
        return true;
    };

    /**
     * the main method. This will be where the program starts everytime.
     * Prompts the user to either log in or sign up for a new account.
     *
     * @param args string of arguments inputted by the user
     */
    public static void main(String[] args) {
        int opt = 0;

        Scanner scanner = new Scanner(System.in);

        User user;
        UserService userService = new UserService(new UserRepository());
        UserController userController = new UserController(scanner, userService);
        AuthController authController = new AuthController(scanner, new AuthService(userService));

        System.out.println("Welcome to this bank app (Name TBD)");

        do {
            System.out.println("\nPlease login or sign up to continue:");
            System.out.println("1. Log In\n2. Sign Up\n3. Exit");
            
            if (anyInt.isValid(scanner, "\nInvalid input type. Please enter a number 1-3")) {
                opt = scanner.nextInt();

                switch (opt) {
                    case 1:
                        System.out.println("\nLogging In");
                        user = authController.logIn();

                        if (user != null)
                            accessAccount(scanner, user, userController);
                        break;

                    case 2:
                        System.out.println("\nSigning Up");
                        user = userController.createUser();

                        if (user != null)
                            accessAccount(scanner, user, userController);
                        break;

                    case 3:
                        System.out.println("\nExiting Application");
                        break;

                    default:
                        System.out.println("\nInvalid input. Please enter number 1 or 2");
                }
            }
        } while (opt != 3);
    }

    /**
     * Uses the user's id to retrieve their account from the database.
     * If no account is found, the user is prompted to create one before being able to perform any other actions
     *
     * @param scanner           for user input
     * @param user              the user currently using this application
     * @param userController    class needed for the different account functionalities
     */
    private static void accessAccount(Scanner scanner, User user, UserController userController) {
        int opt = 0;
        AccountRepository accountRepository = new AccountRepository();
        AccountController accountController = new AccountController(scanner, new AccountService(accountRepository));
        Account account = accountController.getAccountById(user.getUserId());

        if (account == null) {
            System.out.println("\nCreate a Savings or Checking account to continue");
            account = accountController.createAccount(user.getUserId());
        }

        do {
            System.out.println("\nWelcome " + user.getFirstName());
            System.out.println("1. View Your Account Information");
            System.out.println("2. Make a Deposit");
            System.out.println("3. Make a Withdrawal");
            System.out.println("4. Log Out");
            System.out.print("Please enter a numeric choice: ");

            if (anyInt.isValid(scanner, "\nInvalid input type. Please enter a number 1-3")) {
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
                        if (account.getAccountBalance() <= 0) {
                            System.out.println("Insufficient funds. Unable to make a withdrawal at this time");
                            break;
                        }

                        System.out.println("Making a Withdrawal");
                        accountController.withdraw(account);
                        break;

                    case 4:
                        System.out.println("Exiting Application");
                        break;

                    default:
                        System.out.println("Invalid input. Please enter a number 1-4");
                }
            }
        } while (opt != 4);
    }
}
