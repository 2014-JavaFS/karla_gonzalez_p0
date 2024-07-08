package com.revature.bankingapp.Account;

import com.revature.bankingapp.util.exceptions.InvalidInputException;

import java.util.function.Predicate;

public class AccountService {
    private Predicate<String> isNotEmpty = str -> str != null && !str.isBlank();

    public void validateAccountInfo(Account account) throws InvalidInputException {
        if (account == null)
            throw new InvalidInputException("Account is null as it has not been instantiated in memory");

        double balance = account.getAccountBalance();

        if (balance < 0)
            throw new InvalidInputException("Balance should be greater than or equal to zero");

        /*
        if (!isNotEmpty.test(account.getAccountType()))
            throw new InvalidInputException("Account type is null");

         */
    }

    // validate withdrawal amt

    // validate deposit amt



}
