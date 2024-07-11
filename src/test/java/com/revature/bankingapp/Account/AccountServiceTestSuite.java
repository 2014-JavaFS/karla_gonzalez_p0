package com.revature.bankingapp.Account;

import com.revature.bankingapp.util.exceptions.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountServiceTestSuite {
    private AccountService accServ;
    private AccountRepository accountRepository;

    @BeforeEach
    public void setup() {
        accServ = new AccountService(accountRepository);
    }

    @Test
    public void validateAccountInfo_MinBalance() throws InvalidInputException {
        Account account = new Account(Account.AccountType.CHECKING, 123123, -78.54);

        InvalidInputException e = assertThrows(InvalidInputException.class, () -> accServ.validateAccountInfo(account));
        assertEquals("Balance cannot be less than zero", e.getMessage());
    }
}
