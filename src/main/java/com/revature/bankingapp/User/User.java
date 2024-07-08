package com.revature.bankingapp.User;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int userId;

    // Constructors
    public User() {}

    public User(String firstName, String lastName, String email, String password, int userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userId = userId;
    }

    // Getters & Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName + "\nEmail: " + email + "\nUser Id: " + userId;
    }
}

