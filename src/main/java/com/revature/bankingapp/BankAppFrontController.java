package com.revature.bankingapp;

import com.revature.bankingapp.Account.Account;
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

public class BankAppFrontController {

    /**
     * the main method. This will be where the program starts everytime.
     * Prompts the user to either log in or sign up for a new account.
     *
     * @param args string of arguments inputted by the user
     */
    public static void main(String[] args) {
        Javalin app = Javalin.create();

        User user;
        UserService userService = new UserService(new UserRepository());
        UserController userController = new UserController(userService);
        AuthController authController = new AuthController(new AuthService(userService));


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
        Account account = accountController.getAccountById(user.getUserId());
    }
}
