package com.revature.bankingapp.Account;

public class Account {
    private int userId;
    private AccountType accountType;
    private double accountBalance;
    public enum AccountType {
        CHECKING,
        SAVINGS
    };

    public Account() {}

    public Account(AccountType accountType, int accountId, double accountBalance) {
        this.accountType = accountType;
        this.userId = accountId;
        this.accountBalance = accountBalance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public int getAccountId() {
        return userId;
    }

    public void setAccountId(int accountId) {
        this.userId = accountId;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return "Account Type: " + accountType + "\nBalance: $" + accountBalance;
    }
}
