package com.revature.bankingapp.User;

import com.revature.bankingapp.util.exceptions.InvalidInputException;

import java.util.function.Predicate;

public class UserService {
    // Return true if string is empty or null
    private Predicate<String> isEmpty = str -> str == null && str.isBlank();

    /*
        Ensures all information is given when creating a new user
     */
    public void validateMinInfo(User user) throws InvalidInputException {
        // Ensure user is instantiated
        if (user == null)
            throw new InvalidInputException("User is null as it has not been instantiated in memory");

        // Ensure user email, first name, and last name, are not left blank
        if (isEmpty.test(user.getEmail()) || isEmpty.test(user.getFirstName()) || isEmpty.test(user.getLastName()))
            throw new InvalidInputException("One or more values are empty");

        // Ensure user id is exactly 6 digits long
        if (user.getUserId() > 999999 || user.getUserId() < 100000)
            throw new InvalidInputException("User Id should be exactly 6 digits long");
    }
}
