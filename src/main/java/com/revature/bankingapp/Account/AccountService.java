package com.revature.bankingapp.Account;

import com.revature.bankingapp.util.exceptions.InvalidInputException;
import com.revature.bankingapp.util.interfaces.Serviceable;

public class AccountService  implements Serviceable<Account> {
    private AccountRepository accountRepository;


    public AccountService(AccountRepository accountRepository) {
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

    public boolean delete(int userId) {
        return accountRepository.delete(userId);
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

    public void validateDeposit(double amount) throws InvalidInputException {
        if (amount <= 0)
            throw new InvalidInputException("Amount should be greater than zero");

        if (amount > 10000)
            throw new InvalidInputException("Deposit limit is $10,000");

    }

    public void validateWithdrawal(double amount, double currentBalance) throws InvalidInputException {
        if (amount <= 0)
            throw new InvalidInputException("Amount should be greater than zero");

        if (amount > currentBalance)
            throw new InvalidInputException("Withdrawal amount cannot exceed the current account balance");

    }
}