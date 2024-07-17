package com.revature.bankingapp.User;

import com.revature.bankingapp.util.exceptions.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTestSuite {
    //@Mock
    private UserRepository mockUserRepository;
    //@InjectMocks
    private UserService uServ;

    @BeforeEach
    public void setup() {
        mockUserRepository = mock(UserRepository.class);
        uServ = new UserService(mockUserRepository);
    }

    @Test
    public void create_Successful() throws InvalidInputException {
        User validUser = new User(100010, "John", "Doe", "jdoe2@email.net", "R3v@2tur");
        when(mockUserRepository.create(validUser)).thenReturn(validUser);

        User returnedUser = uServ.create(validUser);

        assertEquals(validUser, returnedUser);
        verify(mockUserRepository, times(1)).create(validUser);
    }

    @Test
    public void findById_Successful() {
        User validUser = new User(123123, "John", "Doe", "jdoe2@email.net", "R3v@2tur");
        int validUserId = 123123;
        when(mockUserRepository.findById(validUserId)).thenReturn(validUser);

        User returnedUser = uServ.findById(validUserId);

        assertEquals(validUser, returnedUser);
        verify(mockUserRepository, times(1)).findById(validUserId);
    }

    @Test
    public void findByEmailAndPassword_Successful() throws InvalidInputException {
        User validUser = new User(123123, "John", "Doe", "jdoe2@email.net", "R3v@2tur");
        String validUserEmail = "jdoe2@email.net";
        String validUserPassword = "R3v@2tur";
        when(mockUserRepository.findByEmailAndPassword(validUserEmail, validUserPassword)).thenReturn(validUser);

        User returnedUser = uServ.findByEmailAndPassword(validUserEmail, validUserPassword);

        assertEquals(validUser, returnedUser);
        verify(mockUserRepository, times(1)).findByEmailAndPassword(validUserEmail, validUserPassword);
    }

    @Test
    public void validateMinInfo_FieldLeftBlank() throws InvalidInputException {
        User user = new User(100000, "John", "Doe", "", "R3vat$u2");

        InvalidInputException e = assertThrows(InvalidInputException.class, () -> uServ.validateUserInfo(user));
        assertEquals("One or more values are empty", e.getMessage());
    }

    @Test
    public void validateMinInfo_InvalidEmailFormat() throws InvalidInputException {
        User user = new User(100000, "John", "Doe", "jDoeemail@net", "R3vat$u2");

        InvalidInputException e = assertThrows(InvalidInputException.class, () -> uServ.validateUserInfo(user));
        assertEquals("Invalid email address", e.getMessage());
    }

    @Test
    public void validateMinInfo_InvalidPassword() throws InvalidInputException {
        User user = new User(100100, "John", "Doe", "jDoe@email.net", "Password");

        InvalidInputException e = assertThrows(InvalidInputException.class, () -> uServ.validateUserInfo(user));
        assertEquals("Password doesn't fit security criteria", e.getMessage());
    }
}
