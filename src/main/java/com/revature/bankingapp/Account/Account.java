package com.revature.bankingapp.Account;

import java.security.PublicKey;

public class Account {
    private String accountType;
    private String accountId;
    private double accountBalance;

    public Account() {}

    public Account(String accountType, String accountId, double accountBalance) {
        this.accountType = accountType;
        this.accountId = accountId;
        this.accountBalance = accountBalance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return "Account Type: " + accountType + "\nAccount Id: " + accountId + "\nBalance: " + accountBalance;
    }
}
