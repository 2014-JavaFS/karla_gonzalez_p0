package com.revature.bankingapp.Account;

import com.revature.bankingapp.util.exceptions.DataNotFoundException;
import com.revature.bankingapp.util.exceptions.InvalidInputException;
import com.revature.bankingapp.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.Objects;

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
        int userId = Integer.parseInt(Objects.requireNonNull(ctx.queryParam("userId")));

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
        try {
            int userId = Integer.parseInt(Objects.requireNonNull(ctx.queryParam("userId")));
            this.account = accountService.findById(userId);
            ctx.json(account);
        } catch (DataNotFoundException e) {
            ctx.status(404);
            ctx.result(e.getMessage());
        }
    }

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
