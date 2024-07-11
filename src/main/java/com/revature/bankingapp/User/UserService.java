package com.revature.bankingapp.User;

import com.revature.bankingapp.Account.Account;
import com.revature.bankingapp.Account.AccountRepository;
import com.revature.bankingapp.util.exceptions.InvalidInputException;
import com.revature.bankingapp.util.interfaces.Serviceable;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 *
 */
public class UserService implements Serviceable<User> {
    // Return true if string is empty or null
    private Predicate<String> isNotEmpty = str -> str != null && !str.isBlank();
    private UserRepository userRepository;


    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) throws InvalidInputException {
        validateUserInfo(user);
        return userRepository.create(user);
    }

    @Override
    public User findById(int userId) {
        return userRepository.findById(userId);
    }

    /**
     * Ensures all information is given (and valid) when creating a new user
     *
     * @param user the user currently using this application
     * @throws InvalidInputException exception thrown when there is invalid information
     */
    public void validateUserInfo(User user) throws InvalidInputException {
        String emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        String passwordPattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

        // Ensure user is instantiated
        if (user == null)
            throw new InvalidInputException("User is null as it has not been instantiated in memory");

        int userId = user.getUserId();
        String email = user.getEmail();
        String password = user.getPassword();

        // Ensure user email, first name, and last name, are not left blank
        if (!isNotEmpty.test(email) || !isNotEmpty.test(user.getFirstName()) || !isNotEmpty.test(user.getLastName())
        || !isNotEmpty.test(password))
            throw new InvalidInputException("One or more values are empty");

        // Ensure user id is exactly 6 digits long
        if (userId > 999999 || userId < 100000)
            throw new InvalidInputException("User Id should be exactly 6 digits long");

        // Ensure email is in the correct format using regex pattern
        if (!Pattern.compile(emailPattern).matcher(email).matches())
            throw new InvalidInputException("Invalid email address");

        if (!Pattern.compile(passwordPattern).matcher(password).matches())
            throw new InvalidInputException("Password doesn't fit security criteria");
    }

    public User findByEmailAndPassword(String email, String password) {
        //TODO: Use database to implement this method

        // return user if email and password match, else return null
        return null;
    }
}
