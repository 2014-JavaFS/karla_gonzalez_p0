package com.revature.bankingapp.Account;

import com.revature.bankingapp.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class AccountController implements Controller {
    private AccountService accountService;

    /**
     * Constructor that requires dependencies to create an instance of this class
     *
     * @param accountService    used for validating that input
     */
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Creates a new checking or savings account
     *
     * @param   userId the id of the user associated with the new account
     * @return  the newly created account
     */
    public Account createAccount(int userId) {
        return null;
    }

    /**
     * Increases the amount of money in the account
     *
     * @param account the account to add money to
     */
    public void deposit(Account account) {

    }

    /**
     * reduces the amount of money in the account
     *
     * @param account the account to withdraw from
     */
    public void withdraw(Account account) {

    }

    /**
     * Prints the current amount in the user's account
     *
     * @param account the account information to print out
     */
    public void viewBalance(Account account) {
        System.out.println(account.toString());
    }

    /**
     * Retrieve the account associated with the current user using their id
     *
     * @param userId id of the current user
     * @return account associated with user
     */
    public Account getAccountById(int userId) {
        return accountService.findById(userId);
    }

    //TODO: Gets all accounts, change to only get specific user account
    @Override
    public void registerPaths(Javalin app) {
        app.post("/account", this::postNewAccount);
        app.get("account/{userId}", this::getAccountById);
    }

    private void getAccountById(Context ctx) {
        int userId = Integer.parseInt(ctx.pathParam("userId"));
        Account account = accountService.findById(userId);

        ctx.json(account);
    }

    public void postNewAccount(Context ctx){
        Account account = ctx.bodyAsClass(Account.class);
        ctx.json(accountService.create(account));
        ctx.status(HttpStatus.CREATED);
    }

    private void putUpdateBalance(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);

        if(accountService.updateBalance(account))
            ctx.status(HttpStatus.ACCEPTED);
        else
            ctx.status(HttpStatus.BAD_REQUEST);

    }
}
