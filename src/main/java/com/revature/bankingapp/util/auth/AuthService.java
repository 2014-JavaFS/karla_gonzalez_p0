package com.revature.bankingapp.util.auth;

import com.revature.bankingapp.User.User;
import com.revature.bankingapp.User.UserService;

import javax.security.sasl.AuthenticationException;

public class AuthService {
    private final UserService userService;

    /**
     * constructor requiring userService
     * @param userService business logic class for User class
     */
    public AuthService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Attempts to find the user matching the given email and password
     *
     * @param email     user's email
     * @param password  user's password
     * @return          user found with the given credentials
     * @throws AuthenticationException if credentials do not match those in the database
     */
    public User login(String email, String password) throws AuthenticationException {
        User user = userService.findByEmailAndPassword(email,password);
        if (user == null) throw new AuthenticationException("Incorrect email or password.");

        return user;
    }
}
