package com.revature.bankingapp;

import com.revature.bankingapp.Account.AccountController;
import com.revature.bankingapp.Account.AccountRepository;
import com.revature.bankingapp.Account.AccountService;
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
        logger.info("Javalin app successfully started.....");
    }
}
