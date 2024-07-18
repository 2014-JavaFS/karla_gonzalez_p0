package com.revature.bankingapp.Account;

import com.revature.bankingapp.util.exceptions.InvalidInputException;
import com.revature.bankingapp.util.interfaces.Serviceable;

public class AccountService  implements Serviceable<Account> {
    private final AccountRepository accountRepository;

    /**
     * Creates and initializes an instance of the AccountService class
     * @param accountRepository An instance of the AccountRepository class
     */
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Validates the information given by the user before creating a new checking or savings account
     *
     * @param account An instance of the Account class representing the account the user is attempting to create
     *
     * @return An instance of the Account class initialized with the new account's information
     *
     * @throws InvalidInputException If any information is missing or considered invalid
     */
    @Override
    public Account create(Account account) throws InvalidInputException {
        validateAccountInfo(account);
        return accountRepository.create(account);
    }

    /**
     * Searches the database to find and return the account matching the provided id
     *
     * @param userId The user ID to look for in the database
     *
     * @return The account matching the provided user Id
     */
    @Override
    public Account findById(int userId) {
        return accountRepository.findById(userId);
    }

    /**
     * Changes the amount of money in the account
     *
     * @param userId        The id of the owner of the account being updated
     * @param newBalance    The new account balance
     *
     * @return  True if the account balance was successfully updated
     *          False if the account balance could not be updated
     */
    public boolean updateAccountBalance(int userId, double newBalance) {
        return accountRepository.updateAccountBalance(userId, newBalance);
    }

    /**
     * Ensures that all the information needed to create a new account is provided.
     * Ensures that the balance amount is no less than zero
     *
     * @param account An instance of the Account class representing the new account
     *
     * @throws InvalidInputException If any of the required information is not provided or found to be invalid
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

    /**
     * Checks to make sure that deposit amounts are greater than zero but less than 10,000
     *
     * @param amount The amount to deposit into the account
     *
     * @throws InvalidInputException If deposit amount is outside the set range
     */
    public void validateDeposit(double amount) throws InvalidInputException {
        if (amount <= 0)
            throw new InvalidInputException("Amount should be greater than zero");

        if (amount > 10000)
            throw new InvalidInputException("Deposit limit is $10,000");

    }

    /**
     * Checks to make sure that withdrawal amounts are greater than zero
     * but no greater than the amount currently in the account
     *
     * @param amount            The amount to withdraw from the account
     * @param currentBalance    The balance in the account before the withdrawal is done
     *
     * @throws InvalidInputException If the amount is outside the set range
     */
    public void validateWithdrawal(double amount, double currentBalance) throws InvalidInputException {
        if (amount <= 0)
            throw new InvalidInputException("Amount should be greater than zero");

        if (amount > currentBalance)
            throw new InvalidInputException("Withdrawal amount cannot exceed the current account balance");
    }
}