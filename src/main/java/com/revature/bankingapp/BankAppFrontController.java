package com.revature.bankingapp;

import com.revature.bankingapp.Account.AccountController;
import com.revature.bankingapp.Account.AccountRepository;
import com.revature.bankingapp.Account.AccountService;
import com.revature.bankingapp.User.User;
import com.revature.bankingapp.User.UserController;
import com.revature.bankingapp.User.UserRepository;
import com.revature.bankingapp.User.UserService;
import com.revature.bankingapp.util.auth.AuthController;
import com.revature.bankingapp.util.auth.AuthService;

import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankAppFrontController {
    public static final Logger logger = LoggerFactory.getLogger(BankAppFrontController.class);

    public static void main(String[] args) {
        logger.info("Banking Application is up and running.....");

        Javalin app = Javalin.create(config -> {
            config.jsonMapper(new JavalinJackson());
        });

        User userLoggedIn = null;
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService);
        userController.registerPaths(app);

        AccountRepository accountRepository = new AccountRepository();
        AccountService accountService = new AccountService(accountRepository);
        AccountController accountController = new AccountController(accountService);
        accountController.registerPaths(app);

        AuthService authService = new AuthService(userService);
        AuthController authController = new AuthController(authService);
        authController.registerPaths(app);

        app.start(8080);
    }

    /**
     * Uses the user's id to retrieve their account from the database.
     * If no account is found, the user is prompted to create one before being able to perform any other actions
     *
     * @param user              the user currently using this application
     * @param userController    class needed for the different account functionalities
     */
    private static void accessAccount(User user, UserController userController) {
        AccountRepository accountRepository = new AccountRepository();
        AccountController accountController = new AccountController(new AccountService(accountRepository));
        //Account account = accountController.getAccountById(user.getUserId());

    /*
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

     */
    }
}
