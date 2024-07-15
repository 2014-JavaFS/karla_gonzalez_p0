package com.revature.bankingapp.User;

import com.revature.bankingapp.util.exceptions.DataNotFoundException;
import com.revature.bankingapp.util.exceptions.InvalidInputException;
import com.revature.bankingapp.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.Random;

import static com.revature.bankingapp.BankAppFrontController.logger;

public class UserController implements Controller {
    private final UserService userService;

    //Constructor
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void registerPaths(Javalin app) {
        app.get("user/(userId)", this::getUserById);
    }

    // Sign up new users.
    public User createUser() {
        Random rand = new Random();
        int userId = rand.nextInt(999999 - 100001) + 100000;

        String firstName = "";
        //firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);

        String lastName = "";
        //lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);

        String email = "";
        String password = "";

        try {
            User newUser = new User(userId, firstName, lastName, email, password);
            userService.create(newUser);

            return newUser;

        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /*
     * Displays the user's information in the following format:
     * Name: [firstName] [lastName]
     * Email: [email]
     * User Id: [userId]
     *
     */
    public void getUserById(Context ctx) {
        int userId = Integer.parseInt(ctx.pathParam("userId"));
        logger.info("Accessing user info.....");
        try {
            User user = userService.findById(userId); //TODO: userService.?
            logger.info("User {} found, converting to JSON", userId);
            ctx.json(user);
            logger.info("Sending back to user");
        } catch (DataNotFoundException e) {
            logger.warn("Data was not found. Responded with 404");
            ctx.status(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            logger.warn("Something else is amiss");
            e.printStackTrace();
            ctx.status(500);
        }
    }

}
