package com.revature.bankingapp.User;

import java.util.Scanner;

public class UserController {
    public Scanner scanner;



    // TODO: Implement method to sign up new users
    public void createUser() {
        User newUser;

        System.out.println("Please enter your first name: ");

        System.out.println("Please enter your last name: ");
        // TODO: Make sure a valid email address is entered (should include '@' and end in ".com/.net./edu/etc.")
        System.out.println("Please enter your email address: ");
    }

    // TODO: Should this be here?
    public boolean login() {
        // TODO: Implement method to validate login credentials
        // return true if login was successful, else false
        return true;
    }
}
