package com.revature.bankingapp.User;

import com.revature.bankingapp.util.exceptions.DataNotFoundException;
import com.revature.bankingapp.util.exceptions.InvalidInputException;
import com.revature.bankingapp.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.Objects;

import static com.revature.bankingapp.BankAppFrontController.logger;

public class UserController implements Controller {
    private final UserService userService;

    /**
     * Creates and initializes an instance of the UserController class
     *
     * @param userService An instance of the UserService class
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registers application paths with the provided Javalin instance
     *
     * @param app An instance of the Javalin application
     */
    @Override
    public void registerPaths(Javalin app) {
        app.get("/user", this::getUserById);
        app.post("/user", this::postNewUser);
    }

    /**
     * Handles the POST request to create a new user account
     *
     * @param ctx The context object representing the HTTP request and response
     *            Contains the user information in the request body as JSON
     */
    public void postNewUser(Context ctx) {
        User newUser = ctx.bodyAsClass(User.class);

        try {
            userService.create(newUser);
            ctx.result("Account created. Please login to continue.");
            ctx.status(HttpStatus.CREATED);

        } catch (InvalidInputException e) {
            e.printStackTrace();
            ctx.status(400);
            ctx.result("Please make sure all fields are filled out correctly.");
        } catch (RuntimeException e) {
            e.printStackTrace();
            logger.warn("User account could not be created");
            ctx.result("Unable to create a new account. That email may already be registered.");
            ctx.status(500);
        }
    }

    /**
     * Handles the GET request to find a user with the provided Id
     * Will catch and log any DataNotFound and Runtime exceptions
     *
     * @param ctx The context object representing the HTTP request and response
     *            Contains the query parameter 'userId'
     */
    private void getUserById(Context ctx) {
        try {
            int userId = Integer.parseInt(Objects.requireNonNull(ctx.queryParam("userId")));

            logger.info("User Id {}, {}", userId, "sent thorough path parameter");
            User returnedUser = userService.findById(userId);

            logger.info("User {} found, converting to JSON", userId);
            ctx.json(returnedUser);
        } catch (DataNotFoundException e) {
            logger.warn("Data was not found. Responded with 404");
            ctx.status(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            logger.warn("The user id is null");
            e.printStackTrace();
            ctx.result("Looks like you're not logged in. Please log in to continue.");
            ctx.status(500);
        }
    }
}
