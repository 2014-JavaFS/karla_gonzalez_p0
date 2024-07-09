package com.revature.bankingapp.Account;

import com.revature.bankingapp.util.exceptions.InvalidInputException;

public class AccountService {

    //TODO: Access database to find user account info
    /*
    public Account findById(int id) {

        return new Account();
    }
     */

    public void validateAccountInfo(Account account) throws InvalidInputException {
        if (account == null)
            throw new InvalidInputException("Account is null as it has not been instantiated in memory");

        double balance = account.getAccountBalance();

        if (balance < 0)
            throw new InvalidInputException("Balance cannot be less than zero");

        if (account.getAccountType() == null)
            throw new InvalidInputException("Account type should not be null");
    }

}
