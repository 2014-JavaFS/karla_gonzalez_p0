package com.revature.bankingapp.Account;

public class Account {
    private int userId;
    private AccountType accountType;
    private double accountBalance;

    public enum AccountType {
        CHECKING,
        SAVINGS
    };

    /**
     * Default Constructor. Creates an empty Account object.
     */
    public Account() {}

    /**
     * Constructs an Account object with the specified attributes
     *
     * @param userId            the id of the user associated with this account
     * @param accountType       the type of account to be created, can be 'CHECKING' or 'SAVINGS'
     * @param accountBalance    the amount of money currently in the account
     */
    public Account(int userId, AccountType accountType, double accountBalance) {
        this.userId = userId;
        this.accountType = accountType;
        this.accountBalance = accountBalance;
    }

    /**
     * Retrieves The id of the account owner
     *
     * @return The id of the account owner
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user id associated with the account
     *
     * @param userId The id of the account owner
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the account type
     *
     * @return  The account type
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * Sets the account type
     *
     * @param accountType The account type (Can be 'CHECKING' or 'SAVINGS')
     */
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    /**
     * Retrieves the amount of money in the account
     *
     * @return The amount of money currently in the account
     */
    public double getAccountBalance() {
        return accountBalance;
    }

    /**
     * Sets the account balance
     *
     * @param accountBalance the amount to change the account balance to
     */
    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    /**
     * Prints out the account information in the following format:
     *      Account Type: 'accountType'
     *      Balance: 'accountBalance'
     *
     * @return A string containing the account information
     */
    @Override
    public String toString() {
        return "Account Type: " + accountType + "\nBalance: $" + String.format("%.2f", accountBalance);
    }
}
