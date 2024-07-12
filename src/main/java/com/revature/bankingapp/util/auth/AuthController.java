package com.revature.bankingapp.util.auth;

import com.revature.bankingapp.User.User;
import com.revature.bankingapp.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import javax.security.sasl.AuthenticationException;
import java.util.Scanner;

public class AuthController implements Controller {
    private final AuthService authService;

    /**
     * Constructor requiring dependencies. Instantiation will only happen when these dependencies are included.
     *
     * @param authService   used for validating that input
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void registerPaths(Javalin app) {
        app.post("/login", this::postLogin);
    }

    private void postLogin(Context ctx) {
        String email = ctx.queryParam("email");
        String password = ctx.queryParam("password");

        try {
            User user = authService.login(email, password);
            ctx.header("userId", String.valueOf(user.getUserId()));
            ctx.status(HttpStatus.ACCEPTED);
        } catch (AuthenticationException e) {
            ctx.status(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Prompts the user to enter an email and password. Then parses this information to the AuthService login() method
     * to attempt to log the user into the application. If the credentials do not match those in the database an
     * AuthenticationException is thrown
     *
     * @return logged in user or null
     */
}
