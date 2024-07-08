package com.revature.bankingapp.User;

import com.revature.bankingapp.util.exceptions.InvalidInputException;

import java.util.Random;
import java.util.Scanner;
import java.util.function.Predicate;

public class UserController {
    public Scanner scanner;
    private final UserService userService;

    //private Predicate<String> isNotEmpty = str -> str != null && !str.isBlank();

    //Constructor
    public UserController(Scanner scanner, UserService userService) {
        this.scanner = scanner;
        this.userService = userService;
    }

    // Sign up new users.
    public User createUser() {
        //TODO: Make sure number aren't re-used
        Random rand = new Random();
        int userId = rand.nextInt(999999 - 100001) + 100000;

        scanner = new Scanner(System.in);

        // TODO: Use string method to capitalize first letter in first and last name
        System.out.println("Please enter your first name: ");
        String firstName = scanner.next();

        System.out.println("Please enter your last name: ");
        String lastName = scanner.next();

        System.out.println("Please enter your email address: ");
        String email = scanner.next();

        System.out.println("Enter a password: ");
        String password = scanner.next();

        try {
            User newUser = new User(firstName, lastName, email, password, userId);
            userService.validateMinInfo(newUser);
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }

        return new User(firstName, lastName, email, password, userId);
    }

    public void viewUserInfo(User user) {
        System.out.println(user.toString());
    }

/*
    // TODO: Start working on login after creating database

    public boolean login() {
        // TODO: Implement method to validate login credentials
        // return true if login was successful, else false

        return true;
    }
    */
}
