package com.revature.bankingapp.util.auth;

import com.revature.bankingapp.User.User;
import com.revature.bankingapp.util.exceptions.InvalidInputException;

import javax.security.sasl.AuthenticationException;
import java.util.Scanner;

public class AuthController {
    private final Scanner scanner;
    private final AuthService authService;

    /**
     * Constructor requiring dependencies. Instantiation will only happen when these dependencies are included.
     *
     * @param scanner       used for user input
     * @param authService   used for validating that input
     */
    public AuthController(Scanner scanner, AuthService authService) {
        this.scanner = scanner;
        this.authService = authService;
    }

    /**
     * Prompts the user to enter an email and password. Then parses this information to the AuthService login() method
     * to attempt to log the user into the application. If the credentials do not match those in the database an
     * AuthenticationException is thrown
     *
     * @return logged in user or null
     */
    public User login() {
        try {
            System.out.print("Enter your email: ");
            String email = scanner.next();

            System.out.print("Enter your password: ");
            String password = scanner.next();

            return authService.login(email, password);
        } catch (AuthenticationException | InvalidInputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
