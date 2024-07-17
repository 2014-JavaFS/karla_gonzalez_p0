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

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void registerPaths(Javalin app) {
        app.post("/login", this::postLogin);
    }

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
            ctx.result(e.getMessage() + " Please create an account");
        }
    }
}
