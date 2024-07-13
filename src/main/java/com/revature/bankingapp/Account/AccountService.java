package com.revature.bankingapp.Account;

import com.revature.bankingapp.util.exceptions.InvalidInputException;
import com.revature.bankingapp.util.interfaces.Serviceable;

public class AccountService  implements Serviceable<Account> {
    private AccountRepository accountRepository;


    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Override
    public Account create(Account account) throws InvalidInputException {
        validateAccountInfo(account);
        return accountRepository.create(account);
    }

    @Override
    public Account findById(int userId) {
        return accountRepository.findById(userId);
    }

    public boolean updateAccountBalance(int userId, double newBalance) {
        return accountRepository.updateAccountBalance(userId, newBalance);
    }

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