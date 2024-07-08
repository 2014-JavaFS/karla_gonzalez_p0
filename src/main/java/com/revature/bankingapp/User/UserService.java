package com.revature.bankingapp.User;

import com.revature.bankingapp.util.exceptions.InvalidInputException;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class UserService {
    // Return true if string is empty or null
    private Predicate<String> isNotEmpty = str -> str != null && !str.isBlank();



    /*
        Ensures all information is given when creating a new user
     */
    public void validateMinInfo(User user) throws InvalidInputException {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

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

        // Ensure email is in the correct format using regex pattern
        if (!Pattern.compile(regexPattern).matcher(user.getEmail()).matches())
            throw new InvalidInputException("Invalid email address");
    }
}
