package com.revature.bankingapp.Account;

import com.revature.bankingapp.User.UserRepository;
import com.revature.bankingapp.User.UserService;
import com.revature.bankingapp.util.exceptions.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountServiceTestSuite {
    @Mock
    private AccountRepository mockAccountRepository;
    @InjectMocks
    private AccountService accServ;

    @Test
    public void create_Successful() {}

    @Test
    public void findById_Successful() {}

    @Test
    public void validateAccountInfo_Successful() {}




    @Test
    public void validateAccountInfo_MinBalance() throws InvalidInputException {
        Account account = new Account(Account.AccountType.CHECKING, 123123, -78.54);

        InvalidInputException e = assertThrows(InvalidInputException.class, () -> accServ.validateAccountInfo(account));
        assertEquals("Balance cannot be less than zero", e.getMessage());
    }
}
