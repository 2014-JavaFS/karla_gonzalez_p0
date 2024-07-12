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
        Random rand = new Random();
        int userId = rand.nextInt(999999 - 100001) + 100000;

        scanner = new Scanner(System.in);

        System.out.print("Please enter your first name: ");
        String firstName = scanner.next();
        firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);

        System.out.print("Please enter your last name: ");
        String lastName = scanner.next();
        lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);

        System.out.print("Please enter your email address: ");
        String email = scanner.next();

        System.out.print("Enter a password: ");
        String password = scanner.next();

        try {
            User newUser = new User(firstName, lastName, email, password, userId);
            userService.validateUserInfo(newUser);

        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }

        return new User(firstName, lastName, email, password, userId);
    }

    /**
     * Displays the user's information in the following format:
     * Name: [firstName] [lastName]
     * Email: [email]
     * User Id: [userId]
     *
     * @param user the user whose information should be accessed
     */
    public void viewUserInfo(User user) {
        System.out.println(user.toString());
    }
}
