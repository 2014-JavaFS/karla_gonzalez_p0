package com.revature.bankingapp.Account;

import com.revature.bankingapp.util.exceptions.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceTestSuite {
    private AccountRepository mockAccountRepository;
    private AccountService accServ;

    @BeforeEach
    public void setup() {
        mockAccountRepository = mock(AccountRepository.class);
        accServ = new AccountService(mockAccountRepository);
    }

    @Test
    public void create_Successful() throws InvalidInputException {
        Account validAccount = new Account(Account.AccountType.CHECKING, 123123, 10.13);
        when(mockAccountRepository.create(validAccount)).thenReturn(validAccount);

        Account returnedAccount = accServ.create(validAccount);

        assertEquals(validAccount, returnedAccount);
        verify(mockAccountRepository, times(1)).create(validAccount);
    }

    @Test
    public void findById_Successful() {
        Account validAccount = new Account(Account.AccountType.CHECKING, 898900, 23.24);
        int validAccountId = 898900;
        when(mockAccountRepository.findById(validAccountId)).thenReturn(validAccount);

        Account returnedAccount = accServ.findById(validAccountId);

        assertEquals(validAccount, returnedAccount);
        verify(mockAccountRepository, times(1)).findById(validAccountId);
    }

    @Test
    public void updateAccountBalance_Successful() {
        double balance = 10.25;
        int userId = 123123;
        when(mockAccountRepository.updateAccountBalance(userId, balance)).thenReturn(true);

        boolean balanceUpdated = accServ.updateAccountBalance(userId, balance);

        assertTrue(balanceUpdated);
        verify(mockAccountRepository, times(1)).updateAccountBalance(userId, balance);
    }

    @Test
    public void delete_Successful() {
        int userId = 898900;
        when(mockAccountRepository.delete(userId)).thenReturn(true);

        boolean accountDeleted = accServ.delete(userId);

        assertTrue(accountDeleted);
        verify(mockAccountRepository, times(1)).delete(userId);
    }

    @Test
    public void validateAccountInfo_MinBalance() throws InvalidInputException {
        Account account = new Account(Account.AccountType.CHECKING, 123123, -78.54);

        InvalidInputException e = assertThrows(InvalidInputException.class, () -> accServ.validateAccountInfo(account));
        assertEquals("Balance cannot be less than zero", e.getMessage());
    }
}
