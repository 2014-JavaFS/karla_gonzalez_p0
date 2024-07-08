package com.revature.bankingapp.User;

import com.revature.bankingapp.util.exceptions.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTestSuite {
    private UserService uServ;

    @BeforeEach
    public void setup() {
        uServ = new UserService();
    }

    @Test
    public void validateMinInfo_FieldLeftBlank() throws InvalidInputException {
        User user = new User("John", "Doe", "", "R3vat$u2", 100000);

        InvalidInputException e = assertThrows(InvalidInputException.class, () -> uServ.validateMinInfo(user));
        assertEquals("One or more values are empty", e.getMessage());
    }

    @Test
    public void validateMinInfo_UserIdTooLong() throws InvalidInputException {
        User user = new User("John", "Doe", "jDoe@email.net", "R3vat$u2", 1000010);

        InvalidInputException e = assertThrows(InvalidInputException.class, () -> uServ.validateMinInfo(user));
        assertEquals("User Id should be exactly 6 digits long", e.getMessage());
    }

    @Test
    public void validateMinInfo_UserIdTooShort() throws InvalidInputException {
        User user = new User("John", "Doe", "jDoe@email.net", "R3vat$u2", 1000);

        InvalidInputException e = assertThrows(InvalidInputException.class, () -> uServ.validateMinInfo(user));
        assertEquals("User Id should be exactly 6 digits long", e.getMessage());
    }

    @Test
    public void validateMinInfo_InvalidEmailFormat() throws InvalidInputException {
        User user = new User("John", "Doe", "jDoeemail@net", "R3vat$u2", 100000);

        InvalidInputException e = assertThrows(InvalidInputException.class, () -> uServ.validateMinInfo(user));
        assertEquals("Invalid email address", e.getMessage());
    }

    @Test
    public void validateMinInfo_InvalidPassword() throws InvalidInputException {
        User user = new User("John", "Doe", "jDoe@email.net", "Password", 100000);

        InvalidInputException e = assertThrows(InvalidInputException.class, () -> uServ.validateMinInfo(user));
        assertEquals("Password doesn't fit security criteria", e.getMessage());
    }
}
