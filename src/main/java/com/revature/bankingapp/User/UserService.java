package com.revature.bankingapp.User;

import com.revature.bankingapp.util.exceptions.InvalidInputException;
import com.revature.bankingapp.util.interfaces.Serviceable;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import static com.revature.bankingapp.BankAppFrontController.logger;

public class UserService implements Serviceable<User> {
    private final Predicate<String> isNotEmpty = str -> str != null && !str.isBlank();
    private final UserRepository userRepository;

    /**
     * Creates and initializes an instance of the UserService class
     *
     * @param userRepository An instance of the UserRepository class
     */
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;

        logger.info("UserService Initialized.....");
    }

    /**
     * Validates the information given by the user before creating a new user account
     *
     * @param user An instance of the User class representing the user attempting to sign up
     *
     * @return  An instance of the User class initialized with the new user's information
     *          Null if an InvalidInputException is thrown
     *
     * @throws InvalidInputException If any of the information provided is missing or not considered valid
     */
    @Override
    public User create(User user) throws InvalidInputException {
        logger.info("User.Create() -- User sent to service as {}", user.toString());
        validateUserInfo(user);

        User newUser = userRepository.create(user);

        logger.info("User.Create() -- User returned as {}", newUser.toString());
        return newUser;
    }

    /**
     * Searches the database to find and return the user matching the provided id
     *
     * @param userId The user ID to look for in the database
     *
     * @return The user matching the provided ID
     */
    @Override
    public User findById(int userId) {
        logger.info("User Id was sent to service as {}", userId);
        User user = userRepository.findById(userId);

        logger.info("User returned as {}", user.toString());
        return user;
    }

    /**
     * Searches the database for the user containing the same email AND password provided
     *
     * @param email     The email to search for in the database
     * @param password  THe password to search for in the database
     *
     * @return  The user matching the email and password provided
     *          Null if no such user is found
     *
     * @throws InvalidInputException If either the email or password are not provided
     */
    public User findByEmailAndPassword(String email, String password) throws InvalidInputException {
        if(!isNotEmpty.test(email) || !isNotEmpty.test(password))
            throw new InvalidInputException("One or more fields were left empty.");

        return userRepository.findByEmailAndPassword(email, password);
    }

    /**
     * Ensures that all the information needed to create a new user account is provided.
     * Uses regex patterns to ensure that a valid email address is provided and that the password matches the security criteria.
     *
     * @param user An instance of the User class representing the new user
     *
     * @throws InvalidInputException If any of the required information is not provided or found to be invalid
     */
    public void validateUserInfo(User user) throws InvalidInputException {
        String emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        String passwordPattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

        if (user == null)
            throw new InvalidInputException("User is null as it has not been instantiated in memory");

        String email = user.getEmail();
        String password = user.getPassword();

        if (!isNotEmpty.test(email) || !isNotEmpty.test(user.getFirstName()) || !isNotEmpty.test(user.getLastName())
        || !isNotEmpty.test(password))
            throw new InvalidInputException("One or more values are empty");

        if (!Pattern.compile(emailPattern).matcher(email).matches())
            throw new InvalidInputException("Invalid email address");

        if (!Pattern.compile(passwordPattern).matcher(password).matches())
            throw new InvalidInputException("Password doesn't fit security criteria");
    }
}
