package com.revature.bankingapp.User;

import java.util.Scanner;
import java.util.function.Predicate;

public class UserController {
    public Scanner scanner;
    private User user;

    private Predicate<String> isNotEmpty = str -> str != null && !str.isBlank();

    // Sign up new users.
    // Here I am making the assumption that users will be provided with their user id before creating an account
    // with this application
    public int createUser() {
        scanner = new Scanner(System.in);

        // TODO: Use string method to capitalize first letter in first and last name
        System.out.println("Please enter your first name: ");
        String firstName = scanner.next();

        System.out.println("Please enter your last name: ");
        String lastName = scanner.next();

        // TODO: Make sure a valid email address is entered (should include '@' and end in ".com/.net./edu/etc.")
        System.out.println("Please enter your email address: ");
        String email = scanner.next();

        System.out.println("Enter your user ID: ");
        int userId = scanner.nextInt();

        user = new User(firstName, lastName, email, userId);

        return user.getUserId();
    }

    // TODO: Unable to print, user null?
    public void viewUserInfo(int userId) {

        System.out.printf("Name: %s %s %n", user.getFirstName(), user.getLastName());
        System.out.printf("Email Address: %s %n", user.getEmail());
    }

    // TODO: Should this be here?
    public boolean login() {
        // TODO: Implement method to validate login credentials
        // return true if login was successful, else false



        return true;
    }
}
