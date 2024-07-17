package com.revature.bankingapp.util.auth;

import com.revature.bankingapp.User.User;
import com.revature.bankingapp.User.UserService;
import com.revature.bankingapp.util.exceptions.InvalidInputException;

import javax.security.sasl.AuthenticationException;

public class AuthService {
    private final UserService userService;

    /**
     * Creates and initializes an instance of the AuthService class
     *
     * @param userService an instance of the UserService class
     */
    public AuthService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Ensures that the email and password provided match an already existing user.
     *
     * @param email the email address provided by the user
     * @param password the password provided by the user
     *
     * @return an instance of the User class filled with the existing user's information
     *
     * @throws AuthenticationException if the provided email and password do not return a user
     */
    public User login(String email, String password) throws AuthenticationException {
        User user = null;
        try {
            user = userService.findByEmailAndPassword(email,password);
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }

        if (user == null)
            throw new AuthenticationException("Incorrect email or password.");

        return user;
    }
}
