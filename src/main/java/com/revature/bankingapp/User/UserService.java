package com.revature.bankingapp.User;

import com.revature.bankingapp.util.exceptions.InvalidInputException;

import java.util.function.Predicate;

public class UserService {
    // Return true if string is empty or null
    private Predicate<String> isNotEmpty = str -> str != null && !str.isBlank();

    /*
        Ensures all information is given when creating a new user
     */
    public void validateMinInfo(User user) throws InvalidInputException {
        // Ensure user is instantiated
        if (user == null)
            throw new InvalidInputException("User is null as it has not been instantiated in memory");

        int userId = user.getUserId();

        // Ensure user email, first name, and last name, are not left blank
        if (!isNotEmpty.test(user.getEmail()) || !isNotEmpty.test(user.getFirstName()) || !isNotEmpty.test(user.getLastName()))
            throw new InvalidInputException("One or more values are empty");

        // Ensure user id is exactly 6 digits long
        if (userId > 999999 || userId < 100000)
            throw new InvalidInputException("User Id should be exactly 6 digits long");
    }
}
