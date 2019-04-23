package com.ibiker.ibiker.Controllers;

import com.ibiker.ibiker.Models.AuthData;
import com.ibiker.ibiker.Models.Error;
import com.ibiker.ibiker.Models.User;
import com.ibiker.ibiker.Repositories.UsersRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity register(@Valid @RequestBody AuthData authData, Errors errors) {

        if (errors.hasErrors()) {
            return new ResponseEntity<>(new Error("An error occurred."), HttpStatus.BAD_REQUEST);
        }

        final User user = new User(ObjectId.get(), authData.getUserEmail(), passwordEncoder.encode(authData.getUserPassword()), System.currentTimeMillis());

        try {
            usersRepository.save(user);
        } catch (DuplicateKeyException e) {
            return new ResponseEntity<>(new Error("Email is already in use."), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
