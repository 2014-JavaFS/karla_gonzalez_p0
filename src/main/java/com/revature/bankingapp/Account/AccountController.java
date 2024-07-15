package com.revature.bankingapp.Account;

import com.revature.bankingapp.util.exceptions.InvalidInputException;
import com.revature.bankingapp.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;

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

    @Override
    public void registerPaths(Javalin app) {
        app.get("/account/{userId}", this::getAccountById);
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
     *
     * @param userId id of the current user
     * @return account associated with user
     */
    private void getAccountById(Context ctx) {
        int userId = Integer.parseInt(ctx.pathParam("userId"));
        Account returnedAccount = accountService.findById(userId);

        ctx.json(returnedAccount);
    }

    /**
     * Increases the amount of money in the account
     *
     * @param account the account to add money to
     */
    public void deposit(Account account) {
/*
        if (validAmt) {
            amt += account.getAccountBalance();
            if (accountService.updateAccountBalance(account.getUserId(), amt))
                account.setAccountBalance(amt);
        }
 */
    }

    /**
     * reduces the amount of money in the account
     *
     * @param account the account to withdraw from
     */
    public void withdraw(Account account) {
        double amt = 0.0;
        double balance = account.getAccountBalance();
        boolean validAmt = false;

/*
        if (amt == -1)
            break;
        if (amt <= 0) {
            System.out.println("\nAmount should be greater than zero");
            continue;
        }
        if (amt > balance) {
            System.out.println("\nCannot withdraw more than your current account balance");
            continue;
        }

 */

        if (validAmt) {
            balance -= amt;
            if (accountService.updateAccountBalance(account.getUserId(), balance))
                account.setAccountBalance(balance);
        }
    }

    /**
     * Prints the current amount in the user's account
     *
     * @param account the account information to print out
     */
    public void viewBalance(Account account) {
        System.out.println(account.toString());
    }

}
