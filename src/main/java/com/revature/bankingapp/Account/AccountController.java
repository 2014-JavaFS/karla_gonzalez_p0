package com.revature.bankingapp.Account;

import com.fasterxml.jackson.core.JsonParseException;
import com.revature.bankingapp.util.exceptions.DataNotFoundException;
import com.revature.bankingapp.util.exceptions.InvalidInputException;
import com.revature.bankingapp.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.Objects;

import static com.revature.bankingapp.BankAppFrontController.logger;

public class AccountController implements Controller {
    private final AccountService accountService;
    private Account account;

    /**
     * Creates and initializes an instance of the AccountController class
     *
     * @param accountService An instance of the AccountService class
     */
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Registers application paths with the provided Javalin instance
     *
     * @param app An instance of the Javalin application
     */
    @Override
    public void registerPaths(Javalin app) {
        app.post("/account", this::postNewAccount);
        app.get("/account", this::getAccountById);
        app.post("/account/transaction", this::postTransaction);
    }

    /**
     * Handles the POST request to create a new account
     * Will catch and log any InvalidInputExceptions
     *
     * @param ctx The context object representing the HTTP request and response
     *            Contains the account information in the request body as JSON
     */
    public void postNewAccount(Context ctx) {
        try {
            Account newAccount = ctx.bodyAsClass(Account.class);
            ctx.json(accountService.create(newAccount));
            ctx.status(HttpStatus.CREATED);

        } catch (InvalidInputException e) {
            e.printStackTrace();

            ctx.status(400);
            ctx.result("Invalid input. Please ensure all fields are filled out correctly");
        } catch (RuntimeException e) {
            e.printStackTrace();
            ctx.result("You cannot create more than one banking account.");
            ctx.status(403);
        }
    }

    /**
     * Handles the GET request to find an account with the provided Id
     * Will catch and log any DataNotFound and Runtime exceptions
     *
     * @param ctx The context object representing the HTTP request and response
     *            Contains the query parameter 'userId'
     */
    private void getAccountById(Context ctx) {
        try {
            int userId = Integer.parseInt(Objects.requireNonNull(ctx.queryParam("userId")));
            this.account = accountService.findById(userId);
            ctx.json(account);
        } catch (DataNotFoundException e) {
            ctx.status(404);
            ctx.result(e.getMessage());
        } catch (RuntimeException e) {
            logger.warn("Something else is amiss");
            e.printStackTrace();
            ctx.result("Looks like you're not logged in. Please log in to continue");
            ctx.status(500);
        }
    }

    /**
     * Handles the POST request to perform a deposit or withdrawal
     * Will catch and log any InvalidInputExceptions
     *
     * @param ctx The context object representing the HTTP request and response
     *            Contains query parameters 'transactionType' and 'amount'
     */
    public void postTransaction(Context ctx) {
        getAccountById(ctx);

        if(this.account == null) {
            ctx.status(404);
            ctx.result("It looks like you don't have an account currently open. Please create one to continue");
            return;
        }

        String transactionType = Objects.requireNonNull(ctx.queryParam("transactionType"));
        double amount = Double.parseDouble(Objects.requireNonNull(ctx.queryParam("amount")));

        try {
            if (transactionType.equals("deposit")) {
                accountService.validateDeposit(amount);
                deposit(amount);
            }

            if (transactionType.equals("withdraw")) {
                accountService.validateWithdrawal(amount, account.getAccountBalance());
                withdraw(amount);
            }

            ctx.json(account);
        } catch (InvalidInputException e) {
            ctx.result(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Increases the amount of money in the account balance
     *
     * @param amount The amount to add to the account balance
     */
    public void deposit(double amount) {
        amount += account.getAccountBalance();

        if (accountService.updateAccountBalance(account.getUserId(), amount))
            account.setAccountBalance(amount);
    }

    /**
     * Reduces the amount of money in the account
     *
     * @param amount The amount to remove from the current account balance
     */
    public void withdraw(double amount) {
        double balance = account.getAccountBalance();
        balance -= amount;

        if (accountService.updateAccountBalance(account.getUserId(), balance))
            account.setAccountBalance(balance);
    }
}
