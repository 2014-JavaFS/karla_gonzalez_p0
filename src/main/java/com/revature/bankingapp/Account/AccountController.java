package com.revature.bankingapp.Account;

import com.revature.bankingapp.util.exceptions.InvalidInputException;
import com.revature.bankingapp.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class AccountController implements Controller {
    private AccountService accountService;
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
        app.get("/account/{userId}", this::getAccountById);
        app.post("/account/{userId}", this::postTransaction);
    }

    /**
     * Creates a new checking or savings account
     *
     * @param   userId the id of the user associated with the new account
     * @return  the newly created account
     */
    public void createAccount(int userId) {
        Account.AccountType accountType = null;


//        switch (opt) {
//            case 1:
//                accountType = Account.AccountType.valueOf("CHECKING");
//                accountCreated = true;
//                break;
//            case 2:
//                accountType = Account.AccountType.valueOf("SAVINGS");
//                accountCreated = true;
//                break;
//            default:
//                System.out.println("Please enter either 1 or 2");
//        }

        Account account = new Account(accountType, userId, 0.0);

        try {
            accountService.create(account);
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve the account associated with the current user using their id
     */
    private void getAccountById(Context ctx) {
        int userId = Integer.parseInt(ctx.pathParam("userId"));
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
