package com.revature.bankingapp.util.auth;

import com.revature.bankingapp.User.User;
import com.revature.bankingapp.util.exceptions.DataNotFoundException;
import com.revature.bankingapp.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import javax.security.sasl.AuthenticationException;

public class AuthController implements Controller {
    private final AuthService authService;

    /**
     * Creates and initializes an instance of the AuthController class
     *
     * @param authService An instance of the AuthService class
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registers application paths with the provided Javalin instance
     *
     * @param app An instance of the Javalin application
     */
    @Override
    public void registerPaths(Javalin app) {
        app.post("/login", this::postLogin);
    }

    /**
     * Handles the POST request for user login.
     * Will attempt to authenticate the user by matching the email and password provided to those in the database.
     *
     * @param ctx The context object representing the HTTP request and response.
     *            Contains query parameters 'email' and 'password'
     */
    public void postLogin(Context ctx) {
        String email = ctx.queryParam("email");
        String password = ctx.queryParam("password");

        try {
            User user = authService.login(email, password);

            ctx.header("userId", String.valueOf(user.getUserId()));

            ctx.status(HttpStatus.ACCEPTED);
            ctx.result("Logged in successfully");
        } catch (AuthenticationException e) {
            ctx.status(HttpStatus.UNAUTHORIZED);
            ctx.result(e.getMessage());
        } catch (DataNotFoundException e) {
            ctx.status(HttpStatus.UNAUTHORIZED);
            ctx.result(e.getMessage() + " Please sign up to continue.");
        }
    }
}
