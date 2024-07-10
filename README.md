# Banking Application

## Overview

A banking application built for Project 0.

## User Stories
### As a user:
   1. I want to be able to sign up
      1. So that I can easily access my funds
   2. I want to be able to log into my account
      1. So that I can view my account and make deposits and withdrawals
   3. I want to be able to create a new account
      1. So that I can have a place to store my money
   4. I want to be able to deposit funds into my account
      1. So that I can safely store my next paycheck
   5. I want to be able to withdraw funds from my account
      1. So that I can pay for things
   6. I want to be able to view my account balance(s)
      1. So that I know how much money I have to spend

## Must-Have Functionalities:
   1. Register new user account
      - Password
      - Email
      - First Name
      - Last Name
      - An auto-generated user Id
   2. Login
      - Password
      - Username
   3. Create at least one account
      - Account Type
      - Checking
      - Savings
   4. View account balances
      - Show account type
      - Show account balance
   5. Deposit funds
      - Amount of type double
      - Amount should be greater than zero
   6. Withdraw Funds
      - Amount of type double
      - Amount should be greater than zero
      - Amount should be less than the current account balance

## Minimum Features:
   1. Basic validation of user input (e.g. no registration for classes outside of registration window, no negative deposits/withdrawals, no overdrafting, etc.)
   2. Unit tests for all business-logic classes
   3. All exceptions are properly caught and handled
   4. Proper use of OOP principles
   5. Database is 3rd Normal Form Compliant
   6. Referential integrity (e.g. if a class is removed from the catalog, no students should be registered for it)
   7. Logging messages and exceptions to a file using a logger
   8. Generation of basic design documents (e.g. relational diagram, class diagram, flows, etc.)
