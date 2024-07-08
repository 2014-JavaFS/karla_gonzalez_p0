package com.revature.bankingapp.User;

import com.revature.bankingapp.util.exceptions.InvalidInputException;

import java.util.Scanner;
import java.util.function.Predicate;

public class UserController {
    public Scanner scanner;
    private final UserService userService;

    private Predicate<String> isNotEmpty = str -> str != null && !str.isBlank();

    //Constructor
    public UserController(Scanner scanner, UserService userService) {
        this.scanner = scanner;
        this.userService = userService;
    }

    // Sign up new users.
    // Here I am making the assumption that users will be provided with their user id before creating an account
    // with this application
    // TODO: Ask for pasword once database is created
    public User createUser() {
        scanner = new Scanner(System.in);

        // TODO: Use string method to capitalize first letter in first and last name
        System.out.println("Please enter your first name: ");
        String firstName = scanner.next();

        System.out.println("Please enter your last name: ");
        String lastName = scanner.next();

        System.out.println("Please enter your email address: ");
        String email = scanner.next();

        System.out.println("Enter your user ID: ");
        int userId = scanner.nextInt();

        try {
            User newUser = new User(firstName, lastName, email, userId);
            userService.validateMinInfo(newUser);
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }

        return new User(firstName, lastName, email, userId);
    }

    // TODO: Unable to print, user null?
    public void viewUserInfo(User user) {
        System.out.println(user.toString());
    }
/*
    // Start working on login after creating database
    // TODO: Should this be here?

    public boolean login() {
        // TODO: Implement method to validate login credentials
        // return true if login was successful, else false

        return true;
    }
    */
}
