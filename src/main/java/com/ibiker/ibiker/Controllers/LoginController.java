package com.ibiker.ibiker.Controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ibiker.ibiker.JWTSecretKeyProvider;
import com.ibiker.ibiker.Models.AuthData;
import com.ibiker.ibiker.Models.Error;
import com.ibiker.ibiker.Models.Token;
import com.ibiker.ibiker.Models.User;
import com.ibiker.ibiker.Repositories.UsersRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Long EXPIRATION_OFFSET = 600_000_0L;
    private static final String SECRET_KEY = JWTSecretKeyProvider.getSecretKey();

    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody AuthData authData, Errors errors) {

        if (errors.hasErrors()) {
            return new ResponseEntity<>(new Error("An error occurred."), HttpStatus.BAD_REQUEST);
        }

        final User user = usersRepository.findUserByEmail(authData.getUserEmail());

        if (user == null) {
            return new ResponseEntity<>(new Error("No user corresponding to this email."), HttpStatus.NOT_FOUND);
        } else if (!passwordEncoder.matches(authData.getUserPassword(), user.getPassword())) {
            return new ResponseEntity<>(new Error("Password is incorrect."), HttpStatus.UNAUTHORIZED);
        }
        
        String userID = user.getId().toString();

        final String token = JWT.create()
                .withSubject(user.getEmail())
                .withIssuer(userID)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_OFFSET))
                .sign(Algorithm.HMAC512(SECRET_KEY.getBytes()));

        return new ResponseEntity<>(new Token(token), HttpStatus.OK);
    }
}
