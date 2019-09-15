package com.ibiker.ibiker.Controllers;

import com.ibiker.ibiker.Exceptions.UserNotFoundException;
import com.ibiker.ibiker.Exceptions.WrongPasswordException;
import com.ibiker.ibiker.JWTSecretKeyProvider;
import com.ibiker.ibiker.UserService;
import com.ibiker.ibiker.Models.AuthData;
import com.ibiker.ibiker.Models.Error;
import com.ibiker.ibiker.Models.Token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/login")
public class LoginController {


    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    private static final Long EXPIRATION_OFFSET = 600_000_0L;
    private static final String SECRET_KEY = JWTSecretKeyProvider.getSecretKey();

    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody AuthData authData, Errors errors) {

        if (errors.hasErrors()) {
            return new ResponseEntity<>(new Error("An error occurred."), HttpStatus.BAD_REQUEST);
        }

        final String token;

        try {
            token = userService.loginUser(authData, EXPIRATION_OFFSET, SECRET_KEY);
        } catch (UserNotFoundException userNotFound) {
            return new ResponseEntity<>(new Error(userNotFound.getMessage()), HttpStatus.NOT_FOUND);
        } catch (WrongPasswordException wrongPassword) {
            return new ResponseEntity<>(new Error(wrongPassword.getMessage()), HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(new Token(token), HttpStatus.OK);
    }
}
