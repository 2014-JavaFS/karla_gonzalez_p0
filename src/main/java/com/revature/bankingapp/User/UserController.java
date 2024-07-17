package com.revature.bankingapp.User;

import com.revature.bankingapp.util.exceptions.DataNotFoundException;
import com.revature.bankingapp.util.exceptions.InvalidInputException;
import com.revature.bankingapp.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static com.revature.bankingapp.BankAppFrontController.logger;

public class UserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void registerPaths(Javalin app) {
        app.get("/user", this::getUserById);
        app.post("/user", this::postNewUser);
    }

    public void postNewUser(Context ctx) {
        User newUser = ctx.bodyAsClass(User.class);

        try {
            userService.create(newUser);
            ctx.result("Account created. Please login to continue.");
            ctx.status(HttpStatus.CREATED);

        } catch (InvalidInputException e) {
            e.printStackTrace();
        }
    }

    private void getUserById(Context ctx) {
        int userId = Integer.parseInt(Objects.requireNonNull(ctx.queryParam("userId")));

        logger.info("User Id {}, {}", userId, "sent thorough path parameter");

        try {
            User returnedUser = userService.findById(userId);
            logger.info("User {} found, converting to JSON", userId);
            ctx.json(returnedUser);
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
