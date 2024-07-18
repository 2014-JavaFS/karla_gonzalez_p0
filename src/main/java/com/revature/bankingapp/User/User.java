package com.revature.bankingapp.User;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    /**
     * Default Constructor. Creates an empty User object.
     */
    public User() {}

    /**
     * Constructs a User object with the specified attributes.
     *
     * @param userId    A six digit long, unique identifier
     * @param firstName The user's first name
     * @param lastName  The user's last name
     * @param email     The user's email address
     * @param password  The user's password
     */
    public User(int userId, String firstName, String lastName, String email, String password) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    /**
     * Retrieves the user's unique identifier
     *
     * @return  The user's unique identifier
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user's unique identifier
     *
     * @param userId The value to set the id to
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the user's first name
     *
     * @return The user's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the user's first name
     *
     * @param firstName The user's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves the user's last name
     *
     * @return The user's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the user's last name
     *
     * @param lastName The user's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves the user's email address
     *
     * @return The user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address
     *
     * @param email The user's email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the user's password
     *
     * @return The user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password
     *
     * @param password The user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Prints out the user's information in the following format:
     *      User Id: 'userId'
     *      Name: 'firstName' 'lastName'
     *      Email: 'email'
     *      Password: 'password'
     *
     * @return A string containing the user information
     */
    @Override
    public String toString() {
        return "User Id: " + userId + "\nName: " + firstName + " " + lastName + "\nEmail: " + email + "\nPassword: " + password;
    }
}

