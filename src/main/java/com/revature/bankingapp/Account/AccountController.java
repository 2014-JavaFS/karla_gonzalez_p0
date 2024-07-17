package com.revature.bankingapp.Account;

import com.revature.bankingapp.util.exceptions.InvalidInputException;
import com.revature.bankingapp.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class AccountController implements Controller {
    private final AccountService accountService;
    private Account account;

    /**
     * Constructor that requires dependencies to create an instance of this class
     *
     * @param accountService    used for validating that input
     */
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void registerPaths(Javalin app) {
        app.post("/account", this::postNewAccount);
        app.get("/account", this::getAccountById);
        app.post("/account/transaction", this::postTransaction);
    }

    public void postNewAccount(Context ctx) {
        //TODO: throws NumberFormatException when not logged in first through postman (status code: 500)
        String stringId = ctx.queryParam("userId");
        System.out.println(stringId);

        if(stringId == null) {
            ctx.status(403);
            ctx.result("You cannot create a new account as you are not logged in");
            return;
        }

        int userId = Integer.parseInt(stringId);
        String accountType = ctx.queryParam("accountType");

        account = new Account(Account.AccountType.valueOf(accountType), userId, 0.0);

        try {
            ctx.json(accountService.create(account));
            ctx.status(HttpStatus.CREATED);

        } catch (InvalidInputException e) {
            e.printStackTrace();

            ctx.status(400);
            ctx.result("Invalid input. Please ensure all fields are filled out correctly");
        }
    }

    /**
     * Retrieve the account associated with the current user using their id
     */
    private void getAccountById(Context ctx) {
        String stringId = ctx.queryParam("userId");

        if (stringId == null) {
            ctx.result("Please log in to see account details.");
            ctx.status(HttpStatus.UNAUTHORIZED);
            return;
        }


        int userId = Integer.parseInt(stringId);

        this.account = accountService.findById(userId);

        ctx.json(account);
    }

    /**
     * Increases the amount of money in the account
     */
    public void postTransaction(Context ctx) {
        getAccountById(ctx);

        String transactionType = ctx.queryParam("transactionType");
        double amount = Double.parseDouble(ctx.queryParam("amount"));

        try {
            if (transactionType.equals("deposit")) {
                accountService.validateDeposit(amount);
                deposit(amount);
            }

            if (transactionType.equals("withdraw")) {
                accountService.validateWithdrawal(amount, account.getAccountBalance());
                withdraw(amount);
            }

        } catch (InvalidInputException e) {
            e.printStackTrace();
        }
        ctx.json(account);
    }

    public void deposit(double amount) {
        amount += account.getAccountBalance();

        if (accountService.updateAccountBalance(account.getUserId(), amount))
            account.setAccountBalance(amount);
    }

    public void withdraw(double amount) {
        double balance = account.getAccountBalance();
        balance -= amount;

        if (accountService.updateAccountBalance(account.getUserId(), balance))
            account.setAccountBalance(balance);
    }
}
