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
     * Default constructor used for instantiation
     */
    public Account() {}

    /**
     * Constructor used for initialization
     *
     * @param accountType       the type of account to be created, can be CHECKING or SAVINGS
     * @param userId            the id of the user associated with this account
     * @param accountBalance    the amount of money currently in the account
     */
    public Account(int userId, AccountType accountType, double accountBalance) {
        this.userId = userId;
        this.accountType = accountType;
        this.accountBalance = accountBalance;
    }

    /**
     * Getter for accountType
     *
     * @return the type of account (SAVINGS or CHECKING)
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * Setter used for changing the account type
     *
     * @param accountType can be either CHECKING or SAVINGS
     */
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    /**
     * Getter used to retrieve the id of the account's owner
     *
     * @return int id of the user associated with the account
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user id
     *
     * @param userId numerical id of the account owner
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Getter used to retrieve the amount of money in the account
     *
     * @return the amount of money currently in the account
     */
    public double getAccountBalance() {
        return accountBalance;
    }

    /**
     * Setter used for changing the account balance
     *
     * @param accountBalance the amount to be changed to
     */
    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    /**
     * An override of the toString() method, used to print out the account information in the following format:
     * Account Type: [CHECKING/SAVINGS]
     * Balance: $##.##
     *
     * @return a string containing the account information
     */
    @Override
    public String toString() {
        return "Account Type: " + accountType + "\nBalance: $" + String.format("%.2f", accountBalance);
    }
}
